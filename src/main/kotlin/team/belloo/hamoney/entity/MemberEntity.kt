package team.belloo.hamoney.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Instant

@Entity
@Table(name = "member", indexes = [Index(name = "member_idx_account_book_id", columnList = "account_book_id")])
@IdClass(MemberEntity.PK::class)
@EntityListeners(AuditingEntityListener::class)
class MemberEntity {

    @Id
    @Column(nullable = false)
    var userId: Long = 0

    @Id
    @Column(nullable = false, name = "account_book_id")
    var accountBookId: Long = 0

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    var joinedAt: Instant = Instant.now()

    data class PK(
        val userId: Long = 0,
        val accountBookId: Long = 0
    ) : Serializable

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