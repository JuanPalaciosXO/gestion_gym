
package Repositories;

import Models.Rutina;
import java.util.ArrayList;

public class RutinaRepository {
    
    private ArrayList <Rutina>
    rutinas = new ArrayList<>();
    
    public boolean guardar(Rutina rutina){
    
     rutinas.add(rutina);
        System.out.println("rutina guardada en el repositorio");
         return true; 
    }
    
    public boolean actualizar (Rutina rutina){
    
        for(int i = 0 ; i<rutinas.size(); i++){
        
        if(rutinas.get(i).getId()==rutina.getId()){
         
            return true;
         }
        }
    
       return false;
    }
    
        public boolean Eliminar(int idRutina) {
        for (int i = 0; i < rutinas.size(); i++) {
            if (rutinas.get(i).getId() == idRutina) {
                rutinas.remove(i);
                return true;
            }
        }

        return false;
    }
    
    
    public Rutina ConsultarPorId(int idRutina) {
        for (int i = 0; i < rutinas.size(); i++) {
            if (rutinas.get(i).getId() == idRutina) {
                return rutinas.get(i);
            }
        }

        return null;
    }
}
    
              
    

