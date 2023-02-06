package br.com.vrbeneficios.miniauthorizator.util.implementation;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Service;

import br.com.vrbeneficios.miniauthorizator.util.interfaces.HashGenerator;

@Service
public class HashGeneratorService implements HashGenerator {

    private final int ITERATIONS = 10000;
    private final int KEY_LENGTH = 256;
    private final String SECRET_INSTANCE = "PBKDF2WithHmacSHA1";

    @Override
    public String generateSaltHash(String text, String salt) {

        char[] charText = text.toCharArray();
        byte[] byteSalt = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(charText, byteSalt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(charText, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(SECRET_INSTANCE);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return convertHashIntoAString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Erro ao gerar o HASH.", e);
        } finally {
            spec.clearPassword();
        }
    }

    private String convertHashIntoAString(byte[] hash) {
        return Base64.getEncoder().encodeToString(hash);
    }

}
