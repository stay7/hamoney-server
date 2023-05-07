package team.belloo.hamoney.domain.user

object NicknameValidator {
    const val MAX_LENGTH = 14
    const val MIN_LENGTH = 4
    val regex = Regex("^([a-zA-Z0-9]+[\\._]?)*[a-zA-Z0-9]+\$")

    fun validate(nickname: String): Boolean {
        val candidate = nickname.replace(" ", "")

        if (candidate.length !in MIN_LENGTH..MAX_LENGTH) return false
        return candidate.matches(regex)
    }
}