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
import java.time.Instant

@Entity
@Table(
    name = "pay",
    indexes = [Index(name = "pay_idx_ref_id", columnList = "ref_id")]
)
@EntityListeners(AuditingEntityListener::class)
class PayEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false, name = "ref_id")
    var refId: Long = 0

    @Column(nullable = false, columnDefinition = "TINYINT")
    var type: Type = Type.PERSONAL

    @Column(nullable = false)
    var name: String = ""

    @Column(nullable = false)
    var iconId: Int = 0

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Instant = Instant.now()

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Instant = Instant.now()

    enum class Type(
        val value: Int
    ) {
        PERSONAL(0), SHARED(1);

        companion object {
            fun of(value: Int) = values().find { it.value == value }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PayEntity

        if (id != other.id) return false
        if (refId != other.refId) return false
        if (type != other.type) return false
        if (name != other.name) return false
        if (iconId != other.iconId) return false
        if (createdAt != other.createdAt) return false
        return updatedAt == other.updatedAt
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + refId.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + iconId
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        return result
    }
}