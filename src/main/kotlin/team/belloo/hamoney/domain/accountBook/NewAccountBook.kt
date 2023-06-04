package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.accountbook.AccountBook
import team.belloo.hamoney.core.accountbook.AccountBookRepository
import team.belloo.hamoney.core.member.Member
import team.belloo.hamoney.core.member.MemberRepository
import team.belloo.hamoney.core.user.User
import java.time.Clock

@UseCase
class NewAccountBook(
    private val accountBookRepository: AccountBookRepository,
    private val memberRepository: MemberRepository,
    private val initDefaultCategories: InitDefaultCategories,
    private val initDefaultPayments: InitDefaultPayments,
    private val issueInvitationCode: IssueInvitationCode,
    private val clock: Clock,
) {
    operator fun invoke(
        user: User,
    ): AccountBook {
        val newAccountBook = AccountBook(
            id = 0,
            name = "${user.nickname}의 가계부",
            createdAt = clock.instant(),
            updatedAt = clock.instant()
        ).let {
            accountBookRepository.save(it)
        }

        Member(
            userId = user.id,
            accountBookId = newAccountBook.id,
            joinedAt = clock.instant()
        ).also {
            memberRepository.save(it)
        }

        initDefaultCategories(InitDefaultCategories.Command(user, newAccountBook))
        initDefaultPayments(InitDefaultPayments.Command(user.id, newAccountBook.id))
        issueInvitationCode(newAccountBook.id)

        return newAccountBook
    }
}