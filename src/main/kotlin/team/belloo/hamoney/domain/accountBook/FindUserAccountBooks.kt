package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.core.accountbook.AccountBook
import team.belloo.hamoney.core.accountbook.AccountBookRepository
import team.belloo.hamoney.core.member.MemberRepository

@UseCase
class FindUserAccountBooks(
    private val memberRepository: MemberRepository,
    private val accountBookRepository: AccountBookRepository
) {

    operator fun invoke(userId: Long): Set<AccountBook> {
        return memberRepository.findAllByUserId(userId)
            .map { it.accountBookId }
            .map { accountBookRepository.findById(it)!! }
            .toSet()
    }
}