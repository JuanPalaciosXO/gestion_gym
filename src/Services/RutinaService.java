package Services;

import Models.Rutina;
import Repositories.RutinaRepository;
import java.util.ArrayList;

public class RutinaService {
    
    private RutinaRepository repository; 
    
    public RutinaService() {
        this.repository = new RutinaRepository();
    }
    
    public boolean guardar(Rutina rutina) {
        System.out.println("Se ha guardado la rutina exitosamente.");
        return repository.Guardar(rutina); 
    }
    
    public boolean actualizar(Rutina rutina) {
        System.out.println("Se actualizó correctamente la rutina.");
        return repository.Actualizar(rutina); 
    }
    
    public boolean eliminar(int idRutina) {
        System.out.println("Se eliminó exitosamente la rutina.");
        return repository.Eliminar(idRutina); 
    }
    
    public Rutina consultarId(int idRutina) {
        System.out.println("Consultando rutina con ID: " + idRutina);
        return repository.ConsultarPorId(idRutina); 
    }

    public ArrayList<Rutina> listar() {
        return repository.Listar();
    }
}