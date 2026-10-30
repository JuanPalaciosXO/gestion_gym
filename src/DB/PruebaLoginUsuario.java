
package DB;

import Controllers.UsuarioController;
import Models.Usuario;

public class PruebaLoginUsuario {

    public static void main(String[] args) {

        UsuarioController controller = new UsuarioController();

        // PRUEBA 1: Contraseña correcta
        Usuario usuario = controller.IniciarSesion(
                "TEST20261030B",
                "PruebaSegura123!"
        );

        if (usuario != null) {
            System.out.println("LOGIN EXITOSO");
            System.out.println("Bienvenido: " + usuario.getNombre());
        } else {
            System.out.println("LOGIN FALLIDO");
        }

        // PRUEBA 2: Contraseña incorrecta
        Usuario usuarioIncorrecto = controller.IniciarSesion(
                "TEST20261030B",
                "ClaveIncorrecta123"
        );

        if (usuarioIncorrecto == null) {
            System.out.println("Contraseña incorrecta rechazada correctamente.");
        } else {
            System.out.println("ERROR: Se permitió una contraseña incorrecta.");
        }
    }
}

