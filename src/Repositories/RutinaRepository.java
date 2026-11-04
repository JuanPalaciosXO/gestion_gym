package Repositories;

import DB.Database;
import Models.Rutina;
import java.sql.*;
import java.util.ArrayList;

public class RutinaRepository {

    public boolean Guardar(Rutina rutina) {
        String sql = "INSERT INTO rutinas (id, nombre, objetivo, nivel, descripcion, estado) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, rutina.getId());
            pstmt.setString(2, rutina.getNombre());
            pstmt.setString(3, rutina.getObjetivo());
            pstmt.setString(4, rutina.getNivel());
            pstmt.setString(5, rutina.getDescripcion());
            pstmt.setBoolean(6, rutina.isEstado());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al guardar rutina: " + e.getMessage());
            return false;
        }
    }

    public boolean Actualizar(Rutina rutina) {
        String sql = "UPDATE rutinas SET nombre = ?, objetivo = ?, nivel = ?, descripcion = ?, estado = ? WHERE id = ?";
        
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, rutina.getNombre());
            pstmt.setString(2, rutina.getObjetivo());
            pstmt.setString(3, rutina.getNivel());
            pstmt.setString(4, rutina.getDescripcion());
            pstmt.setBoolean(5, rutina.isEstado());
            pstmt.setInt(6, rutina.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar rutina: " + e.getMessage());
            return false;
        }
    }

    public boolean Eliminar(int idRutina) {
        String sql = "DELETE FROM rutinas WHERE id = ?";
        
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idRutina);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar rutina: " + e.getMessage());
            return false;
        }
    }

    public Rutina ConsultarPorId(int idRutina) {
        String sql = "SELECT * FROM rutinas WHERE id = ?";
        Rutina rutina = null;
        
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idRutina);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    rutina = new Rutina(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("objetivo"),
                        rs.getString("nivel"),
                        rs.getString("descripcion"),
                        rs.getBoolean("estado")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consultar rutina por ID: " + e.getMessage());
        }
        
        return rutina;
    }

    public ArrayList<Rutina> Listar() {
        ArrayList<Rutina> lista = new ArrayList<>();
        String sql = "SELECT * FROM rutinas";
        
        try (Connection conn = Database.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Rutina r = new Rutina(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("objetivo"),
                    rs.getString("nivel"),
                    rs.getString("descripcion"),
                    rs.getBoolean("estado")
                );
                lista.add(r);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al listar rutinas: " + e.getMessage());
        }
        
        return lista;
    }
}