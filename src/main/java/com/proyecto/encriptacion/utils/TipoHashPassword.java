package com.proyecto.encriptacion.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class TipoHashPassword {

    private TipoHashPassword() {}

    public static final PasswordEncoder BCRYPT =  new BCryptPasswordEncoder();
    public static final PasswordEncoder PBKDF2 =  Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    public static final PasswordEncoder ARGON2 =
            new Argon2PasswordEncoder(
                    16,
                    32,
                    1,
                    65536,
                    3);
}