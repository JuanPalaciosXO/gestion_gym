
package Services;

import Models.Entrenador;

public class EntrenadorService {
    public void Guardar(Entrenador entrenador){
        System.out.println("Entrenador guardado: " + entrenador.getNombre());
    }
    
    public void Actualizar(Entrenador entrenador){
        System.out.println("Entrenador actualiado: " + entrenador.getNombre());
    }
    
    public void Eliminar(int idEntrenador){
        System.out.println("ENtrenador elimiando con ID: " + idEntrenador);
    }
    
    public void Listar(){
        System.out.println("Listado de entrenadores");
    }
}