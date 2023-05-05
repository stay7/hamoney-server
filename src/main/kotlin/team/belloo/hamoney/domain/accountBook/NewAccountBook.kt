package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.domain.category.InitDefaultCategories
import team.belloo.hamoney.entity.AccountBookEntity
import team.belloo.hamoney.entity.MemberEntity
import team.belloo.hamoney.entity.UserEntity
import team.belloo.hamoney.persistence.AccountBookRepository
import team.belloo.hamoney.persistence.MemberRepository

@UseCase
class NewAccountBook(
    private val accountBookRepository: AccountBookRepository,
    private val memberRepository: MemberRepository,
    private val initDefaultCategories: InitDefaultCategories
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

        return newAccountBook
    }
}