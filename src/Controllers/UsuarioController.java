package Controllers;

import Models.Usuario;
import Models.Rutina;
import Services.UsuarioService;
import java.time.LocalDate;
import java.util.List;

public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController() {
        usuarioService = new UsuarioService();
    }

    
public boolean Guardar(int idUsuario, String nombre,
        String documento, String telefono, String correo,
        LocalDate fechaNacimiento, boolean estado,
        List<Rutina> rutinas, String password) {

    Usuario usuario = new Usuario(
            idUsuario,
            nombre,
            documento,
            telefono,
            correo,
            fechaNacimiento,
            estado,
            rutinas
    );

    return usuarioService.Guardar(usuario, password);
}


    public boolean Actualizar(int idUsuario, String nombre,
            String documento, String telefono, String correo,
            LocalDate fechaNacimiento, boolean estado, List<Rutina> rutinas) {

        Usuario usuario = new Usuario(
                idUsuario,
                nombre,
                documento,
                telefono,
                correo,
                fechaNacimiento,
                estado,
                rutinas
        );

        return usuarioService.Actualizar(usuario);
    }

    public boolean Eliminar(int idUsuario) {
        return usuarioService.Eliminar(idUsuario);
    }

    public List<Usuario> Listar() {
        return usuarioService.Listar();
    }
    
public Usuario IniciarSesion(String documento, String password) {

    return usuarioService.IniciarSesion(documento, password);

}

}
