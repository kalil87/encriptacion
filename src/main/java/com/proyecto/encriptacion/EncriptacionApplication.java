package com.proyecto.encriptacion;

import com.proyecto.encriptacion.utils.SistemaNumeracion;
import com.proyecto.encriptacion.utils.TipoHashPassword;
import com.proyecto.encriptacion.utils.VariantesHash;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.util.Arrays;

@SpringBootApplication
public class EncriptacionApplication {
    private static final PasswordEncoder encoder = TipoHashPassword.BCRYPT;

    public static void main(String[] args) {
        String texto = "/api/encriptacion/usuarios/login";
        System.out.println("*** Conversion del texto '" + texto + "' en hash ***");
        System.out.println(generarMd5(texto));

        String encriptada = encoder.encode(texto);
        System.out.println("\n*** Conversion del texto '" + texto + "' en hash password ***");
        System.out.println(encriptada);

        System.out.println("\n*** matcheador del texto '" + texto + "' en hash password ***");
        System.out.println(encoder.matches(texto, encriptada));

        SpringApplication.run(EncriptacionApplication.class, args);
    }

    public static String generarMd5(String texto) {

        try {
            MessageDigest md5 = MessageDigest.getInstance(VariantesHash.MD5);

            byte[] hashBytes = md5.digest(texto.getBytes());

            System.out.println("\nHasheado sin conversion:");
            System.out.println(Arrays.toString(hashBytes));

            String hashString = "";
            for (byte b : hashBytes) {
                hashString += b;
            }

            System.out.println("\nHasheado y convertido a String:");
            System.out.println(hashString);
            System.out.println("\nHasheado, convertido a hexadecimal y devuelto en String:");

            return SistemaNumeracion.hexadecimal(hashBytes);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}