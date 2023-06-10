package team.belloo.hamoney.domain.category

import team.belloo.hamoney.core.category.Category
import team.belloo.hamoney.core.category.SubCategory
import team.belloo.hamoney.entity.accountbook.CategoryEntity
import team.belloo.hamoney.entity.accountbook.SubCategoryEntity

internal fun SubCategoryEntity.toDomain() = SubCategory(
    id = id,
    categoryId = categoryId,
    name = name,
    iconId = iconId
)

internal fun CategoryEntity.toDomain(
    subCategories: List<SubCategory>
) = Category(
    id = id,
    accountBookId = accountBookId,
    name = name,
    subCategories = subCategories,
    createdAt = createdAt,
    updatedAt = updatedAt
)

internal fun SubCategory.toEntity(categoryId: Long) = SubCategoryEntity().apply {
    id = this@toEntity.id
    this.categoryId = categoryId
    name = this@toEntity.name
    iconId = this@toEntity.iconId
}

internal fun Category.toEntity() = CategoryEntity().apply {
    id = this@toEntity.id
    accountBookId = this@toEntity.accountBookId
    name = this@toEntity.name
}