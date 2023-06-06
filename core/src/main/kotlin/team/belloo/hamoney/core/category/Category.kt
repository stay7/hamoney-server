package team.belloo.hamoney.core.category

import java.time.Instant

data class Category(
    val id: Long,
    val accountBookId: Long,
    val name: String,
    val subCategories: List<SubCategory>,
    val createdAt: Instant,
    val updatedAt: Instant,
)