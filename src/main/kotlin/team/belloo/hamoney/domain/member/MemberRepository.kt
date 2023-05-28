package team.belloo.hamoney.domain.member

interface MemberRepository {
    fun findAllByUserId(userId: Long): Set<Member>

    fun save(member: Member): Member
}