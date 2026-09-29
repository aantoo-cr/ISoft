package cl.ufro.dci.naive_pay_api.payments.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordHashUtil {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 128;

    public String createPasswordHashWithGivenSalt(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte [] saltBytes = convertIntToBytes(salt);
        byte[] hash = createHash(password, saltBytes);
        return Base64.getEncoder().encodeToString(hash);
    }

    private byte[] convertIntToBytes(String salt) {
        return salt.getBytes();
    }

    private static byte[] createHash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return skf.generateSecret(spec).getEncoded();
    }

}
