package team.belloo.hamoney.domain.accountBook

interface AccountBookRepository {
    fun findById(id: Long): AccountBook?

    fun save(accountBook: AccountBook): AccountBook
}