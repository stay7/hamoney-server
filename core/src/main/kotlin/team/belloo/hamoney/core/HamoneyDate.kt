package team.belloo.hamoney.core

data class HamoneyDate(
    val year: Int,
    val month: Int,
    val day: Int
) {
    init {
        require(year in 2020..2100)
        require(month in 1..12)
        require(day in 1..31)
    }

    companion object {
        fun of(expression: String): HamoneyDate {
            return expression.split("-").map { it.toInt() }.let {
                HamoneyDate(it[0], it[1], it[2])
            }
        }
    }

    fun format(): String = "$year-$month-$day"
}