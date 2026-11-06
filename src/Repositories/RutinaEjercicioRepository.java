package Repositories;

import DB.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RutinaEjercicioRepository {

    public boolean asignarEjercicio(int idRutina, int idEjercicio) {
        String sql = "INSERT INTO rutina_ejercicio "
                + "(idRutina, idEjercicio) VALUES (?, ?)";

        try (Connection conn = Database.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idRutina);
            pstmt.setInt(2, idEjercicio);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al asignar ejercicio: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Integer> obtenerEjercicios(int idRutina) {
        ArrayList<Integer> ejercicios = new ArrayList<>();

        String sql = "SELECT re.idEjercicio "
                + "FROM rutina_ejercicio re "
                + "INNER JOIN ejercicio e ON re.idEjercicio = e.idEjercicio "
                + "WHERE re.idRutina = ? AND e.estado = 1 "
                + "ORDER BY re.idEjercicio";

        try (Connection conn = Database.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idRutina);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ejercicios.add(rs.getInt("idEjercicio"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar ejercicios de la rutina: "
                    + e.getMessage());
        }

        return ejercicios;
    }

    public boolean quitarEjercicio(int idRutina, int idEjercicio) {
        String sql = "DELETE FROM rutina_ejercicio "
                + "WHERE idRutina = ? AND idEjercicio = ?";

        try (Connection conn = Database.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idRutina);
            pstmt.setInt(2, idEjercicio);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al quitar ejercicio de la rutina: "
                    + e.getMessage());
            return false;
        }
    }
}
