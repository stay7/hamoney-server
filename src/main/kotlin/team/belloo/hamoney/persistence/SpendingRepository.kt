package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.SpendingEntity

interface SpendingRepository : JpaRepository<SpendingEntity, Long> {
}