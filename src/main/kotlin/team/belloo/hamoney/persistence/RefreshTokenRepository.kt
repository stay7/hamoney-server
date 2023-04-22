package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.RefreshTokenEntity

interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, Long> {
}