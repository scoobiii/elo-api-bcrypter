package elo.api.bcrypter;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

public class JTestPasswordHash {

    private final String username = "User_123";
    private final String password = "A1234567890";
    private final String loginSalt = BCrypt.gensalt(12);

    @Test
    public void passwordEncryptedSuccessfully() {
        String encrypted = EloBcrypter.encryptPassword(username, password);
        ("BCrypted Password: $encrypted")
        println("LoginSalt: $loginSalt")
        assert(EloBcrypter.checkPassword(password, encrypted))
        assert(encrypted.length == 60)
    }

    @Test
    public void generateLoginChallenge() {
        val challenge = EloBcrypter.createLoginChallenge(username, password, loginSalt)
        println("Challenge: $challenge")
        assert(challenge.isNotEmpty())
        assert(challenge.length == 60)
    }

}
