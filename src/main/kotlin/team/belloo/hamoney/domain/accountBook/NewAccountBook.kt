package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.entity.AccountBookEntity
import team.belloo.hamoney.entity.CategoryEntity
import team.belloo.hamoney.entity.MemberEntity
import team.belloo.hamoney.entity.UserEntity
import team.belloo.hamoney.persistence.AccountBookRepository
import team.belloo.hamoney.persistence.CategoryRepository
import team.belloo.hamoney.persistence.MemberRepository

@UseCase
class NewAccountBook(
    private val accountBookRepository: AccountBookRepository,
    private val memberRepository: MemberRepository,
    private val categoryRepository: CategoryRepository,
    private val defaultCategoryNames: List<String>
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

        defaultCategoryNames.map {
            CategoryEntity().apply {
                accountBookId = newAccountBook.id
                name = it
            }
        }.also {
            categoryRepository.saveAll(it)
        }

        return newAccountBook
    }
}