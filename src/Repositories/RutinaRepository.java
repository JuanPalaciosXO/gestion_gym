
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
    
    
    public Rutina ConsultarPorId(int id) {
        for (int i = 0; i < rutinas.size(); i++) {
            if (rutinas.get(i).getId() == id) {
                return rutinas.get(i);
            }
        }

        return null;
    }
}
    
              
    

