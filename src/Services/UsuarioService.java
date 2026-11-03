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

    public boolean Guardar(Usuario usuario, String password) {

        if (!ValidarUsuario(usuario)) {
            return false;
        }

        if (password == null
                || password.length() < 8
                || password.length() > 72) {

            System.out.println(
                    "Error: La contraseña debe tener entre 8 y 72 caracteres."
            );
            return false;
        }

        if (repository.ExisteDocumento(usuario.getDocumento(), 0)) {
            System.out.println(
                    "Error: Ya existe un usuario con ese documento."
            );
            return false;
        }

        if (repository.ExisteCorreo(usuario.getCorreo(), 0)) {
            System.out.println(
                    "Error: Ya existe un usuario con ese correo."
            );
            return false;
        }

        String hash = PasswordUtils.encriptar(password);
        usuario.setPasswordHash(hash);

        return repository.Insertar(usuario);
    }

    @Deprecated
    public boolean Guardar(Usuario usuario) {
        System.out.println("Debe proporcionar una contraseña.");
        return false;
    }

    public List<Usuario> Listar() {
        return repository.Traer();
    }

    public boolean Actualizar(Usuario usuario) {

        if (!ValidarUsuario(usuario)) {
            return false;
        }

        if (usuario.getIdUsuario() <= 0) {
            System.out.println("Error: ID de usuario inválido.");
            return false;
        }

        if (!repository.ExistePorId(usuario.getIdUsuario())) {
            System.out.println("Error: El usuario no existe.");
            return false;
        }

        if (repository.ExisteDocumento(
                usuario.getDocumento(), usuario.getIdUsuario())) {

            System.out.println(
                    "Error: El documento pertenece a otro usuario."
            );
            return false;
        }

        if (repository.ExisteCorreo(
                usuario.getCorreo(), usuario.getIdUsuario())) {

            System.out.println(
                    "Error: El correo pertenece a otro usuario."
            );
            return false;
        }

        return repository.Actualizar(usuario);
    }

    public boolean Eliminar(int idUsuario) {

        if (idUsuario <= 0) {
            System.out.println("Error: ID de usuario inválido.");
            return false;
        }

        if (!repository.ExistePorId(idUsuario)) {
            System.out.println("Error: El usuario no existe.");
            return false;
        }

        return repository.Eliminar(idUsuario);
    }

    public Usuario IniciarSesion(String documento, String password) {

        if (documento == null || documento.isBlank()
                || password == null || password.isEmpty()) {
            return null;
        }

        Usuario usuario = repository.BuscarPorDocumento(documento);

        if (usuario == null || !usuario.isEstado()) {
            return null;
        }

        boolean passwordCorrecta = PasswordUtils.verificar(
                password,
                usuario.getPasswordHash()
        );

        if (!passwordCorrecta) {
            return null;
        }

        usuario.setPasswordHash(null);

        return usuario;
    }

    private boolean ValidarUsuario(Usuario usuario) {

        if (usuario == null) {
            System.out.println("Error: El usuario no puede ser nulo.");
            return false;
        }

        if (usuario.getNombre() == null
                || usuario.getNombre().isBlank()) {
            System.out.println("Error: El nombre es obligatorio.");
            return false;
        }

        if (usuario.getNombre().trim().length() > 100) {
            System.out.println("Error: El nombre es demasiado largo.");
            return false;
        }

        if (usuario.getDocumento() == null
                || usuario.getDocumento().isBlank()) {
            System.out.println("Error: El documento es obligatorio.");
            return false;
        }

        if (!usuario.getDocumento().trim().matches("[a-zA-Z0-9-]{5,20}")) {
            System.out.println("Error: El documento debe tener entre 5 y 20 caracteres válidos.");
            return false;
        }

        if (usuario.getTelefono() != null
                && !usuario.getTelefono().isBlank()
                && !usuario.getTelefono().trim().matches("\\d{7,15}")) {
            System.out.println("Error: El teléfono debe tener entre 7 y 15 dígitos.");
            return false;
        }

        if (usuario.getCorreo() != null
                && !usuario.getCorreo().isBlank()
                && !usuario.getCorreo().trim().matches(
                        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            System.out.println("Error: El correo electrónico no es válido.");
            return false;
        }

        if (usuario.getFechaNacimiento() != null
                && usuario.getFechaNacimiento().isAfter(
                        java.time.LocalDate.now())) {
            System.out.println("Error: La fecha de nacimiento no puede ser futura.");
            return false;
        }
        return true;
    }
}
