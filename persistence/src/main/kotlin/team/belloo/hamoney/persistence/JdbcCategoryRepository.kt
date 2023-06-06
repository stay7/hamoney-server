package team.belloo.hamoney.persistence

import team.belloo.hamoney.Repository
import team.belloo.hamoney.core.category.Category
import team.belloo.hamoney.core.category.CategoryRepository
import team.belloo.hamoney.core.category.SubCategory
import team.belloo.hamoney.entity.accountbook.CategoryEntity
import team.belloo.hamoney.entity.accountbook.SubCategoryEntity

@Repository
class JdbcCategoryRepository(
    private val jpaCategoryRepository: JpaCategoryRepository,
    private val jpaSubCategoryRepository: JpaSubCategoryRepository
) : CategoryRepository {

    override fun findAllByAccountBookId(accountBookId: Long): List<Category> {
        return jpaCategoryRepository.findAllByAccountBookId(accountBookId)
            .map { it.toDomain(jpaSubCategoryRepository.findByCategoryId(it.id).map { it.toDomain() }) }
    }

    override fun save(category: Category) {
        val categoryId = category.id

        jpaCategoryRepository.save(category.toEntity())
        category.subCategories.map {
            jpaSubCategoryRepository.save(it.toEntity(categoryId))
        }
    }

    private fun SubCategoryEntity.toDomain() = SubCategory(
        id = id,
        categoryId = categoryId,
        name = name,
        iconId = iconId
    )

    private fun CategoryEntity.toDomain(
        subCategories: List<SubCategory>
    ) = Category(
        id = id,
        accountBookId = accountBookId,
        name = name,
        subCategories = subCategories,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    private fun SubCategory.toEntity(categoryId: Long) = SubCategoryEntity().apply {
        id = this@toEntity.id
        this.categoryId = categoryId
        name = this@toEntity.name
        iconId = this@toEntity.iconId
    }

    private fun Category.toEntity() = CategoryEntity().apply {
        id = this@toEntity.id
        accountBookId = this@toEntity.accountBookId
        name = this@toEntity.name
        createdAt = this@toEntity.createdAt
        updatedAt = this@toEntity.updatedAt
    }
}