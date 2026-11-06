package Services;

import Repositories.RutinaEjercicioRepository;
import java.util.ArrayList;

public class RutinaEjercicioService {

    private final RutinaEjercicioRepository repository;

    public RutinaEjercicioService() {
        this.repository = new RutinaEjercicioRepository();
    }

    public boolean asignarEjercicio(int idRutina, int idEjercicio) {
        if (idRutina <= 0 || idEjercicio <= 0) {
            return false;
        }

        return repository.asignarEjercicio(idRutina, idEjercicio);
    }

    public ArrayList<Integer> obtenerEjercicios(int idRutina) {
        if (idRutina <= 0) {
            return new ArrayList<>();
        }

        return repository.obtenerEjercicios(idRutina);
    }

    public boolean quitarEjercicio(int idRutina, int idEjercicio) {
        if (idRutina <= 0 || idEjercicio <= 0) {
            return false;
        }

        return repository.quitarEjercicio(idRutina, idEjercicio);
    }
}
