package team.belloo.hamoney.domain.accountBook

import java.time.Instant

data class AccountBook(
    val id: Long,
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant
)