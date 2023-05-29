package team.belloo.hamoney.domain.user

import java.time.Instant

data class User(
    val id: Long,
    val uuid: String,
    val email: String,
    val nickname: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val status: Status
) {
    enum class Status {
        ACTIVE, INACTIVE
    }
}