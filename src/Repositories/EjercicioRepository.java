package Repositories;

import DB.Database;
import Models.Ejercicio;
import java.sql.*;
import java.util.ArrayList;

public class EjercicioRepository {

    public boolean Guardar(Ejercicio ejercicio) {
        String sql = "INSERT INTO ejercicio "
                + "(nombre, tipoMuscular, series, repeticiones, duracion, estado, idMn) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ejercicio.getNombre());
            pstmt.setString(2, ejercicio.getTipoMuscular());
            pstmt.setInt(3, ejercicio.getSeries());
            pstmt.setInt(4, ejercicio.getRepeticiones());
            pstmt.setInt(5, ejercicio.getDuracion());
            pstmt.setBoolean(6, ejercicio.isEstado());
            if (ejercicio.getIdMn() != null) {
                pstmt.setInt(7, ejercicio.getIdMn());
            } else {
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar ejercicio: " + e.getMessage());
            return false;
        }
    }

    public boolean Actualizar(Ejercicio ejercicio) {
        String sql = "UPDATE ejercicio SET "
                + "nombre = ?, tipoMuscular = ?, series = ?, "
                + "repeticiones = ?, duracion = ?, estado = ?, idMn = ? "
                + "WHERE idEjercicio = ?";
        try (Connection conn = Database.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ejercicio.getNombre());
            pstmt.setString(2, ejercicio.getTipoMuscular());
            pstmt.setInt(3, ejercicio.getSeries());
            pstmt.setInt(4, ejercicio.getRepeticiones());
            pstmt.setInt(5, ejercicio.getDuracion());
            pstmt.setBoolean(6, ejercicio.isEstado());
            if (ejercicio.getIdMn() != null) {
                pstmt.setInt(7, ejercicio.getIdMn());
            } else {
                pstmt.setNull(7, java.sql.Types.INTEGER);
            }

            pstmt.setInt(8, ejercicio.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar ejercicio: " + e.getMessage());
            return false;
        }
    }

    public boolean Eliminar(int idEjercicio) {
        String sql = "UPDATE ejercicio SET estado = 0 "
                + "WHERE idEjercicio = ? AND estado = 1";

        try (Connection conn = Database.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEjercicio);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al desactivar ejercicio: " + e.getMessage());
            return false;
        }
    }

    public Ejercicio ConsultarPorId(int idEjercicio) {
        String sql = "SELECT * FROM ejercicio "
                + "WHERE idEjercicio = ? AND estado = 1";

        try (Connection conn = Database.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEjercicio);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Ejercicio(
                            rs.getInt("idEjercicio"),
                            rs.getString("nombre"),
                            rs.getString("tipoMuscular"),
                            rs.getInt("series"),
                            rs.getInt("repeticiones"),
                            rs.getInt("duracion"),
                            rs.getBoolean("estado"),
                            rs.getObject("idMn", Integer.class)
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar ejercicio: " + e.getMessage());
        }

        return null;
    }

    public ArrayList<Ejercicio> Listar() {
        ArrayList<Ejercicio> lista = new ArrayList<>();

        String sql = "SELECT * FROM ejercicio "
                + "WHERE estado = 1 ORDER BY idEjercicio";

        try (Connection conn = Database.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ejercicio ejercicio = new Ejercicio(
                        rs.getInt("idEjercicio"),
                        rs.getString("nombre"),
                        rs.getString("tipoMuscular"),
                        rs.getInt("series"),
                        rs.getInt("repeticiones"),
                        rs.getInt("duracion"),
                        rs.getBoolean("estado"),
                        rs.getObject("idMn", Integer.class)
                );

                lista.add(ejercicio);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar ejercicios: " + e.getMessage());
        }

        return lista;
    }
}
