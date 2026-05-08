package com.proyecto.encriptacion.utils;

import com.proyecto.encriptacion.exception.CifradoInvalidoException;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AesCbcCryptoAdapter implements CryptoAdapter {
    private static final String AES_KEY = "0123456789abcdef0123456789abcdef";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 16;

    private final SecureRandom secureRandom = new SecureRandom();
    private final SecretKeySpec secretKeySpec;

    public AesCbcCryptoAdapter() {
        byte[] keyBytes = AES_KEY.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalArgumentException("La clave AES debe tener 16, 24 o 32 caracteres");
        }
        this.secretKeySpec = new SecretKeySpec(keyBytes, "AES");
    }

    @Override
    public String encrypt(String plainText) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            ByteBuffer buffer = ByteBuffer.allocate(iv.length + encrypted.length);
            buffer.put(iv);
            buffer.put(encrypted);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(buffer.array());
        } catch (GeneralSecurityException ex) {
            throw new CifradoInvalidoException("No se pudo cifrar el texto");
        }
    }

    @Override
    public String decrypt(String encryptedText) {
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(encryptedText);
            if (decoded.length <= IV_LENGTH) {
                throw new CifradoInvalidoException("Texto cifrado invalido");
            }

            byte[] iv = new byte[IV_LENGTH];
            byte[] encrypted = new byte[decoded.length - IV_LENGTH];
            System.arraycopy(decoded, 0, iv, 0, IV_LENGTH);
            System.arraycopy(decoded, IV_LENGTH, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
            return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException | GeneralSecurityException ex) {
            throw new CifradoInvalidoException("Texto cifrado invalido");
        }
    }

    @Override
    public Long decryptLong(String encryptedText) {
        try {
            return Long.valueOf(decrypt(encryptedText));
        } catch (NumberFormatException ex) {
            throw new CifradoInvalidoException("El identificador cifrado no es valido");
        }
    }
}
