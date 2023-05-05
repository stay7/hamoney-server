package team.belloo.hamoney.domain.category

data class SubCategory(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val iconId: Int
)