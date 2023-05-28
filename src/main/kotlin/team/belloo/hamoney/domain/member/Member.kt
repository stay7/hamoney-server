package team.belloo.hamoney.domain.member

import java.time.Instant

data class Member(
    val userId: Long,
    val accountBookId: Long,
    val joinedAt: Instant,
)