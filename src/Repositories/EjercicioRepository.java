package Repositories;

import DB.Database;
import Models.Ejercicio;
import java.sql.*;
import java.util.ArrayList;

public class EjercicioRepository {

    public boolean Guardar(Ejercicio ejercicio) {
        String sql = "INSERT INTO ejercicios (id, nombre, tipo_muscular, series, repeticiones, duracion, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ejercicio.getId());
            pstmt.setString(2, ejercicio.getNombre());
            pstmt.setString(3, ejercicio.getTipoMuscular());
            pstmt.setInt(4, ejercicio.getSeries());
            pstmt.setInt(5, ejercicio.getRepeticiones());
            pstmt.setInt(6, ejercicio.getDuracion());
            pstmt.setBoolean(7, ejercicio.isEstado());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al guardar ejercicio: " + e.getMessage());
            return false;
        }
    }

    public boolean Actualizar(Ejercicio ejercicio) {
        String sql = "UPDATE ejercicios SET nombre = ?, tipo_muscular = ?, series = ?, repeticiones = ?, duracion = ?, estado = ? WHERE id = ?";
        
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ejercicio.getNombre());
            pstmt.setString(2, ejercicio.getTipoMuscular());
            pstmt.setInt(3, ejercicio.getSeries());
            pstmt.setInt(4, ejercicio.getRepeticiones());
            pstmt.setInt(5, ejercicio.getDuracion());
            pstmt.setBoolean(6, ejercicio.isEstado());
            pstmt.setInt(7, ejercicio.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar ejercicio: " + e.getMessage());
            return false;
        }
    }

    public boolean Eliminar(int idEjercicio) {
        String sql = "DELETE FROM ejercicios WHERE id = ?";
        
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idEjercicio);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar ejercicio: " + e.getMessage());
            return false;
        }
    }

    public Ejercicio ConsultarPorId(int idEjercicio) {
        String sql = "SELECT * FROM ejercicios WHERE id = ?";
        Ejercicio ejercicio = null;
        
        try (Connection conn = Database.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idEjercicio);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ejercicio = new Ejercicio(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("tipo_muscular"),
                        rs.getInt("series"),
                        rs.getInt("repeticiones"),
                        rs.getInt("duracion"),
                        rs.getBoolean("estado")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consultar ejercicio por ID: " + e.getMessage());
        }
        
        return ejercicio;
    }

    public ArrayList<Ejercicio> Listar() {
        ArrayList<Ejercicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM ejercicios";
        
        try (Connection conn = Database.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Ejercicio e = new Ejercicio(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("tipo_muscular"),
                    rs.getInt("series"),
                    rs.getInt("repeticiones"),
                    rs.getInt("duracion"),
                    rs.getBoolean("estado")
                );
                lista.add(e);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al listar ejercicios: " + e.getMessage());
        }
        
        return lista;
    }
}