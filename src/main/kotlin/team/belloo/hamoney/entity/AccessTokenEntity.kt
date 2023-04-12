package team.belloo.hamoney.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Duration
import java.time.Instant

@Entity
@Table(name = "access_token")
@EntityListeners(AuditingEntityListener::class)
class AccessTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    var id: Long = 0

    @Column(nullable = false)
    var userId: Long = 0

    @Column(nullable = false, unique = true)
    var token: String = ""

    @Column(nullable = false)
    var status: String = "active"

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    var createdAt: Instant = Instant.now()

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    var expiredAt: Instant = Instant.now().plus(Duration.ofDays(7))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AccessTokenEntity

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (token != other.token) return false
        if (createdAt != other.createdAt) return false
        return expiredAt == other.expiredAt
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + token.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + expiredAt.hashCode()
        return result
    }
}