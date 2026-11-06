
package Services;
import Models.Membresia;
import Models.Pago;
import Models.PagoSuplemento;
import Models.Suplemento;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import Repositories.PagoRepository;
 
 
public class PagoService {
    private PagoRepository repository;
    private MembresiaService membresiaService;
    private SupleService supleService;

    private String ultimoError;
    
    private static final List<String> METODOS_VALIDOS =
            Arrays.asList("Efectivo", "Tarjeta", "Transferencia");
    
    public PagoService(){
        this.repository = new PagoRepository();
        this.membresiaService = new MembresiaService();
        this.supleService = new SupleService();
    }

    public String getUltimoError(){
        return ultimoError;
    }

    private boolean fallar(String accion, String error){
        this.ultimoError = error;
        System.out.println("No se pudo " + accion + " el pago: " + error);
        return false;
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

    private String prepararSuplementos(List<PagoSuplemento> lineas, double[] total){
        Set<Integer> vistos = new HashSet<>();
        for (PagoSuplemento linea : lineas) {
            if (linea == null) {
                return "Hay un suplemento vacío en la lista";
            }
            if (linea.getCantidad() <= 0) {
                return "La cantidad de cada suplemento debe ser mayor que 0";
            }
            if (!vistos.add(linea.getIdSu())) {
                return "Un suplemento no puede repetirse en el mismo pago";
            }
            Suplemento s = supleService.ConsultarPorId(linea.getIdSu());
            if (s == null) {
                return "El suplemento con id " + linea.getIdSu() + " no existe";
            }
            if (!s.getEstado()) {
                return "El suplemento «" + s.getNombre() + "» está inactivo";
            }
            if (s.getStock() < linea.getCantidad()) {
                return "No hay stock suficiente de «" + s.getNombre() + "» (disponible: "
                        + s.getStock() + ", pedido: " + linea.getCantidad() + ")";
            }
            linea.setPrecioUnitario(s.getPrecio());
            linea.setNombre(s.getNombre());
            total[0] += s.getPrecio() * linea.getCantidad();
        }
        return null;
    }
    
    public boolean Guardar(Pago pago){
        return Guardar(pago, new ArrayList<>());
    }

    public boolean Guardar(Pago pago, List<PagoSuplemento> suplementos){
        this.ultimoError = null;
        if (pago == null) {
            return fallar("guardar", "El pago no puede ser nulo");
        }
        List<PagoSuplemento> lineas = suplementos == null ? new ArrayList<>() : suplementos;

        Membresia membresia = null;
        if (pago.getIdMembresia() > 0) {
            membresia = membresiaService.ConsultarPorId(pago.getIdMembresia());
            if (membresia == null) {
                return fallar("guardar", "La membresía indicada no existe");
            }
            if (!membresia.getEstado()) {
                return fallar("guardar", "La membresía indicada está inactiva");
            }
        }

        double[] totalSuplementos = {0};
        String error = prepararSuplementos(lineas, totalSuplementos);
        if (error == null && !lineas.isEmpty() && membresia != null) {
            pago.setValor((int) Math.round(membresia.precio() + totalSuplementos[0]));
        }
        if (error == null) {
            error = validar(pago);
        }
        if (error == null && this.repository.ConsultarPorId(pago.getIdPago()) != null) {
            error = "Ya existe un pago con ese id";
        }
        if (error != null) {
            return fallar("guardar", error);
        }
        pago.setMetodoPago(pago.getMetodoPago().trim());
        boolean ok = this.repository.Insertar(pago, lineas);
        if (ok) {
            System.out.println("El pago se ha guardado exitosamente");
        } else {
            this.ultimoError = this.repository.getUltimoError();
        }
        return ok;
    }
    
    public boolean Actualizar(Pago pago){
        this.ultimoError = null;
        String error = validar(pago);
        Pago existente = null;
        if (error == null) {
            existente = this.repository.ConsultarPorId(pago.getIdPago());
            if (existente == null) {
                error = "No existe un pago con ese id";
            }
        }
        if (error == null && existente.isEstado() != pago.isEstado()) {
            error = "El estado no se cambia desde Editar. Para anular un pago usa el botón Anular de la lista";
        }
        if (error == null && existente.getIdMembresia() != pago.getIdMembresia()) {
            error = "No se puede cambiar la membresía de un pago registrado. Anúlalo y registra uno nuevo";
        }
        if (error == null && existente.getValor() != pago.getValor()) {
            error = "El valor de un pago registrado no se modifica (incluye membresía y suplementos)";
        }
        if (error != null) {
            return fallar("actualizar", error);
        }
        pago.setMetodoPago(pago.getMetodoPago().trim());
        boolean ok = this.repository.Actualizar(pago);
        if (ok) {
            System.out.println("Se actualizó correctamente");
        } else {
            this.ultimoError = this.repository.getUltimoError();
        }
        return ok;
    }
    
    public boolean Eliminar(int idPago){
        this.ultimoError = null;
        if (idPago <= 0) {
            return fallar("anular", "el id debe ser mayor que 0");
        }
        Pago existente = this.repository.ConsultarPorId(idPago);
        if (existente == null) {
            return fallar("anular", "no existe ese id");
        }
        if (!existente.isEstado()) {
            return fallar("anular", "el pago ya está anulado");
        }
        
        boolean ok = this.repository.Eliminar(idPago);
        if (ok) {
            System.out.println("El pago se ha anulado exitosamente");
        } else {
            this.ultimoError = this.repository.getUltimoError();
        }
        return ok;
    }
    
    public Pago ConsultarPorId(int idPago){
        return this.repository.ConsultarPorId(idPago);
    }

    public List<PagoSuplemento> ListarSuplementos(int idPago){
        return this.repository.ConsultarSuplementos(idPago);
    }
    
    public List<Pago> Listar(){
        return repository.Consultar();
    }
}
