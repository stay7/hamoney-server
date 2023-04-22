package team.belloo.hamoney.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "member", indexes = [Index(name = "account_book_id", columnList = "account_book_id")])
@EntityListeners(AuditingEntityListener::class)
class MemberEntity {

    @Id
    @Column(nullable = false)
    var userId: Long = 0

    @Id
    @Column(nullable = false)
    var accountBookId: Long = 0

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var joinedAt: Instant = Instant.now()

    data class PK(
        val userId: Long,
        val accountBookId: Long
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemberEntity

        if (userId != other.userId) return false
        if (accountBookId != other.accountBookId) return false
        return joinedAt == other.joinedAt
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + accountBookId.hashCode()
        result = 31 * result + joinedAt.hashCode()
        return result
    }
}