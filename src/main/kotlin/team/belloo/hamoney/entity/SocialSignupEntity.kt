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

    @Column(nullable = false)
    var email: String = ""

    @Column(nullable = false, unique = true)
    var providerKey: String = "" // kakao_1234

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    var completedAt: Instant? = null

    fun providerKey(provider: SocialProvider, providerId: String) = "${provider.value}_${providerId}"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SocialSignupEntity

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (email != other.email) return false
        if (providerKey != other.providerKey) return false
        if (createdAt != other.createdAt) return false
        return completedAt == other.completedAt
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + providerKey.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + (completedAt?.hashCode() ?: 0)
        return result
    }
}