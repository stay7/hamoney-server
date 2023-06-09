package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.signup.SocialSignupEntity

interface JpaSocialSignupRepository : JpaRepository<SocialSignupEntity, Long> {
    fun findByEmail(email: String): SocialSignupEntity?

    fun findByProviderKey(providerKey: String): SocialSignupEntity?
}