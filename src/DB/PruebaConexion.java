
package DB;

import Models.Usuario;
import Repositories.UsuarioRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PruebaConexion {

    public static void main(String[] args) {

        // 1. PROBAR CONEXION A MYSQL
        System.out.println("===== PRUEBA DE CONEXION =====");

        try (Connection conexion = Database.conectar()) {

            if (conexion != null && !conexion.isClosed()) {
                System.out.println("PRUEBA EXITOSA");
                System.out.println("Conectado a gestion_gym");
            } else {
                System.out.println("ERROR DE CONEXION");
                return;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        UsuarioRepository repositorio = new UsuarioRepository();

        // 2. INSERTAR USUARIO
        // Esta prueba ya se realizo correctamente.
        // Se deja comentada para evitar duplicados.

        /*
        System.out.println("\n===== INSERTAR USUARIO =====");

        Usuario usuario = new Usuario(
                0,
                "Usuario Prueba",
                "1234567890",
                "3001234567",
                "prueba@gimnasio.com",
                LocalDate.of(2000, 5, 15),
                true,
                new ArrayList<>()
        );

        boolean resultado = repositorio.Insertar(usuario);

        if (resultado) {
            System.out.println("USUARIO GUARDADO CORRECTAMENTE");
            System.out.println("ID generado: " + usuario.getIdUsuario());
        } else {
            System.out.println("ERROR AL GUARDAR USUARIO");
        }
        */

        // 3. CONSULTAR USUARIOS
        System.out.println("\n===== USUARIOS REGISTRADOS EN MYSQL =====");

        for (Usuario u : repositorio.Traer()) {

            System.out.println("ID: " + u.getIdUsuario());
            System.out.println("Nombre: " + u.getNombre());
            System.out.println("Documento: " + u.getDocumento());
            System.out.println("Telefono: " + u.getTelefono());
            System.out.println("Correo: " + u.getCorreo());
            System.out.println("Fecha nacimiento: " + u.getFechaNacimiento());
            System.out.println("Estado: " + u.isEstado());
            System.out.println("--------------------------------");
        }

        // 4. ACTUALIZAR USUARIO
        System.out.println("\n===== PRUEBA ACTUALIZAR USUARIO =====");

        boolean encontrado = false;

        for (Usuario u : repositorio.Traer()) {

            if (u.getIdUsuario() == 1) {

                encontrado = true;

                u.setNombre("Usuario Actualizado");
                u.setTelefono("3109876543");

                boolean actualizado = repositorio.Actualizar(u);

                if (actualizado) {
                    System.out.println("USUARIO ACTUALIZADO CORRECTAMENTE");
                } else {
                    System.out.println("ERROR AL ACTUALIZAR USUARIO");
                }

                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontro el usuario con ID 1");
        }

        // 5. CONSULTAR DESPUES DE ACTUALIZAR
        System.out.println("\n===== USUARIOS DESPUES DE ACTUALIZAR =====");

        for (Usuario u : repositorio.Traer()) {

            System.out.println("ID: " + u.getIdUsuario());
            System.out.println("Nombre: " + u.getNombre());
            System.out.println("Telefono: " + u.getTelefono());
            System.out.println("--------------------------------");
        }
        
        
System.out.println("\n===== PRUEBA ELIMINAR USUARIO =====");


int idEliminar = 1;

boolean eliminado = repositorio.Eliminar(idEliminar);

if (eliminado) {
    System.out.println("USUARIO ELIMINADO CORRECTAMENTE");
} else {
    System.out.println("NO SE PUDO ELIMINAR EL USUARIO");
}

System.out.println("\n===== USUARIOS DESPUES DE ELIMINAR =====");

for (Usuario usuario : repositorio.Traer()) {
    System.out.println("ID: " + usuario.getIdUsuario());
    System.out.println("Nombre: " + usuario.getNombre());
    System.out.println("-----------------------");
}


    }
}
