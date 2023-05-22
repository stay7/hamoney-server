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
import java.time.Instant

// SpendingDetail이 필요할까?
// TODO: 필요한 인덱스 걸자
@Entity
@Table(name = "spending")
@EntityListeners(AuditingEntityListener::class)
class SpendingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var accountBookId: Long = 0

    @Column(nullable = false)
    var amount: Long? = null

    @Column(nullable = false)
    var date: String = ""

    @Column(nullable = false)
    var categoryId: Long = 0

    @Column(nullable = true)
    var subCategoryId: Long? = null

    @Column(nullable = true)
    var payId: Long = 0

    @Column(nullable = true)
    var used: String? = null

    @Column(nullable = false)
    var skipSum: Boolean = false

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Instant = Instant.now()

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Instant = Instant.now()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SpendingEntity

        if (id != other.id) return false
        if (accountBookId != other.accountBookId) return false
        if (amount != other.amount) return false
        if (date != other.date) return false
        if (categoryId != other.categoryId) return false
        if (subCategoryId != other.subCategoryId) return false
        if (payId != other.payId) return false
        if (used != other.used) return false
        if (skipSum != other.skipSum) return false
        if (createdAt != other.createdAt) return false
        return updatedAt == other.updatedAt
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + accountBookId.hashCode()
        result = 31 * result + (amount?.hashCode() ?: 0)
        result = 31 * result + date.hashCode()
        result = 31 * result + categoryId.hashCode()
        result = 31 * result + (subCategoryId?.hashCode() ?: 0)
        result = 31 * result + payId.hashCode()
        result = 31 * result + (used?.hashCode() ?: 0)
        result = 31 * result + skipSum.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        return result
    }
}