package Services;

import Models.Maquina;
import Repositories.MaquinaRepository;
import java.util.List;

public class MaquinaService {
    
    private MaquinaRepository repository;
    
    public MaquinaService() {
        this.repository = new MaquinaRepository();
    }
    
    private boolean existe(int idMn) {
        return this.repository.ConsultarPorId(idMn) != null;
    }
    
    public boolean Guardar(Maquina maquina) {
        validar(maquina);
            for (Maquina m : repository.Traer()) {
            if (m.getNombre().equalsIgnoreCase(maquina.getNombre())) {
                System.out.println("Error: Ya existe una máquina con ese nombre.");
                return false;
            }
        }
        return this.repository.Insertar(maquina);
    }

    public boolean Actualizar(Maquina maquina) {
        if (maquina == null) {
            throw new IllegalArgumentException("La máquina no puede ser nula.");
        }
        
        if (!existe(maquina.getIdMn())) {
            throw new IllegalArgumentException("No se puede actualizar: No existe una máquina con ese ID.");
        }
        for (Maquina m : repository.Traer()) {
            if (m.getNombre().equalsIgnoreCase(maquina.getNombre()) && m.getIdMn() != maquina.getIdMn()) {
                System.out.println("Error: Ya existe otra máquina con ese nombre.");
                return false;
            }
        }
        return this.repository.Actualizar(maquina);
    }

    public boolean Eliminar(int idMn) {
        if (!existe(idMn)) {
            throw new IllegalArgumentException("No se puede eliminar: El ID especificado no existe.");
        }
        return this.repository.Eliminar(idMn);
    }
    
    public Maquina ConsultarPorId(int idMn) { 
        return this.repository.ConsultarPorId(idMn);
    }

    public List<Maquina> Listar() {
        return this.repository.Traer();
    }

    private void validar(Maquina maquina) {
    if (maquina == null) {
        throw new IllegalArgumentException("La máquina no puede ser nula.");
    }
    if (maquina.getNombre() == null || maquina.getNombre().trim().isEmpty()) {
        throw new IllegalArgumentException("El nombre es obligatorio.");
    }
    if (maquina.getUbicacion() == null || maquina.getUbicacion().trim().isEmpty()) {
        throw new IllegalArgumentException("La ubicación es obligatoria.");
    }
    }
}
