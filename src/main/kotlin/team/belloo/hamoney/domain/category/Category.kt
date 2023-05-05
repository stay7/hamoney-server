package team.belloo.hamoney.domain.category

data class Category(
    val id: Long,
    val accountBookId: Long,
    val name: String,
    val subCategories: List<SubCategory>
)