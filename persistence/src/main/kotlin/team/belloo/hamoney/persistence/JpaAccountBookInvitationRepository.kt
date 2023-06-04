package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.accountbook.AccountBookInvitationEntity

interface JpaAccountBookInvitationRepository :
    JpaRepository<AccountBookInvitationEntity, AccountBookInvitationEntity.PK> {
    fun findByAccountBookId(accountBookId: Long): AccountBookInvitationEntity?

    fun findByInvitationCode(invitationCode: Long): AccountBookInvitationEntity?
}