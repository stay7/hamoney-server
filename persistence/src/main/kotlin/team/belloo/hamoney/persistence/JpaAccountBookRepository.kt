package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.accountbook.AccountBookEntity

interface JpaAccountBookRepository : JpaRepository<AccountBookEntity, Long>
