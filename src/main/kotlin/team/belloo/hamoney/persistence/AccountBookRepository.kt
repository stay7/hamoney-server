package team.belloo.hamoney.persistence

import org.springframework.data.jpa.repository.JpaRepository
import team.belloo.hamoney.entity.AccountBookEntity

interface AccountBookRepository : JpaRepository<AccountBookEntity, Long> {}