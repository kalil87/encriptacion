package com.proyecto.encriptacion.utils;

import java.util.Base64;
import java.util.HexFormat;

public final class SistemaNumeracion {

    private SistemaNumeracion() {
    }

    public static String hexadecimal(byte[] bytes) {
        return HexFormat.of().formatHex(bytes);
    }

    public static String base64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}