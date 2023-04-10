package team.belloo.hamoney.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import team.belloo.hamoney.domain.signup.SocialProvider
import java.time.Instant

@Entity
@Table(name = "social_signup", indexes = [Index(name = "idx_user_id", columnList = "userId")])
@EntityListeners(AuditingEntityListener::class)
class SocialSignupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    var id: Long = 0

    @Column(nullable = false)
    var userId: Long = 0

    @Column(nullable = false, unique = true)
    var providerKey: String = "" // kakao_1234

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    var completedAt: Instant? = null

    fun providerKey(provider: SocialProvider, providerId: String) = "${provider.value}_${providerId}"
}