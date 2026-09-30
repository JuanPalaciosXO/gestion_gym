/*
import Controllers.EntrenadorController;
import Controllers.UsuarioController;
import Models.Usuario;
import Models.Entrenador;
import Models.Rutina;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        UsuarioController usuarioController = new UsuarioController();

        List<Rutina> rutinas1 = new ArrayList<>();
        List<Rutina> rutinas2 = new ArrayList<>();

        usuarioController.Guardar(
                1,
                "Jonathan Pardo",
                "123456789",
                "3001234567",
                "jonathan@gmail.com",
                LocalDate.of(2005, 5, 15),
                true,
                rutinas1
        );

        usuarioController.Guardar(
                2,
                "Laura Gomez",
                "987654321",
                "3109876543",
                "laura@gmail.com",
                LocalDate.of(2004, 8, 20),
                true,
                rutinas2
        );

        List<Usuario> usuarios = usuarioController.Listar();

        for (Usuario usuario : usuarios) {
            System.out.println(
                    "ID: " + usuario.getIdUsuario()
                    + " | Nombre: " + usuario.getNombre());
        }

        System.out.println("\n===== PRUEBA ENTRENADOR =====");

        EntrenadorController entrenadorController = new EntrenadorController();

        entrenadorController.Guardar(
                1,
                "Carlos Ramirez",
                "1012345678",
                "3001234567",
                "carlos@gmail.com",
                "Musculacion",
                true
        );

        entrenadorController.Guardar(
                2,
                "Andrea Lopez",
                "1098765432",
                "3109876543",
                "andrea@gmail.com",
                "Entrenamiento funcional",
                true
        );

        System.out.println("\n--- ENTRENADORES REGISTRADOS ---");

        List<Entrenador> entrenadores = entrenadorController.Listar();

        for (Entrenador entrenador : entrenadores) {
            System.out.println(
                    "ID: " + entrenador.getIdEntrenador()
                    + " | Nombre: " + entrenador.getNombre()
                    + " | Especialidad: " + entrenador.getEspecialidad()
            );
        }
    }
}*/
