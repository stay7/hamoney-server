package team.belloo.hamoney.core.member

interface MemberRepository {
    fun findAllByUserId(userId: Long): Set<Member>

    fun save(member: Member): Member
}