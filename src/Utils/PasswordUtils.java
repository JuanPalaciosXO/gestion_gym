
package Utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Convierte una contraseña en un hash seguro
    public static String encriptar(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // Verifica una contraseña contra su hash
    public static boolean verificar(String password, String hash) {

        if (password == null || hash == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(password, hash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
