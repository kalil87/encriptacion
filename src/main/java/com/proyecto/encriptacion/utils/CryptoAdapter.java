package com.proyecto.encriptacion.utils;

public interface CryptoAdapter {
    String encrypt(String plainText);

    String decrypt(String encryptedText);

    Long decryptLong(String encryptedText);
}
