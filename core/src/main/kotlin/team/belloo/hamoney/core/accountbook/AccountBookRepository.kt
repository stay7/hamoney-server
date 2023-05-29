package team.belloo.hamoney.core.accountbook

interface AccountBookRepository {
    fun findById(id: Long): AccountBook?

    fun save(accountBook: AccountBook): AccountBook
}