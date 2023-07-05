package team.belloo.hamoney.core.spending

enum class SpendingRepeat(val value: String) {
    DAILY("daily"), WEEKLY("weekly"), MONTHLY("monthly"), YEARLY("yearly");

    companion object {
        fun of(value: String): SpendingRepeat = SpendingRepeat.values().find { it.value == value }!!
    }
}