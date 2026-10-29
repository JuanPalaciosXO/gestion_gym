
package Services;

import  Models.Ejercicio; 
import Repositories.EjercicioRepository;

public class EjercicioService {
    
    private EjercicioRepository repository; 
    public EjercicioService(){
        this.repository = new EjercicioRepository();
    
    }
    
    
    public boolean guardar (Ejercicio ejercicio){
    
        System.out.println("se a guardo exitosamente el ejercicio"); 
        return repository.Guardar(ejercicio);
        
    }
    
        public boolean actualizar (Ejercicio ejercicio){
    
        System.out.println("se a actualizado el ejercicio"); 
        return repository.Actualizar(ejercicio);
        
    }
        
        public boolean eliminar (int idEjercicio ){
    
        System.out.println("se a eliminado el ejercicio"); 
        return repository.Eliminar(idEjercicio);
        
    }
        
        public Ejercicio consultarId (int idEjercicio){
    
        System.out.println("se guardo exitosamente el ejercicio"); 
        return repository.ConsultarPorId(idEjercicio); 
        
    }
}
    

