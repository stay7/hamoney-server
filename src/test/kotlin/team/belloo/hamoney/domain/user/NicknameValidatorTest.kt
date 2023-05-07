package team.belloo.hamoney.domain.user

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class NicknameValidatorTest {

    @Test
    fun `validated nicknames`() {
        val candidates = listOf(
            "abcd", "abc.d", "abcd.e", "abc d", " abcd", "abcd ", "abcd_a", "abcdefghijklmn", "ab12345.6789.d"
        )

        assertTrue { candidates.all { NicknameValidator.validate(it) } }
    }

    @Test
    fun `not validated nicknames`() {
        val candidates = listOf(
            "a", "ab", "abc", "abc.", ".abc", "abcd.", "abcd_", "abcdefghijklmno", "abcdefg.hijklmn"
        )

        assertTrue { candidates.all { !NicknameValidator.validate(it) } }
    }
}