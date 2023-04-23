package team.belloo.hamoney.domain.category

data class Category(
    val name: String,
    val iconId: String
) {
    companion object {
        fun defaultCategories() = listOf(
            Category("식재료", "icon_grocery"),
            Category("외식", "icon_restaurant"),
            Category("커피", "icon_coffee"),
            Category("교통비", "icon_traffic"),
            Category("생활비", "icon_lifestyle"),
            Category("문화생활", "icon_curture"),
            Category("미분류", "icon_etc"),
        )
    }
}