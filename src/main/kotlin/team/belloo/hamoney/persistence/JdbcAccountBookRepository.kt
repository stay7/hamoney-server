package team.belloo.hamoney.persistence

import team.belloo.hamoney.Repository
import team.belloo.hamoney.domain.accountBook.AccountBook
import team.belloo.hamoney.domain.accountBook.AccountBookRepository
import team.belloo.hamoney.entity.accountbook.AccountBookEntity
import team.belloo.hamoney.persistence.jpa.JpaAccountBookRepository

@Repository
class JdbcAccountBookRepository(
    private val jpaAccountBookRepository: JpaAccountBookRepository
) : AccountBookRepository {

    override fun findById(id: Long): AccountBook? = jpaAccountBookRepository.findById(id).orElse(null)?.toDomain()

    override fun save(accountBook: AccountBook): AccountBook {
        return jpaAccountBookRepository.save(accountBook.toEntity()).toDomain()
    }

    private fun AccountBook.toEntity(): AccountBookEntity = AccountBookEntity().apply {
        id = this@toEntity.id
        name = this@toEntity.name
        createdAt = this@toEntity.createdAt
        updatedAt = this@toEntity.updatedAt
    }

    private fun AccountBookEntity.toDomain(): AccountBook = AccountBook(
        id = this.id,
        name = this.name,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}