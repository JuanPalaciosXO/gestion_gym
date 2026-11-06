package Controllers;

import Services.RutinaEjercicioService;
import java.util.ArrayList;

public class RutinaEjercicioController {

    private final RutinaEjercicioService service;

    public RutinaEjercicioController() {
        this.service = new RutinaEjercicioService();
    }

    public boolean asignarEjercicio(int idRutina, int idEjercicio) {
        return service.asignarEjercicio(idRutina, idEjercicio);
    }

    public ArrayList<Integer> obtenerEjercicios(int idRutina) {
        return service.obtenerEjercicios(idRutina);
    }

    public boolean quitarEjercicio(int idRutina, int idEjercicio) {
        return service.quitarEjercicio(idRutina, idEjercicio);
    }
}
