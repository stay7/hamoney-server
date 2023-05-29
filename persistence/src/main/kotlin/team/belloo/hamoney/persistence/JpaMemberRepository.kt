package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.accountbook.MemberEntity

interface JpaMemberRepository : JpaRepository<MemberEntity, MemberEntity.PK> {
    fun findAllByUserId(userId: Long): List<MemberEntity>
}
