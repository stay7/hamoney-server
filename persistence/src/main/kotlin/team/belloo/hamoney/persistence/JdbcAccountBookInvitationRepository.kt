package team.belloo.hamoney.persistence

import team.belloo.hamoney.Repository
import team.belloo.hamoney.core.accountbook.AccountBookInvitation
import team.belloo.hamoney.core.accountbook.AccountBookInvitationRepository
import team.belloo.hamoney.entity.accountbook.AccountBookInvitationEntity

@Repository
class JdbcAccountBookInvitationRepository(
    private val jpaRepository: JpaAccountBookInvitationRepository
) : AccountBookInvitationRepository {

    override fun findByCode(code: Long): AccountBookInvitation? {
        return jpaRepository.findByInvitationCode(code)?.toDomain()
    }

    override fun findByAccountBookId(accountBookId: Long): AccountBookInvitation? {
        return jpaRepository.findByAccountBookId(accountBookId)?.toDomain()
    }

    override fun save(accountBookInvitation: AccountBookInvitation): AccountBookInvitation {
        return jpaRepository.save(accountBookInvitation.toEntity()).toDomain()
    }

    override fun delete(accountBookInvitation: AccountBookInvitation) {
        return jpaRepository.delete(accountBookInvitation.toEntity())
    }

    private fun AccountBookInvitationEntity.toDomain() = AccountBookInvitation(
        accountBookId = accountBookId,
        code = invitationCode
    )

    private fun AccountBookInvitation.toEntity() = AccountBookInvitationEntity().apply {
        accountBookId = this@toEntity.accountBookId
        invitationCode = code
    }
}