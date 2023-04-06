package team.belloo.hamoney

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.format.DateTimeFormatter

class AccessLogFilter(
    private val dateTimeFormatter: DateTimeFormatter,
    private val logger: Logger,
    private val clock: Clock
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val startTime: Instant = clock.instant()
        val request = ContentCachingRequestWrapper(req)
        val response = ContentCachingResponseWrapper(res)
        var exception: Exception? = null

        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            exception = e
        } finally {
            val endTime: Instant = clock.instant()
            log(
                startTime = startTime,
                endTime = endTime,
                request = request,
                response = response,
                exception = exception
            ).run {
                logger.info(this.toString())
                exception?.let {
                    logger.error(exception)
                    exception.printStackTrace()
                }
                response.copyBodyToResponse()
            }
        }
    }

    private fun log(
        startTime: Instant,
        endTime: Instant,
        request: ContentCachingRequestWrapper,
        response: ContentCachingResponseWrapper,
        exception: Exception?
    ): AccessLog {
        val requestJson = request.takeIf {
            it.contentType == MediaType.APPLICATION_JSON_VALUE
        }?.contentAsByteArray?.let {
            objectMapper.readTree(it)
        }
        val responseJsonNode = if (
            response.contentType != null
            && MediaType.parseMediaType(response.contentType).isCompatibleWith(MediaType.APPLICATION_JSON)
        )
            objectMapper.readTree(response.contentAsByteArray) else {
            null
        }

        return AccessLog(
            httpStatus = response.status,
            url = request.requestURI,
            requestBody = requestJson.toString(),
            requestTime = dateTimeFormatter.format(startTime),
            parameters = objectMapper.writeValueAsString(request.parameterMap),
            elapsedTime = "${Duration.between(startTime, endTime).toMillis()}ms",
            responseBody = responseJsonNode.toString()
        )
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AccessLog(
    val httpStatus: Int,
    val url: String,
    val requestBody: String? = null,
    val requestTime: String,
    val elapsedTime: String,
    val parameters: String? = null,
    val responseBody: String,
)

private val objectMapper = jacksonObjectMapper()