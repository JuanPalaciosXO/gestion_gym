package Repositories;

import DB.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRutinaRepository {

    public boolean asignarRutina(int idUsuario, int idRutina) {
        String sql = "INSERT INTO usuario_rutina "
                + "(idUsuario, idRutina) VALUES (?, ?)";

        try (Connection conexion = Database.conectar()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                ps.setInt(2, idRutina);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al asignar rutina: " + e.getMessage());
            return false;
        }
    }

    public List<Integer> obtenerRutinas(int idUsuario) {
        List<Integer> rutinas = new ArrayList<>();

        String sql = "SELECT idRutina FROM usuario_rutina "
                + "WHERE idUsuario = ?";

        try (Connection conexion = Database.conectar()) {
            if (conexion == null) {
                return rutinas;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        rutinas.add(rs.getInt("idRutina"));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar rutinas: "
                    + e.getMessage());
        }

        return rutinas;
    }

    public boolean quitarRutina(int idUsuario, int idRutina) {
        String sql = "DELETE FROM usuario_rutina "
                + "WHERE idUsuario = ? AND idRutina = ?";

        try (Connection conexion = Database.conectar()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                ps.setInt(2, idRutina);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al quitar rutina: " + e.getMessage());
            return false;
        }
    }
}
