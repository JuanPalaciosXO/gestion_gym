
package Services;
import Models.Pago;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import Repositories.PagoRepository;
 
 
public class PagoService {
    private PagoRepository repository;
    
    private static final List<String> METODOS_VALIDOS =
            Arrays.asList("Efectivo", "Tarjeta", "Transferencia");
    
    public PagoService(){
        this.repository = new PagoRepository();
    }
    
    private String validar(Pago p){
        if (p == null) {
            return "El pago no puede ser nulo";
        }
        if (p.getIdPago() < 0) {
            return "El id del pago no puede ser negativo";
        }
        if (p.getFecha() == null) {
            return "La fecha es obligatoria";
        }
        if (p.getFecha().isAfter(LocalDate.now())) {
            return "La fecha del pago no puede ser futura";
        }
        if (p.getValor() <= 0) {
            return "El valor debe ser mayor que 0";
        }
        if (p.getMetodoPago() == null || p.getMetodoPago().trim().isEmpty()) {
            return "El método de pago es obligatorio";
        }
        boolean metodoOk = false;
        for (String metodo : METODOS_VALIDOS) {
            if (metodo.equalsIgnoreCase(p.getMetodoPago().trim())) {
                metodoOk = true;
            }
        }
        if (!metodoOk) {
            return "Método de pago no válido. Use: " + String.join(", ", METODOS_VALIDOS);
        }
        if (p.getIdUsuario() <= 0) {
            return "Debe indicar un usuario válido";
        }
        if (p.getIdMembresia() <= 0) {
            return "Debe indicar una membresía válida";
        }
        return null;
    }
    
    public boolean Guardar(Pago pago){
        String error = validar(pago);
        if (error == null && this.repository.ConsultarPorId(pago.getIdPago()) != null) {
            error = "Ya existe un pago con ese id";
        }
        if (error != null) {
            System.out.println("No se pudo guardar el pago: " + error);
            return false;
        }
        pago.setMetodoPago(pago.getMetodoPago().trim());
        boolean ok = this.repository.Insertar(pago);
        if (ok) {
            System.out.println("El pago se ha guardado exitosamente");
        }
        return ok;
    }
    
    public boolean Actualizar(Pago pago){
        String error = validar(pago);
        if (error == null && this.repository.ConsultarPorId(pago.getIdPago()) == null) {
            error = "No existe un pago con ese id";
        }
        if (error != null) {
            System.out.println("No se pudo actualizar el pago: " + error);
            return false;
        }
        pago.setMetodoPago(pago.getMetodoPago().trim());
        boolean ok = this.repository.Actualizar(pago);
        if (ok) {
            System.out.println("Se actualizó correctamente");
        }
        return ok;
    }
    
    public boolean Eliminar(int idPago){
        if (idPago <= 0) {
            System.out.println("No se pudo eliminar el pago: el id debe ser mayor que 0");
            return false;
        }
        Pago existente = this.repository.ConsultarPorId(idPago);
        if (existente == null) {
            System.out.println("No se pudo anular el pago: no existe ese id");
            return false;
        }
        if (!existente.isEstado()) {
            System.out.println("El pago ya está anulado");
            return false;
        }
        boolean ok = this.repository.Eliminar(idPago);
        if (ok) {
            System.out.println("El pago se ha anulado exitosamente");
        }
        return ok;
    }
    
    public Pago ConsultarPorId(int idPago){
        return this.repository.ConsultarPorId(idPago);
    }
    
    public List<Pago> Listar(){
        return repository.Consultar();
    }
}
