
package Services;
import Models.Membresia;
import java.util.List;
import Repositories.MembRepository;
 
public class MembresiaService {
    private MembRepository repository;
    
    public MembresiaService(){
        this.repository = new MembRepository();
    }
    
    private String validar(Membresia m){
        if (m == null) {
            return "La membresía no puede ser nula";
        }
        if (m.getIdMemb() < 0) {
            return "El id no puede ser negativo";
        }
        if (m.getNombre() == null || m.getNombre().trim().isEmpty()) {
            return "El nombre es obligatorio";
        }
        if (m.getNombre().trim().length() > 50) {
            return "El nombre no puede superar los 50 caracteres";
        }
        if (Double.isNaN(m.precio()) || m.precio() <= 0) {
            return "El precio debe ser mayor que 0";
        }
        if (m.getDuracionmes() <= 0) {
            return "La duración debe ser de al menos 1 mes";
        }
        if (m.getDescripcion() != null && m.getDescripcion().length() > 255) {
            return "La descripción no puede superar los 255 caracteres";
        }
        return null;
    }
    
    private boolean nombreRepetido(Membresia m){
        for (Membresia otra : repository.Consultar()) {
            if (otra.getIdMemb() != m.getIdMemb()
                    && otra.getNombre().equalsIgnoreCase(m.getNombre().trim())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean Guardar(Membresia membresia){
        String error = validar(membresia);
        if (error == null && this.repository.ConsultarPorId(membresia.getIdMemb()) != null) {
            error = "Ya existe una membresía con ese id";
        }
        if (error == null && nombreRepetido(membresia)) {
            error = "Ya existe una membresía con ese nombre";
        }
        if (error != null) {
            System.out.println("No se pudo guardar la membresía: " + error);
            return false;
        }
        membresia.setNombre(membresia.getNombre().trim());
        boolean ok = this.repository.Insertar(membresia);
        if (ok) {
            System.out.println("El tipo de membresia se ha guardado exitosamente");
        }
        return ok;
    }
    
    public boolean Actualizar(Membresia membresia){
        String error = validar(membresia);
        if (error == null && this.repository.ConsultarPorId(membresia.getIdMemb()) == null) {
            error = "No existe una membresía con ese id";
        }
        if (error == null && nombreRepetido(membresia)) {
            error = "Ya existe otra membresía con ese nombre";
        }
        if (error != null) {
            System.out.println("No se pudo actualizar la membresía: " + error);
            return false;
        }
        membresia.setNombre(membresia.getNombre().trim());
        boolean ok = this.repository.Actualizar(membresia);
        if (ok) {
            System.out.println("La membresia se ha actualizado exitosamente");
        }
        return ok;
    }
    
    public boolean Eliminar(int idMemb){
        if (idMemb <= 0) {
            System.out.println("No se pudo eliminar la membresía: el id debe ser mayor que 0");
            return false;
        }
        if (this.repository.ConsultarPorId(idMemb) == null) {
            System.out.println("No se pudo eliminar la membresía: no existe ese id");
            return false;
        }
        boolean ok = this.repository.Eliminar(idMemb);
        if (ok) {
            System.out.println("El tipo de membresia se ha eliminado exitosamente");
        }
        return ok;
    }
    
    public Membresia ConsultarPorId(int idMemb){
        return this.repository.ConsultarPorId(idMemb);
    }
    
    public List<Membresia> Listar(){
        return repository.Consultar();
    }
}
