
package Services;
import Models.Membresia;
import java.util.ArrayList;
import java.util.List;
import Repositories.MembRepository;

public class MembresiaService {
    private MembRepository repository;
    
    public MembresiaService(){
        this.repository = new MembRepository();
    }
    
    public boolean Guardar(Membresia membresia){
        System.out.println("Se ha guardado exitosamente la membresia");
        return true;
    }
    
    public boolean Actualizar(Membresia membresia){
        System.out.println("Se actualizo correctamente");
        return true;
    }
    
    public boolean Eliminar(int id){
        System.out.println("Se elimino exitosamente");
        return true;
    }
    
    public Membresia ConsultarPorId(int id){
        System.out.println("Consultadi membresia de " + id);
        return null;
    }
    
    public List<Membresia> Listar(){
        return repository.Consultar();
    }
}
