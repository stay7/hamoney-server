package team.belloo.hamoney.core.pay

sealed class Pay(
    open val id: Long,
    open val name: String,
    open val iconId: Int,
    open val type: Type
) {
    data class Personal(
        override val id: Long,
        override val name: String,
        override val iconId: Int,
        val userId: Long
    ) : Pay(id, name, iconId, Type.Personal)

    data class Shared(
        override val id: Long,
        override val name: String,
        override val iconId: Int,
        val accountBookId: Long
    ) : Pay(id, name, iconId, Type.Shared)

    enum class Type {
        Personal, Shared;
    }
}