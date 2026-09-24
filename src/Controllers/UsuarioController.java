
package Controllers;

import Models.Usuario;
import Services.UsuarioService;
import java.time.LocalDate;
import java.util.List;
import Models.Rutina;

public class UsuarioController {
    
    private UsuarioService usuarioService;
    
    public UsuarioController(){
        usuarioService = new UsuarioService();
    }
    
    public void Guardar(int idUsuario, String nombre, String documento, String telefono, String correo, LocalDate fechaNacimiento; boolean estado; List<Rutina> rutinas){
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
        usuarioService.Guardar(usuario);
    }
    
    public void Actualizar(int idUsuario, String nombre, String documento, String telefono, String correo, LocalDate fechaNacimiento; boolean estado; List<Rutina> rutinas){
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
        usuarioService.Actualizar(usuario);
    }
    
    public void Eliminar(int idUsuario){
        usuarioService.Eliminar(idUsuario);
    }
    
    public void Listar(){
        usuarioService.Listar();
    }
}

