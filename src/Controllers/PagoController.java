
package Controllers;

import Models.Pago;
import Models.PagoSuplemento;
import java.util.ArrayList;
import java.util.List;
import Services.PagoService;
import java.time.LocalDate;

public class PagoController {
    private PagoService service;
    
    public PagoController(){
        this.service = new PagoService();
    }
    
    public boolean Guardar(int idPago, LocalDate fecha, int valor, String metodoPago, boolean estado, int idUsuario, int idMembresia){
        return Guardar(idPago, fecha, valor, metodoPago, estado, idUsuario, idMembresia, new ArrayList<>());
    }

    public boolean Guardar(int idPago, LocalDate fecha, int valor, String metodoPago, boolean estado, int idUsuario, int idMembresia, List<PagoSuplemento> suplementos){
        Pago pago = new Pago(idPago, fecha, valor, metodoPago, estado, idUsuario, idMembresia);
        return this.service.Guardar(pago, suplementos);
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

    public List<PagoSuplemento> ListarSuplementos(int idPago){
        return this.service.ListarSuplementos(idPago);
    }
    
    public List<Pago> Listar(){
        return service.Listar();
    }

    public String getUltimoError(){
        return service.getUltimoError();
    }
}
