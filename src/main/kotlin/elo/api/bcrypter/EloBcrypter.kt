@file:JvmName("EloBcrypter")
package elo.api.bcrypter

import org.mindrot.jbcrypt.BCrypt
import java.security.MessageDigest
import java.util.*

object EloBcrypter {

    private val base64Encoder by lazyOf(Base64.getEncoder())

    private fun applySHA256(data: String): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(data.toByteArray())
    }

    private fun hashPw(pw: String) = base64Encoder.encodeToString(applySHA256(pw))

    @JvmStatic
    fun encryptPassword(username: String, password: String): String {
        val usernameSha256Hash = applySHA256(username)
        val base64hash = BcryptBase64.encodeBcryptBase64(usernameSha256Hash, 16)
        val salt = "$2a$12$$base64hash"
        val hashedPw = Base64.getEncoder().encodeToString(applySHA256(password))
        return BCrypt.hashpw(hashedPw, salt)
    }

    fun checkPassword(password: String, encryptedPw: String): Boolean {
        return BCrypt.checkpw(hashPw(password), encryptedPw)
    }

    /**
     * @param bcryptedPassword resultado do {@link EloBcrypter.class#encryptPassword encryptPassword()}
     * @param salt salt gerado pelo serviço createLoginSalt
     */
    @JvmStatic
    fun createLoginChallenge(bcryptedPassword: String, salt: String): String {
        return BCrypt.hashpw(bcryptedPassword, salt)
    }

    @JvmStatic
    fun createLoginChallenge(username: String, password: String, salt: String): String {
        return createLoginChallenge(encryptPassword(username, password), salt)
    }
}