package team.belloo.hamoney.webapi.accountbook

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.domain.accountBook.NewAccountBook
import team.belloo.hamoney.domain.member.MemberRepository
import team.belloo.hamoney.domain.user.User
import team.belloo.hamoney.entity.user.UserEntity
import team.belloo.hamoney.webapi.JsonResult

@RestController
@RequestMapping("/use/account_book")
@Authentication
class UseAccountBookController(
    private val newAccountBook: NewAccountBook,
    private val memberRepository: MemberRepository
) {

    @GetMapping("/alone")
    fun useAlone(
        user: User
    ): JsonResult {
        if (memberRepository.findAllByUserId(user.id).isNotEmpty()) {
            return JsonResult.error("이미 가계부가 있습니다.")
        }

        return AccountBookResult(newAccountBook(user).id)
    }

    @GetMapping("/together")
    fun useTogether(): JsonResult {
        return JsonResult.success()
    }

    data class AccountBookResult(
        val accountBookId: Long
    ) : JsonResult(status = Status.SUCCESS)
}