package team.belloo.hamoney.webapi

import team.belloo.hamoney.core.category.Category
import team.belloo.hamoney.core.category.SubCategory
import team.belloo.hamoney.core.pay.Pay

data class AccountBookView(
    val id: Long,
    val name: String,
    val categories: List<CategoryView>,
    val payments: List<PayView>,
    val createdAt: Long
)

data class CategoryView(
    val id: Long,
    val name: String,
    val subCategories: List<SubCategoryView>
)

data class SubCategoryView(
    val id: Long,
    val name: String,
    val iconId: Int
)

data class PayView(
    val id: Long,
    val name: String,
    val iconId: Int
)

internal fun Category.toView() = CategoryView(
    id = id,
    name = name,
    subCategories = subCategories.map { it.toView() }
)

internal fun SubCategory.toView() = SubCategoryView(
    id = id,
    name = name,
    iconId = iconId
)

internal fun Pay.toView() = PayView(
    id = this@toView.id,
    name = this@toView.name,
    iconId = this@toView.iconId
)