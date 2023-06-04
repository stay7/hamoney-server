package team.belloo.hamoney.core.member

interface MemberRepository {
    fun find(userId: Long, accountBookId: Long): Member?

    fun findAllByUserId(userId: Long): Set<Member>

    fun save(member: Member): Member
}