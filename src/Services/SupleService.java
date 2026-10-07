package Services;

import Models.Suplemento;
import Repositories.SuplementoRepository;
import java.util.List;


public class SupleService {
    
    private SuplementoRepository repository;
    
    public SupleService() {
        this.repository = new SuplementoRepository();
    }
            
    public boolean Guardar(Suplemento suplemento) {
    return this.repository.Insertar(suplemento);
}

    public boolean Actualizar(Suplemento suplemento) {
        return this.repository.Actualizar(suplemento);
    }

    public boolean Eliminar(int idSu) {
        return this.repository.Eliminar(idSu);
    }
    
    public Suplemento ConsultarPorId(int idSu) {
        return this.repository.ConsultarPorId(idSu);
    }

    public List<Suplemento> Listar() {
        return this.repository.Traer();
    }

}
