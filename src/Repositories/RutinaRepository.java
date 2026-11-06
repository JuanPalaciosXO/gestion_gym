package Repositories;

import DB.Database;
import Models.Rutina;
import java.sql.*;
import java.util.ArrayList;

public class RutinaRepository {

    public boolean Guardar(Rutina rutina) {
        String sql = "INSERT INTO rutina "
                + "(nombre, objetivo, nivel, descripcion, estado, idEntrenador) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.conectar()) {
            if (conn == null) {
                return false;
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, rutina.getNombre());
                pstmt.setString(2, rutina.getObjetivo());
                pstmt.setString(3, rutina.getNivel());
                pstmt.setString(4, rutina.getDescripcion());
                pstmt.setBoolean(5, rutina.isEstado());
                if (rutina.getIdEntrenador() != null) {
                    pstmt.setInt(6, rutina.getIdEntrenador());
                } else {
                    pstmt.setNull(6, java.sql.Types.INTEGER);
                }

                return pstmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar rutina: " + e.getMessage());
            return false;
        }
    }

    public boolean Actualizar(Rutina rutina) {
        String sql = "UPDATE rutina SET nombre = ?, "
                + "objetivo = ?, nivel = ?, descripcion = ?, "
                + "estado = ?, idEntrenador = ? WHERE idRutina = ?";

        try (Connection conn = Database.conectar()) {
            if (conn == null) {
                return false;
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, rutina.getNombre());
                pstmt.setString(2, rutina.getObjetivo());
                pstmt.setString(3, rutina.getNivel());
                pstmt.setString(4, rutina.getDescripcion());
                pstmt.setBoolean(5, rutina.isEstado());
                if (rutina.getIdEntrenador() != null) {
                    pstmt.setInt(6, rutina.getIdEntrenador());
                } else {
                    pstmt.setNull(6, java.sql.Types.INTEGER);
                }

                pstmt.setInt(7, rutina.getId());

                return pstmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar rutina: " + e.getMessage());
            return false;
        }
    }

    public boolean Eliminar(int idRutina) {
        String sql = "UPDATE rutina SET estado = 0 "
                + "WHERE idRutina = ?";

        try (Connection conn = Database.conectar()) {
            if (conn == null) {
                return false;
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idRutina);
                return pstmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al desactivar rutina: " + e.getMessage());
            return false;
        }
    }

    public Rutina ConsultarPorId(int idRutina) {
        String sql = "SELECT * FROM rutina "
                + "WHERE idRutina = ? AND estado = 1";

        try (Connection conn = Database.conectar()) {
            if (conn == null) {
                return null;
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idRutina);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return new Rutina(
                                rs.getInt("idRutina"),
                                rs.getString("nombre"),
                                rs.getString("objetivo"),
                                rs.getString("nivel"),
                                rs.getString("descripcion"),
                                rs.getBoolean("estado"),
                                rs.getObject("idEntrenador", Integer.class)
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar rutina por ID: "
                    + e.getMessage());
        }

        return null;
    }

    public ArrayList<Rutina> Listar() {
        ArrayList<Rutina> lista = new ArrayList<>();

        String sql = "SELECT * FROM rutina "
                + "WHERE estado = 1 ORDER BY idRutina";

        try (Connection conn = Database.conectar()) {
            if (conn == null) {
                return lista;
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Rutina rutina = new Rutina(
                            rs.getInt("idRutina"),
                            rs.getString("nombre"),
                            rs.getString("objetivo"),
                            rs.getString("nivel"),
                            rs.getString("descripcion"),
                            rs.getBoolean("estado"),
                            rs.getObject("idEntrenador", Integer.class)
                    );

                    lista.add(rutina);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar rutinas: "
                    + e.getMessage());
        }

        return lista;
    }
}
