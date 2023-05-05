package team.belloo.hamoney.domain.category

object DefaultCategories {

    operator fun invoke(
        accountBookId: Long
    ): List<Category> {
        return listOf(
            Category(
                id = 0,
                accountBookId = accountBookId,
                name = "음식",
                subCategories = emptyList()
            ),
            Category(
                id = 0,
                accountBookId = accountBookId,
                name = "생활",
                subCategories = emptyList()
            ),
            Category(
                id = 0,
                accountBookId = accountBookId,
                name = "교통",
                subCategories = emptyList()
            ),
            Category(
                id = 0,
                accountBookId = accountBookId,
                name = "문화",
                subCategories = emptyList()
            ),
            Category(
                id = 0,
                accountBookId = accountBookId,
                name = "미래",
                subCategories = emptyList()
            ),
            Category(
                id = 0,
                accountBookId = accountBookId,
                name = "기타",
                subCategories = emptyList()
            )
        )
    }
}