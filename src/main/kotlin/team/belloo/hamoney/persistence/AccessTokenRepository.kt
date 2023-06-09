package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.oauth.AccessTokenEntity

interface AccessTokenRepository : JpaRepository<AccessTokenEntity, Long> {
    fun findByToken(token: String): AccessTokenEntity?
}