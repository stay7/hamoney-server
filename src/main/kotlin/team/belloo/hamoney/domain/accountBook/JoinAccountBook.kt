package team.belloo.hamoney.domain.accountBook

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.slf4j.LoggerFactory
import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.accountbook.AccountBook
import team.belloo.hamoney.core.accountbook.AccountBookInvitationRepository
import team.belloo.hamoney.core.accountbook.AccountBookRepository
import team.belloo.hamoney.core.member.Member
import team.belloo.hamoney.core.member.MemberRepository
import team.belloo.hamoney.core.user.User
import java.time.Clock

@UseCase
class JoinAccountBook(
    private val accountBookInvitationRepository: AccountBookInvitationRepository,
    private val accountBookRepository: AccountBookRepository,
    private val memberRepository: MemberRepository,
    private val clock: Clock
) {
    private val logger = LoggerFactory.getLogger(javaClass::class.java)

    operator fun invoke(
        user: User,
        code: Long,
    ): Either<Error, AccountBook> {

        val invitation = accountBookInvitationRepository.findByCode(code) ?: return Error.INVITATION_NOT_EXIST.left()
        val accountBook = accountBookRepository.findById(invitation.accountBookId)
            ?: throw IllegalStateException("accountBook not exist ${invitation.accountBookId}")

        runCatching {
            memberRepository.save(Member(user.id, accountBook.id, clock.instant()))
        }.getOrElse {
            logger.error(it.toString())
            return Error.ALREADY_JOINED.left()
        }

        accountBookInvitationRepository.delete(invitation)
        return accountBook.right()
    }

    enum class Error {
        INVITATION_NOT_EXIST, ALREADY_JOINED
    }
}