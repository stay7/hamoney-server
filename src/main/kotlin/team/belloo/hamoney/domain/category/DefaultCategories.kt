package team.belloo.hamoney.domain.category

class DefaultCategories {

    companion object {
        val DEFAULTS = listOf(
            DefaultCategory(
                name = "음식",
                subCategories = listOf(
                    DefaultSubCategory("집밥먹어요", 0),
                    DefaultSubCategory("시켜먹어요", 0),
                    DefaultSubCategory("외식해요", 0),
                    DefaultSubCategory("카페가요", 0)
                )
            ),
            DefaultCategory(
                name = "생활",
                subCategories = listOf(
                    DefaultSubCategory("주거비", 0),
                    DefaultSubCategory("관리비", 0),
                    DefaultSubCategory("공과금", 0),
                    DefaultSubCategory("생활비", 0)
                )
            ),
            DefaultCategory(
                name = "교통",
                subCategories = listOf(
                    DefaultSubCategory("BMW", 0),
                    DefaultSubCategory("공공자전거", 0),
                )
            ),
            DefaultCategory(
                name = "문화",
                subCategories = listOf(
                    DefaultSubCategory("배워요", 0),
                    DefaultSubCategory("전시가요", 0),
                )
            ),
            DefaultCategory(
                name = "미래",
                subCategories = listOf(
                    DefaultSubCategory("저금해요", 0),
                    DefaultSubCategory("경조사비", 0),
                )
            ),
            DefaultCategory(
                name = "기타",
                subCategories = listOf(
                    DefaultSubCategory("병원가요", 0),
                    DefaultSubCategory("반려동물", 0),
                )
            )
        )
    }

    data class DefaultCategory(
        val name: String,
        val subCategories: List<DefaultSubCategory>
    )

    data class DefaultSubCategory(
        val name: String,
        val iconId: Int
    )
}