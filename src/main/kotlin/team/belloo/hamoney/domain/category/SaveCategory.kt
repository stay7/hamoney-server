package team.belloo.hamoney.domain.category

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.persistence.CategoryRepository
import team.belloo.hamoney.persistence.SubCategoryRepository

@UseCase
class SaveCategory(
    private val categoryRepository: CategoryRepository,
    private val subCategoryRepository: SubCategoryRepository
) {

    fun save(category: Category) {
        val categoryEntity = categoryRepository.save(category.toEntity())
        val subCategories = category.subCategories.map { it.toEntity(categoryEntity.id) }
        subCategoryRepository.saveAll(subCategories)
    }
}