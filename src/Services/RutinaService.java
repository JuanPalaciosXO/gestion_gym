
package Services;

import  Models.Rutina;
import Repositories.RutinaRepository; 


public class RutinaService {
    
    private RutinaRepository repository; 
    public RutinaService(){
     this.repository = new RutinaRepository () ;
    }
    
    public boolean guardar (Rutina rutina){
     
        System.out.println("se a guardado la rutina exxitosa mente");
        return repository.guardar(rutina);
    
    }
    
    public boolean actualizar (Rutina rutina){
    
        System.out.println("se actualizo correcta mente la rutina");
        return repository.guardar(rutina); 
    
    }
    
        public boolean eliminar(int idRutina ){
    
        System.out.println("se elimino exitosamente la rutina");
        return repository.Eliminar(idRutina); 
    
    }
        
            public Rutina consultarId (int idRutina){
    
        System.out.println("consultando rutina de " + idRutina);
        return repository.ConsultarPorId(idRutina) ; 
    
    }
    
}
