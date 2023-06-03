package team.belloo.hamoney.persistence

import team.belloo.hamoney.Repository
import team.belloo.hamoney.core.user.User
import team.belloo.hamoney.core.user.UserRepository
import team.belloo.hamoney.entity.user.UserEntity

@Repository
class JdbcUserRepository(
    private val jpaUserRepository: JpaUserRepository,
) : UserRepository {
    override fun findById(id: Long): User? {
        return jpaUserRepository.findById(id).orElse(null)?.toDomain()
    }

    override fun findByEmail(email: String): User? {
        return jpaUserRepository.findByEmail(email)?.toDomain()
    }

    override fun findByUuid(uuid: String): User? {
        return jpaUserRepository.findByUuid(uuid)?.toDomain()
    }

    override fun findByNickname(nickname: String): User? {
        return jpaUserRepository.findByNickname(nickname)?.toDomain()
    }

    override fun save(user: User): User {
        return jpaUserRepository.save(user.toEntity()).toDomain()
    }

    private fun User.toEntity() = UserEntity().apply {
        id = this@toEntity.id
        uuid = this@toEntity.uuid
        email = this@toEntity.email
        nickname = this@toEntity.nickname
        profile = this@toEntity.profile
        status = when (this@toEntity.status) {
            User.Status.ACTIVE -> UserEntity.Status.ACTIVE.value
            User.Status.INACTIVE -> UserEntity.Status.INACTIVE.value
        }
        createdAt = this@toEntity.createdAt
        updatedAt = this@toEntity.updatedAt
    }

    private fun UserEntity.toDomain() = User(
        id = this@toDomain.id,
        uuid = this@toDomain.uuid,
        email = this@toDomain.email,
        nickname = this@toDomain.nickname,
        status = when (this@toDomain.status) {
            UserEntity.Status.ACTIVE.value -> User.Status.ACTIVE
            else -> throw IllegalStateException()
        },
        createdAt = this@toDomain.createdAt,
        updatedAt = this@toDomain.updatedAt,
    )
}
