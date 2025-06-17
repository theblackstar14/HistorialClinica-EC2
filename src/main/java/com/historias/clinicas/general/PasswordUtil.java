package com.historias.clinicas.general;

import org.mindrot.jbcrypt.BCrypt;

/** Helper estático para hashear y verificar contraseñas. */
public final class PasswordUtil {

    private PasswordUtil() {}

    /** Devuelve el hash de la contraseña en texto plano. */
    public static String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    /** Verifica que la contraseña en texto plano coincida con el hash almacenado. */
    public static boolean verify(String plain, String hash) {
        return BCrypt.checkpw(plain, hash);
    }
}
