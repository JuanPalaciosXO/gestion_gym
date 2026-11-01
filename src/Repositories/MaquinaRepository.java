package Repositories;

import DB.Database;
import Models.Maquina;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaquinaRepository {
    public boolean Insertar(Maquina maquina) {
        Connection conexion = Database.conectar();
        String sql = "INSERT INTO maquina (id, nombre, tipo, estado, ubicacion, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
        
        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, maquina.getIdMn());
            stmt.setString(2, maquina.getNombre());
            stmt.setString(3, maquina.getTipo());
            stmt.setBoolean(4, maquina.getEstado());
            stmt.setString(5, maquina.getUbicacion());
            stmt.setString(6, maquina.getDescripcion());
            stmt.executeUpdate();
            return true;
        }catch (SQLException e) {
            System.out.println("Error al insertar máquina: " + e.getMessage());
            return false;
        }finally{
            Database.desconectar(conexion);
        }
    }
    
    public List<Maquina> Traer() {
        List<Maquina> lista = new ArrayList<>();
        Connection conexion = Database.conectar();
        String sql = "SELECT id, nombre, tipo, estado, ubicacion, descripcion FROM maquina";

        try(PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                Maquina m = new Maquina();
                m.setIdMn(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));
                m.setTipo(rs.getString("tipo"));
                m.setEstado(rs.getBoolean("estado"));
                m.setUbicacion(rs.getString("ubicacion"));
                m.setDescripcion(rs.getString("descripcion"));
                lista.add(m);
            }
        }catch(SQLException e) {
            System.out.println("Error al listar máquinas: " + e.getMessage());
        }finally{
            Database.desconectar(conexion);
        }
        return lista;
    }

    public Maquina ConsultarPorId(int idMn){
        Maquina maquina = null;
        Connection conexion = Database.conectar();
        String sql = "SELECT id, nombre, tipo, estado, ubicacion, descripcion FROM maquina WHERE id = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            stmt.setInt(1, idMn);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()) {
                    maquina = new Maquina();
                    maquina.setIdMn(rs.getInt("id"));
                    maquina.setNombre(rs.getString("nombre"));
                    maquina.setTipo(rs.getString("tipo"));
                    maquina.setEstado(rs.getBoolean("estado"));
                    maquina.setUbicacion(rs.getString("ubicacion"));
                    maquina.setDescripcion(rs.getString("descripcion"));
                }
            }
        }catch(SQLException e){
            System.out.println("Error al buscar máquina: " + e.getMessage());
        }finally{
            Database.desconectar(conexion);
        }
        return maquina;
    }

    public boolean Actualizar(Maquina maquina){
        Connection conexion = Database.conectar();
        String sql = "UPDATE maquina SET nombre = ?, tipo = ?, estado = ?, ubicacion = ?, descripcion = ? WHERE id = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            stmt.setString(1, maquina.getNombre());
            stmt.setString(2, maquina.getTipo());
            stmt.setBoolean(3, maquina.getEstado());
            stmt.setString(4, maquina.getUbicacion());
            stmt.setString(5, maquina.getDescripcion());
            stmt.setInt(6, maquina.getIdMn());

            int filas = stmt.executeUpdate();
            return filas > 0;
        }catch (SQLException e){
            System.out.println("Error al actualizar máquina: " + e.getMessage());
            return false;
        }finally{
            Database.desconectar(conexion);
        }
    }

    public boolean Eliminar(int idMn) {
        Connection conexion = Database.conectar();
        String sql = "DELETE FROM maquina WHERE id = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)){
            stmt.setInt(1, idMn);
            int filas = stmt.executeUpdate();
            return filas > 0;
        }catch (SQLException e){
            System.out.println("Error al eliminar máquina: " + e.getMessage());
            return false;
        }finally{
            Database.desconectar(conexion);
        }
    }
}
