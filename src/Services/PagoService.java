
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
        return this.repository.Insertar(pago);
    }
    
    public boolean Actualizar(Pago pago){
        System.out.println("Se actualizó correctamente");
        return this.repository.Actualizar(pago);
    }
    
    public boolean Eliminar(int idPago){
        System.out.println("Se elimino correctamente");
        return this.repository.Eliminar(idPago);
    }
    
    public Pago ConsultarPorId(int idPago){
        return this.repository.ConsultarPorId(idPago);
    }
    
    public List<Pago> Listar(){
        return repository.Consultar();
    }
}
