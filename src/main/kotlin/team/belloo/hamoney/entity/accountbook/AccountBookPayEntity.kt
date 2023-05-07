package team.belloo.hamoney.entity.accountbook

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
    name = "account_book_pay",
    indexes = [Index(name = "account_book_pay_idx_account_book_id", columnList = "account_book_id")]
)
@EntityListeners(AuditingEntityListener::class)
class AccountBookPayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "account_book_id", nullable = false)
    var accountBookId: Long = 0

    @Column
    var name: String = ""

    @Column
    var iconId: Int = 0

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Instant = Instant.now()

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Instant = Instant.now()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AccountBookPayEntity

        if (id != other.id) return false
        if (accountBookId != other.accountBookId) return false
        if (name != other.name) return false
        if (iconId != other.iconId) return false
        if (createdAt != other.createdAt) return false
        return updatedAt == other.updatedAt
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + accountBookId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + iconId
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        return result
    }
}