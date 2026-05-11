package com.proyecto.encriptacion.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioCreateRequest(

        @NotBlank(message = "El usuario no puede estar vacío")
        @Size(min = 6, max = 12, message = "El usuario debe tener entre 6 y 12 caracteres")
        String usuario,

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email no es válido")
        String email,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 8, max = 12, message = "La contraseña debe tener entre 8 y 12 caracteres")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,12}$",
                message = "La contraseña debe tener al menos 1 mayúscula, 1 minúscula, 1 número y 1 caracter especial")
        String password

) {}