package team.belloo.hamoney.entity.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import java.time.Instant

@Entity
@Table(name = "users")
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    var id: Long = 0

    @Column(updatable = false, nullable = false, unique = true)
    var uuid: String = ""

    @Column(unique = true)
    var email: String = ""

    @Column
    var nickname: String = ""

    @Column
    var profile: String = ""

    @Column
    var status: Int = Status.ACTIVE.value

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var signedAt: Instant? = null

    @Column
    var createdAt: Instant = Instant.now()

    @Column
    var updatedAt: Instant = Instant.now()

    enum class Status(val value: Int) {
        ACTIVE(0), INACTIVE(1)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserEntity

        if (id != other.id) return false
        if (uuid != other.uuid) return false
        if (email != other.email) return false
        if (nickname != other.nickname) return false
        if (status != other.status) return false
        if (signedAt != other.signedAt) return false
        if (createdAt != other.createdAt) return false
        return updatedAt == other.updatedAt
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + uuid.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + nickname.hashCode()
        result = 31 * result + status
        result = 31 * result + (signedAt?.hashCode() ?: 0)
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        return result
    }
}
