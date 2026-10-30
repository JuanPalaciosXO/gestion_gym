
package Services;

import Models.Usuario;
import Repositories.UsuarioRepository;
import Utils.PasswordUtils;
import java.util.List;

public class UsuarioService {

    private UsuarioRepository repository;

    public UsuarioService() {
        this.repository = new UsuarioRepository();
    }

    // Registrar usuario con contraseña
    public boolean Guardar(Usuario usuario, String password) {

        if (usuario == null || password == null
                || password.length() < 8
                || password.length() > 72) {

            System.out.println("La contraseña debe tener entre 8 y 72 caracteres.");
            return false;
        }

        String hash = PasswordUtils.encriptar(password);
        usuario.setPasswordHash(hash);

        return repository.Insertar(usuario);
    }

    // Compatibilidad con el código anterior
    @Deprecated
    public boolean Guardar(Usuario usuario) {
        System.out.println("Debe proporcionar una contraseña.");
        return false;
    }

    // Consultar usuarios
    public List<Usuario> Listar() {
        return repository.Traer();
    }

    // Actualizar datos sin cambiar contraseña
    public boolean Actualizar(Usuario usuario) {
        return repository.Actualizar(usuario);
    }

    // Eliminar usuario
    public boolean Eliminar(int idUsuario) {
        return repository.Eliminar(idUsuario);
    }
    
    
public Usuario IniciarSesion(String documento, String password) {

    // Validar que los datos no estén vacíos
    if (documento == null || documento.isBlank()
            || password == null || password.isEmpty()) {
        return null;
    }

    // Buscar al usuario en la base de datos
    Usuario usuario = repository.BuscarPorDocumento(documento);

    // Comprobar que exista y esté activo
    if (usuario == null || !usuario.isEstado()) {
        return null;
    }

    // Verificar la contraseña con BCrypt
    boolean passwordCorrecta = PasswordUtils.verificar(
            password,
            usuario.getPasswordHash()
    );

    if (!passwordCorrecta) {
        return null;
    }

    // No devolver el hash a la capa de presentación
    usuario.setPasswordHash(null);

    return usuario;
}

}
