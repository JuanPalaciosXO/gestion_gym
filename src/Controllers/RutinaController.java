package Controllers;

import Models.Rutina;
import Services.RutinaService;

public class RutinaController {

    private  RutinaService rutinaService = new RutinaService();

    public boolean Guardar(Rutina rutina) {
        return rutinaService.guardar(rutina);
    }

    public boolean Actualizar(Rutina rutina) {
        return rutinaService.actualizar(rutina);
    }

    public boolean Eliminar(Rutina rutina) {
        return rutinaService.eliminar(rutina);
    }

    public Rutina ConsultarPorId(int id) {
        return rutinaService.consultarId(id);
    }
}