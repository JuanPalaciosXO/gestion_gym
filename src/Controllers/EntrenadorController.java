package Controllers;

import Models.Entrenador;
import Services.EntrenadorService;
import java.util.List;

public class EntrenadorController {

    private EntrenadorService entrenadorService;

    public EntrenadorController() {
        this.entrenadorService = new EntrenadorService();
    }

    public boolean Guardar(int idEntrenador, String nombre,
            String documento, String telefono, String correo,
            String especialidad, boolean estado) {

        Entrenador entrenador = new Entrenador(
                idEntrenador,
                nombre,
                documento,
                telefono,
                correo,
                especialidad,
                estado
        );

        return entrenadorService.Guardar(entrenador);
    }

    public List<Entrenador> Listar() {
        return entrenadorService.Listar();
    }

    public boolean Actualizar(int idEntrenador, String nombre,
            String documento, String telefono, String correo,
            String especialidad, boolean estado) {

        Entrenador entrenador = new Entrenador(
                idEntrenador,
                nombre,
                documento,
                telefono,
                correo,
                especialidad,
                estado
        );

        return entrenadorService.Actualizar(entrenador);
    }

    public boolean Eliminar(int idEntrenador) {
        return entrenadorService.Eliminar(idEntrenador);
    }
}
