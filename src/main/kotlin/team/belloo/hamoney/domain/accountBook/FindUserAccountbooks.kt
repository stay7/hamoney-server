package team.belloo.hamoney.domain.accountBook

import team.belloo.hamoney.UseCase
import team.belloo.hamoney.persistence.jpa.JpaMemberRepository

@UseCase
class FindUserAccountbooks(
    private val memberRepository: JpaMemberRepository,
    private val accountBookRepository: AccountBookRepository
) {

}