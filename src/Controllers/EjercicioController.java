
package Controllers;

import Models.Ejercicio;
import Services.EjercicioService;

public class EjercicioController {
    
    private EjercicioService ejercicioService = new EjercicioService();
    
    public boolean guardar(Ejercicio ejercicio){
        return ejercicioService.guardar(ejercicio);
    }
    
    public boolean actualizar (Ejercicio ejercicio){
         return ejercicioService.actualizar(ejercicio );
    }
    
    public boolean eliminar (int idEjercicio){
         return ejercicioService.eliminar(idEjercicio);
    }
    public Ejercicio consultarPorId(int id){
         return ejercicioService.consultarId(id);
    }
}
