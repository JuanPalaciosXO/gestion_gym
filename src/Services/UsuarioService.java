package Services;

import Models.Usuario;
import Repositories.UsuarioRepository;
import java.util.List;

public class UsuarioService {

    private UsuarioRepository repository;

    public UsuarioService() {
        this.repository = new UsuarioRepository();
    }

    public boolean Guardar(Usuario usuario) {
        return repository.Insertar(usuario);
    }

    public List<Usuario> Listar() {
        return repository.Traer();
    }

    public boolean Actualizar(Usuario usuario) {
        return repository.Actualizar(usuario);
    }

    public boolean Eliminar(int idUsuario) {
        return repository.Eliminar(idUsuario);
    }
}
