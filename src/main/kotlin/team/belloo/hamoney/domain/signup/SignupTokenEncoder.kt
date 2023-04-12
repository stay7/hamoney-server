package team.belloo.hamoney.domain.signup

import team.belloo.hamoney.UseCase
import java.security.MessageDigest

@UseCase
object SignupTokenEncoder {

    fun encode(email: String): String {
        MessageDigest.getInstance("SHA-256").apply {
            update(key(email).toByteArray())
        }.let {
            return it.digest().joinToString("") { "%02x".format(it) }
        }
    }

    private fun key(email: String): String = "happiness_${email}"
}