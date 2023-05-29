package team.belloo.hamoney.core.member

import java.time.Instant

data class Member(
    val userId: Long,
    val accountBookId: Long,
    val joinedAt: Instant,
)