package team.belloo.hamoney.core.accountbook

import java.time.Instant

data class AccountBook(
    val id: Long,
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant
)