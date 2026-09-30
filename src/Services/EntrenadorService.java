package Services;

import Models.Entrenador;
import Repositories.EntrenadorRepository;
import java.util.List;

import Models.Entrenador;

public class EntrenadorService {

    private EntrenadorRepository repository;

    public EntrenadorService() {
        this.repository = new EntrenadorRepository();
    }

    public boolean Guardar(Entrenador entrenador) {
        return repository.Insertar(entrenador);
    }

    public List<Entrenador> Listar() {
        return repository.Traer();
    }

    public boolean Actualizar(Entrenador entrenador) {
        return repository.Actualizar(entrenador);
    }

    public boolean Eliminar(int idEntrenador) {
        return repository.Eliminar(idEntrenador);
    }
}
