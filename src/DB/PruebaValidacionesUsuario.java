
package DB;

import Models.Usuario;
import Services.UsuarioService;
import java.time.LocalDate;
import java.util.ArrayList;

public class PruebaValidacionesUsuario {

    public static void main(String[] args) {

        UsuarioService service = new UsuarioService();

        // PRUEBA 1: Nombre vacío
        Usuario usuario1 = new Usuario(
                0, "", "PRUEBA001", "3001234567",
                "prueba1@gmail.com",
                LocalDate.of(2000, 5, 10),
                true, new ArrayList<>()
        );

        System.out.println("PRUEBA 1: Nombre vacío");
        System.out.println("Resultado: "
                + service.Guardar(usuario1, "ClaveSegura123"));

        // PRUEBA 2: Documento duplicado
        Usuario usuario2 = new Usuario(
                0, "Usuario Duplicado", "TEST20261030B",
                "3001234567", "nuevo@gmail.com",
                LocalDate.of(2000, 5, 10),
                true, new ArrayList<>()
        );

        System.out.println("\nPRUEBA 2: Documento duplicado");
        System.out.println("Resultado: "
                + service.Guardar(usuario2, "ClaveSegura123"));

        // PRUEBA 3: Correo inválido
        Usuario usuario3 = new Usuario(
                0, "Usuario Correo", "PRUEBA003",
                "3001234567", "correo-invalido",
                LocalDate.of(2000, 5, 10),
                true, new ArrayList<>()
        );

        System.out.println("\nPRUEBA 3: Correo inválido");
        System.out.println("Resultado: "
                + service.Guardar(usuario3, "ClaveSegura123"));

        // PRUEBA 4: ID inexistente al eliminar
        System.out.println("\nPRUEBA 4: Eliminar ID inexistente");
        System.out.println("Resultado: "
                + service.Eliminar(999999));
        
        
        // PRUEBA 5: Actualizar usuario inexistente
        Usuario usuario5 = new Usuario(
                999999,
                "Usuario Inexistente",
                "PRUEBA005",
                "3001234567",
                "prueba5@gmail.com",
                LocalDate.of(2000, 5, 10),
                true,
                new ArrayList<>()
        );

        System.out.println("\nPRUEBA 5: Actualizar usuario inexistente");
        System.out.println("Resultado: "
                + service.Actualizar(usuario5));
        
        
        // PRUEBA 6: Registrar y actualizar un usuario de prueba
        Usuario usuario6 = new Usuario(
                0,
                "Usuario Temporal",
                "PRUEBAUPDATE006",
                "3001234567",
                "pruebaupdate006@gmail.com",
                LocalDate.of(2000, 5, 10),
                true,
                new ArrayList<>()
        );

        System.out.println("\nPRUEBA 6: Actualización válida");

        boolean registrado = service.Guardar(
                usuario6, "ClaveSegura123"
        );

        if (registrado) {

            System.out.println("Usuario registrado con ID: "
                    + usuario6.getIdUsuario());

            usuario6.setNombre("Usuario Actualizado");
            usuario6.setTelefono("3119876543");

            boolean actualizado = service.Actualizar(usuario6);

            System.out.println("Actualización exitosa: "
                    + actualizado);

        } else {
            System.out.println(
                    "No se pudo registrar el usuario de prueba."
            );
        }

    }
}
