package Repositories;

import Models.Usuario;
import java.util.List;
import DB.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UsuarioRepository {

    public boolean Insertar(Usuario usuario) {

    String sql = "INSERT INTO usuarios "
        + "(nombre, documento, telefono, correo, fecha_nacimiento, estado, password_hash) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conexion = Database.conectar()) {

        if (conexion == null) {
            return false;
        }

        try (PreparedStatement ps = conexion.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getDocumento());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getCorreo());

            if (usuario.getFechaNacimiento() != null) {
                ps.setDate(5, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }

            ps.setBoolean(6, usuario.isEstado());
            ps.setString(7, usuario.getPasswordHash());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (java.sql.ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setIdUsuario(rs.getInt(1));
                    }
                }
                return true;
            }

        }

    } catch (SQLException e) {
        System.out.println("Error al insertar usuario: " + e.getMessage());
    }

    return false;
}

    public List<Usuario> Traer() {

    List<Usuario> usuarios = new ArrayList<>();

    String sql = "SELECT * FROM usuarios";

    try (Connection conexion = Database.conectar()) {

        if (conexion == null) {
            return usuarios;
        }

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setDocumento(rs.getString("documento"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setCorreo(rs.getString("correo"));

                java.sql.Date fecha = rs.getDate("fecha_nacimiento");

                if (fecha != null) {
                    usuario.setFechaNacimiento(fecha.toLocalDate());
                }

                usuario.setEstado(rs.getBoolean("estado"));
                usuario.setPasswordHash(rs.getString("password_hash"));
                usuario.setRutinas(new ArrayList<>());

                usuarios.add(usuario);
            }
        }

    } catch (SQLException e) {
        System.out.println("Error al consultar usuarios: " + e.getMessage());
    }

    return usuarios;
}

    public boolean Actualizar(Usuario usuario) {

    String sql = "UPDATE usuarios SET "
            + "nombre = ?, "
            + "documento = ?, "
            + "telefono = ?, "
            + "correo = ?, "
            + "fecha_nacimiento = ?, "
            + "estado = ? "
            + "WHERE idUsuario = ?";

    try (Connection conexion = Database.conectar()) {

        if (conexion == null) {
            return false;
        }

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getDocumento());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getCorreo());

            if (usuario.getFechaNacimiento() != null) {
                ps.setDate(5,
                        java.sql.Date.valueOf(usuario.getFechaNacimiento()));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }

            ps.setBoolean(6, usuario.isEstado());
            ps.setInt(7, usuario.getIdUsuario());

            int filas = ps.executeUpdate();

            return filas > 0;
        }

    } catch (SQLException e) {
        System.out.println("Error al actualizar usuario: " + e.getMessage());
    }

    return false;
}

    
public boolean Eliminar(int idUsuario) {

    String sql = "DELETE FROM usuarios WHERE idUsuario = ?";

    try (Connection conexion = Database.conectar()) {

        if (conexion == null) {
            return false;
        }

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            int filas = ps.executeUpdate();

            return filas > 0;
        }

    } catch (SQLException e) {
        System.out.println("Error al eliminar usuario: " + e.getMessage());
    }

    return false;
}


public Usuario BuscarPorDocumento(String documento) {

    String sql = "SELECT * FROM usuarios WHERE documento = ?";

    try (Connection conexion = Database.conectar()) {

        if (conexion == null) {
            return null;
        }

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, documento);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(rs.getInt("idUsuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setDocumento(rs.getString("documento"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setCorreo(rs.getString("correo"));

                    java.sql.Date fecha = rs.getDate("fecha_nacimiento");

                    if (fecha != null) {
                        usuario.setFechaNacimiento(fecha.toLocalDate());
                    }

                    usuario.setEstado(rs.getBoolean("estado"));
                    usuario.setPasswordHash(rs.getString("password_hash"));

                    return usuario;
                }
            }
        }

    } catch (SQLException e) {
        System.out.println("Error al buscar usuario: " + e.getMessage());
    }

    return null;
}

}
