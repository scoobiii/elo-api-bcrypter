package elo.api.bcrypter

import org.junit.Test
import org.mindrot.jbcrypt.BCrypt

class TestPasswordHash {

    private val username = "User_123"//"79515337003"
    private val password = "A1234567890"
    private val loginSalt = BCrypt.gensalt(12)

    @Test
    fun passwordEncryptedSuccessfully() {
        val encrypted = EloBcrypter.encryptPassword(username, password)
        println("BCrypted Password: $encrypted")
        println("LoginSalt: $loginSalt")
        assert(EloBcrypter.checkPassword(password, encrypted))
        assert(encrypted.length == 60)
    }

    @Test
    fun generateLoginChallenge() {
        val challenge = EloBcrypter.createLoginChallenge(username, password, loginSalt)
        println("Challenge: $challenge")
        assert(challenge.isNotEmpty())
        assert(challenge.length == 60)
    }

}