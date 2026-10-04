package Services;

import Models.Suplemento;
import Repositories.SuplementoRepository;
import java.util.List;


public class SupleService {
    
    private SuplementoRepository repository;
            
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
    
    public List<Suplemento> Listar(){
        return repository.Traer();
    }

}
