package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.accountbook.MemberEntity

interface MemberRepository : JpaRepository<MemberEntity, MemberEntity.PK> {
    fun findAllByAccountBookId(accountBookId: Long): List<MemberEntity>

    fun findAllByUserId(userId: Long): List<MemberEntity>
}