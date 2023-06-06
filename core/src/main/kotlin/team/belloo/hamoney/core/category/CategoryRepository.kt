package team.belloo.hamoney.core.category

interface CategoryRepository {
    fun findAllByAccountBookId(accountBookId: Long): List<Category>

    fun save(category: Category)
}