
package Services;

import  Models.Rutina;

public class RutinaService {
    
    public boolean guardar (Rutina rutina){
     
        System.out.println("se a guardado la rutina exxitosa mente");
        return true;
    
    }
    
    public boolean actualizar (Rutina rutina){
    
        System.out.println("se actualizo correcta mente la rutina");
        return true; 
    
    }
    
        public boolean eliminar(Rutina rutina){
    
        System.out.println("se elimino exitosamente la rutina");
        return true; 
    
    }
        
            public Rutina consultarId (int id){
    
        System.out.println("consultando rutina de " + id);
        return null ; 
    
    }
    
}
