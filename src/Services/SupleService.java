package Services;

import Models.Suplemento;


public class SupleService {
    public boolean Guardar(Suplemento suplementos){
        System.out.println("Se guardo el suplemento");
        return true;
    }
    
    public boolean Actualizar(Suplemento suplementos){
        System.out.println("Se actualizo el suplemento");
        return true;
    }
    
    public boolean Eliminar (int idSu){
        System.out.println("Se elimino el suplemento");
        return true;
    }

}
