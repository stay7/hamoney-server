package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.domain.member.Member
import team.belloo.hamoney.domain.member.MemberRepository
import team.belloo.hamoney.entity.user.UserEntity
import java.time.Clock

@UseCase
class NewAccountBook(
    private val accountBookRepository: AccountBookRepository,
    private val memberRepository: MemberRepository,
    private val initDefaultCategories: InitDefaultCategories,
    private val initDefaultPayments: InitDefaultPayments,
    private val clock: Clock,
) {
    operator fun invoke(
        user: UserEntity,
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

        return newAccountBook
    }
}