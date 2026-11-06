package Controllers;

import Services.UsuarioRutinaService;
import java.util.List;

public class UsuarioRutinaController {

    private final UsuarioRutinaService service;

    public UsuarioRutinaController() {
        service = new UsuarioRutinaService();
    }

    public boolean asignarRutina(int idUsuario, int idRutina) {
        return service.asignarRutina(idUsuario, idRutina);
    }

    public List<Integer> obtenerRutinas(int idUsuario) {
        return service.obtenerRutinas(idUsuario);
    }

    public boolean quitarRutina(int idUsuario, int idRutina) {
        return service.quitarRutina(idUsuario, idRutina);
    }
}
