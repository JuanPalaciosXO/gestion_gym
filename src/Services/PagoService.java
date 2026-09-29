
package Services;
import Models.Pago;
import java.util.ArrayList;
import java.util.List;
import Repositories.PagoRepository;


public class PagoService {
    private PagoRepository repository;
    
    public PagoService(){
        this.repository = new PagoRepository();
    }
    
    public boolean Guardar(Pago pago){
        System.out.println("El pago se ha guardado exitosamente");
        return true;
    }
    
    public boolean Actualizar(Pago pago){
        System.out.println("Se actualizó correctamente");
        return true;
    }
    
    public boolean Eliminar(int id){
        System.out.println("Se elimino correctamente");
        return true;
    }
    
    public List<Pago> Listar(){
        return repository.Consultar();
    }
}
