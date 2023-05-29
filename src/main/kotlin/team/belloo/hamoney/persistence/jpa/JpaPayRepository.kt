package team.belloo.hamoney.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.PayEntity

interface JpaPayRepository : JpaRepository<PayEntity, Long> {
    fun findAllByRefIdAndType(refId: Long, type: PayEntity.Type): List<PayEntity>
}