package team.belloo.hamoney.domain.category

import team.belloo.hamoney.core.category.Category
import team.belloo.hamoney.UseCase
import team.belloo.hamoney.persistence.JpaCategoryRepository
import team.belloo.hamoney.persistence.JpaSubCategoryRepository

@UseCase
class FindCategory(
    private val categoryRepository: JpaCategoryRepository,
    private val subCategoryRepository: JpaSubCategoryRepository
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