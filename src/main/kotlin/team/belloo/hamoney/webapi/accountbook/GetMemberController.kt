package team.belloo.hamoney.webapi.accountbook

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.core.member.MemberRepository
import team.belloo.hamoney.core.pay.Pay
import team.belloo.hamoney.core.pay.PayRepository
import team.belloo.hamoney.core.user.User
import team.belloo.hamoney.core.user.UserRepository
import team.belloo.hamoney.webapi.JsonResult

@RestController
@Authentication
@RequestMapping("/account_book/members")
class GetMemberController(
    private val memberRepository: MemberRepository,
    private val userRepository: UserRepository,
    private val payRepository: PayRepository,
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun members(
        user: User,
        @RequestParam accountBookId: Long
    ): JsonResult {
        val memberPay = memberRepository.findAllByUserId(user.id)
            .map {
                MemberPay(
                    user = userRepository.findById(it.userId)!!,
                    payments = payRepository.findAllPersonalById(it.userId)
                )
            }

        return MembersResult(
            accountBookId = accountBookId,
            members = memberPay.map {
                MemberView(
                    id = it.user.id,
                    nickname = it.user.nickname,
                    payments = it.payments.map {
                        PaymentView(
                            it.id,
                            it.iconId,
                            it.name
                        )
                    }
                )
            }
        )
    }

    data class MemberPay(
        val user: User,
        val payments: List<Pay>
    )

    data class MembersResult(
        val accountBookId: Long,
        val members: List<MemberView>
    ) : JsonResult(status = Status.SUCCESS)

    data class MemberView(
        val id: Long,
        val nickname: String,
        val payments: List<PaymentView>
    )

    data class PaymentView(
        val id: Long,
        val iconId: Int,
        val name: String
    )
}