package team.belloo.hamoney.domain.user

interface UserRepository {
    fun findById(id: Long): User?

    fun findByEmail(email: String): User?

    fun findByUuid(uuid: String): User?

    fun findByNickname(nickname: String): User?

    fun save(user: User): User
}