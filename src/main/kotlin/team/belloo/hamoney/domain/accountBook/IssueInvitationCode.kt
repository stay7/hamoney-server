package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.accountbook.AccountBookInvitation
import team.belloo.hamoney.core.accountbook.AccountBookInvitationRepository
import kotlin.random.Random

@UseCase
class IssueInvitationCode(
    private val accountBookInvitationRepository: AccountBookInvitationRepository
) {
    operator fun invoke(accountBookId: Long): AccountBookInvitation {
        var sixDigitCode: Long = 0

        while (true) {
            sixDigitCode = Random.nextLong(100000, Long.MAX_VALUE).toString().take(6).toLong()
            if (accountBookInvitationRepository.findByCode(sixDigitCode) == null) break
        }

        return AccountBookInvitation(accountBookId, sixDigitCode).let {
            accountBookInvitationRepository.save(it)
        }
    }
}