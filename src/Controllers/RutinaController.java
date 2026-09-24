package Controllers;

import Models.Rutina;
import Services.RutinaService;

public class RutinaController {

    private RutinaService rutinaService = new RutinaService();

    public boolean Guardar(Rutina rutina) {
        return rutinaService.Guardar(rutina);
    }

    public boolean Actualizar(Rutina rutina) {
        return rutinaService.Actualizar(rutina);
    }

    public boolean Eliminar(int id) {
        return rutinaService.Eliminar(id);
    }

    public Rutina ConsultarPorId(int id) {
        return rutinaService.ConsultarPorId(id);
    }
}