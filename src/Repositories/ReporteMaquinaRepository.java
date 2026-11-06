package Repositories;

import DB.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReporteMaquinaRepository {
    private List<Object[]> consultar(String sql) {
        List<Object[]> filas = new ArrayList<>();
        Connection conexion = Database.conectar();
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            int cols = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] fila = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                filas.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error en reporte: " + e.getMessage());
        } finally {
            Database.desconectar(conexion);
        }
        return filas;
    }

    public List<Object[]> frecuenciaUso(int limite, String orden) {
        String dir = "ASC".equalsIgnoreCase(orden) ? "ASC" : "DESC"; 
        String sql = "SELECT m.nombre, m.tipo, m.ubicacion, COUNT(e.idEjercicio) AS veces "
                + "FROM maquina m "
                + "LEFT JOIN ejercicio e ON e.idMn = m.idMn "
                + "WHERE m.estado = 1 "
                + "GROUP BY m.idMn, m.nombre, m.tipo, m.ubicacion "
                + "ORDER BY veces " + dir + ", m.nombre "
                + (limite > 0 ? "LIMIT " + limite : "");
        return consultar(sql);
    }

    public List<Object[]> sinEjercicios() {
        String sql = "SELECT m.idMn, m.nombre, m.tipo, m.ubicacion, m.estado "
                + "FROM maquina m "
                + "LEFT JOIN ejercicio e ON e.idMn = m.idMn "
                + "WHERE m.estado = 1 AND e.idEjercicio IS NULL";
        return consultar(sql);
    }

    public List<Object[]> obtenerMaquinasSinUso() {
        return sinEjercicios();
    }

    public int obtenerConteoMaquinas(int estado) {
        int conteo = 0;
        Connection conexion = Database.conectar();
        String sql = "SELECT COUNT(*) FROM maquina WHERE estado = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, estado);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    conteo = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al contar máquinas: " + e.getMessage());
        } finally {
            Database.desconectar(conexion);
        }
        return conteo;
    }

    public int contarConEjercicios() {
        int total = 0;
        Connection conexion = Database.conectar();
        String sql = "SELECT COUNT(DISTINCT m.idMn) FROM maquina m "
                   + "JOIN ejercicio e ON e.idMn = m.idMn WHERE m.estado = 1";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al contar máquinas con ejercicios: " + e.getMessage());
        } finally {
            Database.desconectar(conexion);
        }
        return total;
    }
    
    public boolean cambiarEstadoMaquina(int idMn, boolean estado) {
        String sql = "UPDATE maquina SET estado = ? WHERE idMn = ?";
        try (Connection conexion = Database.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setBoolean(1, estado);
            stmt.setInt(2, idMn);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado de máquina: " + e.getMessage());
            return false;
        }
    }
    
    public List<Object[]> obtenerMaquinasSinEjercicios() {
        String sql = "SELECT m.idMn, m.nombre, m.tipo, m.ubicacion, m.estado "
                + "FROM maquina m "
                + "LEFT JOIN ejercicio e ON e.idMn = m.idMn "
                + "WHERE m.estado = 1 AND e.idEjercicio IS NULL";
        return consultar(sql);
    }
    
    public List<Object[]> obtenerHistorialActividad() {
        List<Object[]> lista = new ArrayList<>();
        String sql ="SELECT m.nombre AS maquina, e.nombre AS ejercicio, 'Vinculado' AS detalle " +
                 "FROM maquina m " +
                 "JOIN ejercicio e ON m.idMn = e.idMn";

        try (Connection con = Database.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maquina = rs.getString("maquina");
                String ejercicio = rs.getString("ejercicio");
                String detalle = rs.getString("detalle");
                lista.add(new Object[]{maquina, ejercicio, detalle});
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener historial de actividad: " + e.getMessage());
        }
        return lista;
    }
    
    public List<Object[]> obtenerReporteGeneralMaquinas() {
        String sql = "SELECT idMn, nombre, tipo, ubicacion, descripcion, estado FROM maquina";
        return consultar(sql);
    }

}
