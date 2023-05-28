package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.domain.category.DefaultCategories
import team.belloo.hamoney.entity.accountbook.CategoryEntity
import team.belloo.hamoney.entity.accountbook.SubCategoryEntity
import team.belloo.hamoney.entity.user.UserEntity
import team.belloo.hamoney.persistence.CategoryRepository
import team.belloo.hamoney.persistence.SubCategoryRepository

@UseCase
class InitDefaultCategories(
    private val categoryRepository: CategoryRepository,
    private val subCategoryRepository: SubCategoryRepository
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
        val user: UserEntity,
        val accountBook: AccountBook
    )
}