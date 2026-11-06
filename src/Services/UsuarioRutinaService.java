package Services;

import Repositories.UsuarioRutinaRepository;
import java.util.List;

public class UsuarioRutinaService {

    private final UsuarioRutinaRepository repository;

    public UsuarioRutinaService() {
        repository = new UsuarioRutinaRepository();
    }

    public boolean asignarRutina(int idUsuario, int idRutina) {
        if (idUsuario <= 0 || idRutina <= 0) {
            return false;
        }

        List<Integer> asignadas = repository.obtenerRutinas(idUsuario);

        if (asignadas.contains(idRutina)) {
            return false;
        }

        return repository.asignarRutina(idUsuario, idRutina);
    }

    public List<Integer> obtenerRutinas(int idUsuario) {
        return repository.obtenerRutinas(idUsuario);
    }

    public boolean quitarRutina(int idUsuario, int idRutina) {
        if (idUsuario <= 0 || idRutina <= 0) {
            return false;
        }

        return repository.quitarRutina(idUsuario, idRutina);
    }
}
