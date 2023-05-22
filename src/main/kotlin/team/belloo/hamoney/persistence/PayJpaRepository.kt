package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.PayEntity

interface PayJpaRepository : JpaRepository<PayEntity, Long> {
    fun findAllByRefIdAndType(refId: Long, type: PayEntity.Type): List<PayEntity>
}