
package DB;

import Controllers.UsuarioController;
import java.time.LocalDate;
import java.util.ArrayList;

public class PruebaRegistroUsuario {

    public static void main(String[] args) {

        UsuarioController controller = new UsuarioController();

        boolean registrado = controller.Guardar(
                0,
                "Usuario Prueba",
                "TEST20261030B",
                "3001234567",
                "prueba20261030b@example.com",
                LocalDate.of(2000, 1, 15),
                true,
                new ArrayList<>(),
                "PruebaSegura123!"
        );

        if (registrado) {
            System.out.println("Usuario registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el usuario.");
        }
    }
}
