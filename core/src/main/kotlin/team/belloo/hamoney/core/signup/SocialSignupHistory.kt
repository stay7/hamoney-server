package team.belloo.hamoney.core.signup

import java.time.Instant

data class SocialSignupHistory(
    val id: Long,
    val userId: Long,
    val email: String,
    val providerKey: String,
    val createdAt: Instant,
    val completedAt: Instant?
)