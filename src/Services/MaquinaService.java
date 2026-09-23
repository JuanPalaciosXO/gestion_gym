package Services;

import Models.Maquina;

public class MaquinaService {
    
    public boolean Guardar(Maquina maquina){
        System.out.println("La maquina se guardo");
        return true;
    }
    
    public boolean Actualizar(Maquina maquina){
        System.out.println("La maquina se actualizo");
        return true;
    }
    
    public boolean Eliminar(int idMn){
        System.out.println("Se elimino la maquina");
        return true;
    }
}
