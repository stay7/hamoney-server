package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.entity.accountbook.AccountBookEntity
import team.belloo.hamoney.entity.accountbook.MemberEntity
import team.belloo.hamoney.entity.user.UserEntity
import team.belloo.hamoney.persistence.AccountBookRepository
import team.belloo.hamoney.persistence.MemberRepository

@UseCase
class NewAccountBook(
    private val accountBookRepository: AccountBookRepository,
    private val memberRepository: MemberRepository,
    private val initDefaultCategories: InitDefaultCategories,
    private val initDefaultPayments: InitDefaultPayments
) {
    operator fun invoke(
        user: UserEntity,
    ): AccountBookEntity {
        val newAccountBook = AccountBookEntity().apply {
            name = "${user.nickname}의 가계부"
        }.let {
            accountBookRepository.save(it)
        }

        MemberEntity().apply {
            userId = user.id
            accountBookId = newAccountBook.id
        }.also {
            memberRepository.save(it)
        }

        initDefaultCategories(InitDefaultCategories.Command(user, newAccountBook))
        initDefaultPayments(InitDefaultPayments.Command(user.id, newAccountBook.id))

        return newAccountBook
    }
}