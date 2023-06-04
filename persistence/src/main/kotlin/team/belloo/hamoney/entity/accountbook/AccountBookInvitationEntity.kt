package team.belloo.hamoney.entity.accountbook

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@IdClass(AccountBookInvitationEntity.PK::class)
@Table(name = "account_book_invitation")
class AccountBookInvitationEntity {
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    var accountBookId: Long = 0

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    var invitationCode: Long = 0

    data class PK(
        val accountBookId: Long = 0,
        val invitationCode: Long = 0
    ) : Serializable

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AccountBookInvitationEntity

        if (accountBookId != other.accountBookId) return false
        return invitationCode == other.invitationCode
    }

    override fun hashCode(): Int {
        var result = accountBookId.hashCode()
        result = 31 * result + invitationCode.hashCode()
        return result
    }
}