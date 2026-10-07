
package Controllers;
import Models.Pago;
import java.util.List;
import Services.PagoService;
import java.time.LocalDate;

public class PagoController {
    private PagoService service;
    
    public PagoController(){
        this.service = new PagoService();
    }
    
    public boolean Guardar(int idPago, LocalDate fecha, int valor, String metodoPago, boolean estado, int idUsuario, int idMembresia){
        Pago pago = new Pago(idPago, fecha, valor, metodoPago, estado, idUsuario, idMembresia);
        return this.service.Guardar(pago);
    }
    
    public boolean Actualizar(Pago pago) {
        return this.service.Actualizar(pago);
    }
    
    public boolean Eliminar(int idPago){
        return this.service.Eliminar(idPago);
    }
    
    public Pago consultarPorId(int id) {
    return this.service.ConsultarPorId(id);
    }
    
    public List<Pago> Listar(){
        return service.Listar();
    }
   
    
    
}
