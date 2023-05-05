package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.PersonalPayEntity

interface PersonalPayRepository : JpaRepository<PersonalPayEntity, Long> {
}