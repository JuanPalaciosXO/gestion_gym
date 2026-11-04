package Services;

import Models.Suplemento;
import Repositories.SuplementoRepository;
import java.util.List;


public class SupleService {
    
    private SuplementoRepository repository;
    
    public SupleService() {
        this.repository = new SuplementoRepository();
    }
    
    private boolean existe(int idSu) {
        return this.repository.ConsultarPorId(idSu) != null;
    }
            
    public boolean Guardar(Suplemento suplemento) {
        if (suplemento == null) {
            throw new IllegalArgumentException("El suplemento no puede ser nulo.");
        }
        
        for (Suplemento s : repository.Traer()) {
            if (s.getNombre().equalsIgnoreCase(suplemento.getNombre())) {
                System.out.println("Error: Ya existe un suplemento con ese nombre.");
                return false;
            }
        }
        return this.repository.Insertar(suplemento);
    }

    public boolean Actualizar(Suplemento suplemento) {
        if (suplemento == null) {
            throw new IllegalArgumentException("El suplemento no puede ser nulo.");
        }
        if (!existe(suplemento.getIdSu())) {
            throw new IllegalArgumentException("No se puede actualizar: No existe un suplemento con ese ID.");
        }
        for (Suplemento s : repository.Traer()) {
            if (s.getNombre().equalsIgnoreCase(suplemento.getNombre()) && s.getIdSu() != suplemento.getIdSu()) {
                System.out.println("Error: Ya existe otro suplemento con ese nombre.");
                return false;
            }
        }
        return this.repository.Actualizar(suplemento);
    }

    public boolean Eliminar(int idSu) {
        if (!existe(idSu)) {
            throw new IllegalArgumentException("No se puede eliminar: El ID especificado no existe.");
        }
        return this.repository.Eliminar(idSu);
    }
    
    public Suplemento ConsultarPorId(int idSu) {
        return this.repository.ConsultarPorId(idSu);
    }

    public List<Suplemento> Listar() {
        return this.repository.Traer();
    }

}
