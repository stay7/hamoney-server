package team.belloo.hamoney.core.accountbook

interface AccountBookInvitationRepository {
    fun findByCode(code: Long): AccountBookInvitation?

    fun findByAccountBookId(accountBookId: Long): AccountBookInvitation?

    fun save(accountBookInvitation: AccountBookInvitation): AccountBookInvitation

    fun delete(accountBookInvitation: AccountBookInvitation)
}