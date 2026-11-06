package Repositories;

import DB.Database;
import Models.Entrenador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorRepository {

    public boolean Insertar(Entrenador entrenador) {
        String sql = "INSERT INTO entrenador "
                + "(nombre, documento, telefono, correo, especialidad, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = Database.conectar()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, entrenador.getNombre());
                ps.setString(2, entrenador.getDocumento());
                ps.setString(3, entrenador.getTelefono());
                ps.setString(4, entrenador.getCorreo());
                ps.setString(5, entrenador.getEspecialidad());
                ps.setBoolean(6, entrenador.isEstado());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException ex) {
            System.out.println("Error al guardar entrenador: "
                    + ex.getMessage());
            return false;
        }
    }

    public List<Entrenador> Traer() {
        List<Entrenador> entrenadores = new ArrayList<>();

        String sql = "SELECT idEntrenador, nombre, documento, "
                + "telefono, correo, especialidad, estado "
                + "FROM entrenador WHERE estado = 1 ORDER BY idEntrenador";

        try (Connection conexion = Database.conectar()) {
            if (conexion == null) {
                return entrenadores;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Entrenador entrenador = new Entrenador(
                            rs.getInt("idEntrenador"),
                            rs.getString("nombre"),
                            rs.getString("documento"),
                            rs.getString("telefono"),
                            rs.getString("correo"),
                            rs.getString("especialidad"),
                            rs.getBoolean("estado")
                    );

                    entrenadores.add(entrenador);
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error al listar entrenadores: "
                    + ex.getMessage());
        }

        return entrenadores;
    }

    public boolean Actualizar(Entrenador entrenador) {
        String sql = "UPDATE entrenador SET nombre = ?, "
                + "documento = ?, telefono = ?, correo = ?, "
                + "especialidad = ?, estado = ? "
                + "WHERE idEntrenador = ?";

        try (Connection conexion = Database.conectar()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, entrenador.getNombre());
                ps.setString(2, entrenador.getDocumento());
                ps.setString(3, entrenador.getTelefono());
                ps.setString(4, entrenador.getCorreo());
                ps.setString(5, entrenador.getEspecialidad());
                ps.setBoolean(6, entrenador.isEstado());
                ps.setInt(7, entrenador.getIdEntrenador());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException ex) {
            System.out.println("Error al actualizar entrenador: "
                    + ex.getMessage());
            return false;
        }
    }

    public boolean Eliminar(int idEntrenador) {
        String sql = "UPDATE entrenador SET estado = 0 WHERE idEntrenador = ?";

        try (Connection conexion = Database.conectar()) {
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, idEntrenador);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException ex) {
            System.out.println("Error al desactivar entrenador: "
                    + ex.getMessage());
            return false;
        }
    }
}
