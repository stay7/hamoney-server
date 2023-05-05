package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.SubCategoryEntity

interface SubCategoryRepository : JpaRepository<SubCategoryEntity, Long> {
    fun findByCategoryId(categoryId: Long): List<SubCategoryEntity>
}