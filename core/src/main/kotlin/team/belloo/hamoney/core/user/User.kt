package team.belloo.hamoney.core.user

import java.time.Instant

data class User(
    val id: Long,
    val uuid: String,
    val email: String,
    val profile: String = "",
    val nickname: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val status: Status
) {
    enum class Status {
        ACTIVE, INACTIVE
    }
}