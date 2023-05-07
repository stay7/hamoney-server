package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.user.PersonalPayEntity

interface PersonalPayRepository : JpaRepository<PersonalPayEntity, Long> {
    fun findAllByUserId(userId: Long): List<PersonalPayEntity>
}