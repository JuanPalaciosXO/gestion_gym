package Services;

import Models.Maquina;
import Repositories.MaquinaRepository;
import java.util.List;

public class MaquinaService {
    
    private MaquinaRepository repository;
    
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
    
    public List<Maquina> Listar(){
        return repository.Traer();
    }
}
