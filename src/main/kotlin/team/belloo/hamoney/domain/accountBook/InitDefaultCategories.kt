package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.core.accountbook.AccountBook
import team.belloo.hamoney.core.user.User
import team.belloo.hamoney.UseCase
import team.belloo.hamoney.domain.category.DefaultCategories
import team.belloo.hamoney.entity.accountbook.CategoryEntity
import team.belloo.hamoney.entity.accountbook.SubCategoryEntity
import team.belloo.hamoney.persistence.JpaCategoryRepository
import team.belloo.hamoney.persistence.JpaSubCategoryRepository

@UseCase
class InitDefaultCategories(
    private val categoryRepository: JpaCategoryRepository,
    private val subCategoryRepository: JpaSubCategoryRepository
) {

    operator fun invoke(command: Command) {
        DefaultCategories.DEFAULTS.map {
            val savedCategory = CategoryEntity().apply {
                accountBookId = command.accountBook.id
                name = it.name
            }.let {
                categoryRepository.save(it)
            }

            it.subCategories.map {
                SubCategoryEntity().apply {
                    categoryId = savedCategory.id
                    name = it.name
                    iconId = it.iconId
                }
            }.also {
                subCategoryRepository.saveAll(it)
            }
        }
    }

    data class Command(
        val user: User,
        val accountBook: AccountBook
    )
}