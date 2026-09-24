
package Services;

import Models.Usuario;

public class UsuarioService {
    
    public void Guardar(Usuario usuario){
        System.out.println("Usuario guardado: " + usuario.getNombre());
    }
    
    public void Actualizar(Usuario usuario){
        System.out.println("Usuario actualiado: " + usuario.getNombre());
    }
    
    public void Eliminar(int idUsuario){
        System.out.println("Usuario elimiando con ID: " + idUsuario);
    }
    
    public void Listar(){
        System.out.println("Listado de usuarios");
    }
}
