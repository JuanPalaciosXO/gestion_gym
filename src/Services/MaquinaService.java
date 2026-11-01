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
        if (existe(maquina.getIdMn())) {
            throw new IllegalArgumentException("Ya existe una máquina con ese ID.");
        }
        return this.repository.Insertar(maquina);
    }

    public boolean Actualizar(Maquina maquina) {
        if (!existe(maquina.getIdMn())) {
            throw new IllegalArgumentException("No se puede actualizar: No existe una máquina con ese ID.");
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
}
