package Services;

import Models.Entrenador;
import Repositories.EntrenadorRepository;
import java.util.List;

public class EntrenadorService {

    private final EntrenadorRepository repository;

    public EntrenadorService() {
        this.repository = new EntrenadorRepository();
    }

    private boolean validarEntrenador(Entrenador entrenador) {

        if (entrenador == null) {
            return false;
        }

        if (entrenador.getNombre() == null
                || entrenador.getNombre().trim().isEmpty()) {
            return false;
        }

        if (entrenador.getDocumento() == null
                || entrenador.getDocumento().trim().isEmpty()) {
            return false;
        }

        String correo = entrenador.getCorreo();

        if (correo != null && !correo.trim().isEmpty()
                && !correo.matches(
                        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return false;
        }

        return true;
    }

    private boolean existeDuplicado(Entrenador entrenador) {

        List<Entrenador> entrenadores = repository.Traer();

        for (Entrenador actual : entrenadores) {

            if (actual.getIdEntrenador()
                    == entrenador.getIdEntrenador()
                    && entrenador.getIdEntrenador() > 0) {
                continue;
            }

            if (actual.getDocumento().equals(
                    entrenador.getDocumento())) {
                return true;
            }

            if (entrenador.getCorreo() != null
                    && !entrenador.getCorreo().trim().isEmpty()
                    && actual.getCorreo() != null
                    && actual.getCorreo().equalsIgnoreCase(
                            entrenador.getCorreo())) {
                return true;
            }
        }

        return false;
    }

    public boolean Guardar(Entrenador entrenador) {

        if (!validarEntrenador(entrenador)) {
            return false;
        }

        if (existeDuplicado(entrenador)) {
            return false;
        }

        return repository.Insertar(entrenador);
    }

    public List<Entrenador> Listar() {
        return repository.Traer();
    }

    public boolean Actualizar(Entrenador entrenador) {

        if (!validarEntrenador(entrenador)) {
            return false;
        }

        if (entrenador.getIdEntrenador() <= 0) {
            return false;
        }

        boolean existe = false;

        for (Entrenador actual : repository.Traer()) {
            if (actual.getIdEntrenador()
                    == entrenador.getIdEntrenador()) {
                existe = true;
                break;
            }
        }

        if (!existe || existeDuplicado(entrenador)) {
            return false;
        }

        return repository.Actualizar(entrenador);
    }

    public boolean Eliminar(int idEntrenador) {

        if (idEntrenador <= 0) {
            return false;
        }

        return repository.Eliminar(idEntrenador);
    }
}
