package team.belloo.hamoney.entity.accountbook

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "category", indexes = [Index(name = "category_idx_account_book_id", columnList = "account_book_id")])
class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    var id: Long = 0

    @Column(nullable = false, name = "account_book_id")
    var accountBookId: Long = 0

    @Column(updatable = false, nullable = false)
    var name: String = ""

    @Column
    var createdAt: Instant = Instant.now()

    @Column
    var updatedAt: Instant = Instant.now()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryEntity

        if (id != other.id) return false
        if (accountBookId != other.accountBookId) return false
        if (name != other.name) return false
        if (createdAt != other.createdAt) return false
        return updatedAt == other.updatedAt
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + accountBookId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        return result
    }
}
