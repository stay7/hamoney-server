package team.belloo.hamoney.domain.category

import team.belloo.hamoney.core.category.Category
import team.belloo.hamoney.UseCase
import team.belloo.hamoney.persistence.CategoryRepository
import team.belloo.hamoney.persistence.SubCategoryRepository

@UseCase
class FindCategory(
    private val categoryRepository: CategoryRepository,
    private val subCategoryRepository: SubCategoryRepository
) {

    fun byId(id: Long): Category? {
        return categoryRepository.findById(id).orElse(null)?.let { it ->
            Category(
                id = it.id,
                accountBookId = it.accountBookId,
                name = it.name,
                subCategories = subCategoryRepository.findByCategoryId(it.id).map { entity -> entity.toDomain() }
            )
        }
    }

    fun allByAccountBookId(accountBookId: Long): List<Category> {
        return categoryRepository.findAllByAccountBookId(accountBookId).map { it ->
            Category(
                id = it.id,
                accountBookId = it.accountBookId,
                name = it.name,
                subCategories = subCategoryRepository.findByCategoryId(it.id).map { entity -> entity.toDomain() }
            )
        }
    }
}