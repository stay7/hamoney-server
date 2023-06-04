package team.belloo.hamoney.webapi.accountbook

import arrow.core.merge
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.core.accountbook.AccountBookInvitationRepository
import team.belloo.hamoney.core.member.MemberRepository
import team.belloo.hamoney.core.user.User
import team.belloo.hamoney.domain.accountBook.JoinAccountBook
import team.belloo.hamoney.domain.accountBook.NewAccountBook
import team.belloo.hamoney.webapi.JsonResult

@RestController
@RequestMapping("/use/account_book")
@Authentication
class UseAccountBookController(
    private val newAccountBook: NewAccountBook,
    private val memberRepository: MemberRepository,
    private val accountBookInvitationRepository: AccountBookInvitationRepository,
    private val joinAccountBook: JoinAccountBook
) {

    @GetMapping("/alone")
    fun useAlone(
        user: User
    ): JsonResult {
        //TODO: 함께 사용하기 후 재설치 후 혼자 사용하기하면?
        if (memberRepository.findAllByUserId(user.id).isNotEmpty()) {
            return JsonResult.error("이미 가계부가 있습니다.")
        }

        val accountBook = newAccountBook(user)
        val accountBookInvitation = accountBookInvitationRepository.findByAccountBookId(accountBook.id)!!

        return AccountBookResult(accountBook.id, accountBookInvitation.code)
    }

    @PostMapping("/together")
    fun useTogether(
        user: User,
        @RequestBody form: UseTogetherForm
    ): JsonResult {
        return joinAccountBook(user, form.invitationCode).mapLeft {
            when (it) {
                JoinAccountBook.Error.INVITATION_NOT_EXIST -> JsonResult.error("존재하지 않는 코드입니다")
                JoinAccountBook.Error.ALREADY_JOINED -> JsonResult.error("이미 참여한 가계부입니다")
            }
        }.map {
            UseTogetherResult(it.id)
        }.merge()
    }

    data class UseTogetherForm(
        val invitationCode: Long
    )

    data class AccountBookResult(
        val accountBookId: Long,
        val invitationCode: Long
    ) : JsonResult(status = Status.SUCCESS)

    data class UseTogetherResult(
        val accountBookId: Long
    ) : JsonResult(status = Status.SUCCESS)
}