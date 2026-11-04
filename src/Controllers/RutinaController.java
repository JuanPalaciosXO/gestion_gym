package Controllers;

import Models.Rutina;
import Services.RutinaService;
import java.util.ArrayList; // 

public class RutinaController {

    private RutinaService rutinaService = new RutinaService();

    public boolean Guardar(Rutina rutina) {
        return rutinaService.guardar(rutina);
    }

    public boolean Actualizar(Rutina rutina) {
        return rutinaService.actualizar(rutina);
    }

    public boolean Eliminar(int idRutina) {
        return rutinaService.eliminar(idRutina);
    }

    public Rutina ConsultarPorId(int idRutina) {
        return rutinaService.consultarId(idRutina);
    }

    
    public ArrayList<Rutina> Listar() {
        return rutinaService.listar();
    }
}