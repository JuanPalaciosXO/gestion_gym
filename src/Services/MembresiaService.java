
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
        System.out.println("El tipo de membresia se ha guardado exitosamente");
        return this.repository.Insertar(membresia);
    }
    
    public boolean Actualizar(Membresia membresia){
        System.out.println("La membresia se ha actualizado exitosamente");
        return this.repository.Actualizar(membresia);
    }
    
    public boolean Eliminar(int idMemb){
        System.out.println("El tipo de membresia se ha eliminado exitosamente");
        return this.repository.Eliminar(idMemb);
    }
    
    public Membresia ConsultarPorId(int idMemb){
        return this.repository.ConsultarPorId(idMemb);
    }
    
    public List<Membresia> Listar(){
        return repository.Consultar();
    }
}
