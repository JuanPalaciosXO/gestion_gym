package Repositories;

import Models.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private List<Usuario> lstUsuarios;

    public UsuarioRepository() {
        this.lstUsuarios = new ArrayList<>();
    }

    public boolean Insertar(Usuario usuario) {
        this.lstUsuarios.add(usuario);
        return true;
    }

    public List<Usuario> Traer() {
        return this.lstUsuarios;
    }

    public boolean Actualizar(Usuario usuario) {

        for (int i = 0; i < lstUsuarios.size(); i++) {

            if (lstUsuarios.get(i).getIdUsuario() == usuario.getIdUsuario()) {
                lstUsuarios.set(i, usuario);
                return true;
            }
        }

        return false;
    }

    public boolean Eliminar(int idUsuario) {

        for (int i = 0; i < lstUsuarios.size(); i++) {

            if (lstUsuarios.get(i).getIdUsuario() == idUsuario) {
                lstUsuarios.remove(i);
                return true;
            }
        }

        return false;
    }

}
