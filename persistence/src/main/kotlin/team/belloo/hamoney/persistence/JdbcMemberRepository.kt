package team.belloo.hamoney.persistence

import team.belloo.hamoney.Repository
import team.belloo.hamoney.core.member.Member
import team.belloo.hamoney.core.member.MemberRepository
import team.belloo.hamoney.entity.accountbook.MemberEntity

@Repository
class JdbcMemberRepository(
    private val jpaMemberRepository: JpaMemberRepository,
) : MemberRepository {
    override fun find(userId: Long, accountBookId: Long): Member? {
        return jpaMemberRepository.findByUserIdAndAccountBookId(userId, accountBookId)?.toDomain()
    }

    override fun findAllByUserId(userId: Long): Set<Member> {
        return jpaMemberRepository.findAllByUserId(userId).map { it.toDomain() }.toSet()
    }

    override fun save(member: Member): Member {
        return jpaMemberRepository.save(member.toEntity()).toDomain()
    }

    private fun MemberEntity.toDomain(): Member = Member(
        userId = this.userId,
        accountBookId = this.accountBookId,
        joinedAt = this.joinedAt,
    )

    private fun Member.toEntity(): MemberEntity = MemberEntity().apply {
        userId = this@toEntity.userId
        accountBookId = this@toEntity.accountBookId
        joinedAt = this@toEntity.joinedAt
    }
}
