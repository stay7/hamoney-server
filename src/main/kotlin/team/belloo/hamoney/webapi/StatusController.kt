package team.belloo.hamoney.webapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import team.belloo.hamoney.Authentication
import team.belloo.hamoney.core.member.MemberRepository
import team.belloo.hamoney.core.user.User
import team.belloo.hamoney.domain.accountBook.FindUserAccountBooks
import team.belloo.hamoney.domain.accountBook.GetAccountBookUpdatedAt

@RestController
@Authentication
class StatusController(
    private val memberRepository: MemberRepository,
    private val findUserAccountBooks: FindUserAccountBooks,
    private val getAccountBookUpdatedAt: GetAccountBookUpdatedAt,
) {

    @GetMapping("/status")
    fun status(
        user: User
    ): JsonResult {
        val accountBooks = findUserAccountBooks(user.id)
        val accountBookIdToMembers = memberRepository.findAllByUserId(user.id).groupBy { it.accountBookId }

        return StatusResult(
            me = UserView(
                userId = user.id,
                nickname = user.nickname,
                email = user.email,
                profile = user.profile
            ),
            accountBooks = accountBooks.map {
                AccountBookStatusView(
                    accountBookId = it.id,
                    members = accountBookIdToMembers.getValue(it.id).map { it.userId }.toSet(),
                    revision = getAccountBookUpdatedAt(it).toEpochMilli()
                )
            }
        )
    }

    data class StatusResult(
        val me: UserView,
        val accountBooks: List<AccountBookStatusView>
    ) : JsonResult(status = Status.SUCCESS)

    data class UserView(
        val userId: Long,
        val nickname: String,
        val email: String,
        val profile: String
    )

    data class AccountBookStatusView(
        val accountBookId: Long,
        val members: Set<Long>,
        val revision: Long,
    )
}