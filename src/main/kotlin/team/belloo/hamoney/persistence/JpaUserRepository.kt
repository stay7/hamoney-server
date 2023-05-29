package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.user.UserEntity

interface JpaUserRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?

    fun findByUuid(uuid: String): UserEntity?

    fun findByNickname(nickname: String): UserEntity?
}