package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.CategoryEntity

interface CategoryRepository : JpaRepository<CategoryEntity, Long> {
    fun findAllByAccountBookId(accountBookId: Long): List<CategoryEntity>
}