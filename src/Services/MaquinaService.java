package Services;

import Models.Maquina;
import Repositories.MaquinaRepository;
import java.util.List;

public class MaquinaService {
    
    private MaquinaRepository repository;
    
    public MaquinaService() {
        this.repository = new MaquinaRepository();
    }

    public boolean Guardar(Maquina maquina) {
    return this.repository.Insertar(maquina);
}

    public boolean Actualizar(Maquina maquina) {
    return this.repository.Actualizar(maquina);
    }

    public boolean Eliminar(int idMn) {
        return this.repository.Eliminar(idMn);
    }
    
    public Maquina ConsultarPorId(int idMn) {
    return this.repository.ConsultarPorId(idMn);
    }

    public List<Maquina> Listar() {
        return this.repository.Traer();
    }
}
