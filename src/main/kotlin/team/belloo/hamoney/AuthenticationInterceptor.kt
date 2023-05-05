package team.belloo.hamoney

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import team.belloo.hamoney.entity.UserEntity
import team.belloo.hamoney.persistence.AccessTokenRepository
import team.belloo.hamoney.persistence.UserRepository
import team.belloo.hamoney.webapi.JsonResult
import java.time.Clock
import java.util.logging.Logger

@Component
class AuthenticateInterceptor(
    private val clock: Clock,
    private val accessTokenRepository: AccessTokenRepository,
    private val userRepository: UserRepository
) : HandlerInterceptor {
    private val objectMapper = jacksonObjectMapper()
    private val logger = Logger.getLogger(AuthenticateInterceptor::class.java.name)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (passable(handler)) return true

        return when (val result = validate(request)) {
            is Result.Success -> {
                request.setAttribute(HamoneyAttribute.USER_HEADER, result.user)
                true
            }

            else -> {
                when (result.reason) {
                    Result.Reason.AUTHORIZATION_NOT_FOUND -> {
                        response.status = HttpStatus.BAD_REQUEST.value()
                        response.writer.write(objectMapper.writeValueAsString(JsonResult(status = JsonResult.Status.ERROR)))
                    }
                    Result.Reason.TOKEN_EXPIRED -> {
                        response.status = HttpStatus.UNAUTHORIZED.value()
                        response.writer.write(objectMapper.writeValueAsString(JsonResult(status = JsonResult.Status.ERROR)))
                    }
                    else -> {
                        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
                        response.writer.write(objectMapper.writeValueAsString(JsonResult(status = JsonResult.Status.ERROR)))
                    }
                }
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                false
            }
        }
    }

    private fun passable(handler: Any): Boolean = when {
        handler !is HandlerMethod -> true
        handler.beanType.isAnnotationPresent(Authentication::class.java) -> false
        else -> true
    }

    private fun validate(request: HttpServletRequest): Result {
        val reqAccessToken = request.getHeader(HamoneyAttribute.AUTHORIZATION)
            ?: return Result.Fail(Result.Reason.AUTHORIZATION_NOT_FOUND)

        val accessToken = accessTokenRepository.findByToken(reqAccessToken)
            ?: return Result.Fail()

        if (accessToken.expiredAt < clock.instant()) {
            return Result.Fail(Result.Reason.TOKEN_EXPIRED)
        }

        val user = userRepository.findById(accessToken.userId).orElse(null) ?: return Result.Fail()
        return Result.Success(user)
    }
}

internal sealed class Result(val reason: Reason? = null) {
    class Success(
        val user: UserEntity
    ) : Result()

    class Fail(reason: Reason? = null) : Result(reason)

    enum class Reason {
        AUTHORIZATION_NOT_FOUND,
        TOKEN_EXPIRED
    }
}
