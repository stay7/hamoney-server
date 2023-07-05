package team.belloo.hamoney.core

@JvmInline
value class HamoneyAmount(val value: Long) {
    init {
        require(value >= 0)
    }
}
