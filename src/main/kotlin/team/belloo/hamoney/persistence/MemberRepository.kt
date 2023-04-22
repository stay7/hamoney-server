package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.MemberEntity

interface MemberRepository : JpaRepository<MemberEntity, MemberEntity.PK> {
}