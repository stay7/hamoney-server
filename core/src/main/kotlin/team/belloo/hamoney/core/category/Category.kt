package team.belloo.hamoney.core.category

data class Category(
    val id: Long,
    val accountBookId: Long,
    val name: String,
    val subCategories: List<SubCategory>
)