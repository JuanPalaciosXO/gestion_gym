package Repositories;

import DB.Database;
import Models.Suplemento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuplementoRepository {
    
    public boolean Insertar(Suplemento suplemento){
        Connection conexion = Database.conectar();
        String sql = "INSERT INTO suplemento ( nombre, marca, precio, stock, descripcion, estado) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)){
            
            stmt.setString(1, suplemento.getNombre());
            stmt.setString(2, suplemento.getMarca());
            stmt.setDouble(3, suplemento.getPrecio());
            stmt.setInt(4, suplemento.getStock());
            stmt.setString(5, suplemento.getDescripcion());
            stmt.setBoolean(6, suplemento.getEstado());
            
            stmt.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println("Error al insertar suplemento: " + e.getMessage());
            return false;
        }finally{
            Database.desconectar(conexion);
        }
    }
    
    public List<Suplemento> Traer(){
        List<Suplemento> lista = new ArrayList<>();
        Connection conexion = Database.conectar();
        String sql = "SELECT idSu, nombre, marca, precio, stock, descripcion, estado FROM suplemento";

        try(PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Suplemento s = new Suplemento();
                s.setIdSu(rs.getInt("idSu"));
                s.setNombre(rs.getString("nombre"));
                s.setMarca(rs.getString("marca"));
                s.setPrecio(rs.getDouble("precio"));
                s.setStock(rs.getInt("stock"));
                s.setDescripcion(rs.getString("descripcion"));
                s.setEstado(rs.getBoolean("estado"));
                lista.add(s);
            }
        }catch(SQLException e) {
            System.out.println("Error al listar suplementos: " + e.getMessage());
        }finally{
            Database.desconectar(conexion);
        }
        return lista;
    }
    
    public Suplemento ConsultarPorId(int idSu){
        Suplemento suplemento = null;
        Connection conexion = Database.conectar();
        String sql = "SELECT idSu, nombre, marca, precio, stock, descripcion, estado FROM suplemento WHERE idSu = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idSu);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    suplemento = new Suplemento();
                    suplemento.setIdSu(rs.getInt("idSu"));
                    suplemento.setMarca(rs.getString("marca"));
                    suplemento.setNombre(rs.getString("nombre"));
                    suplemento.setPrecio(rs.getDouble("precio"));
                    suplemento.setStock(rs.getInt("stock"));
                    suplemento.setDescripcion(rs.getString("descripcion"));
                    suplemento.setEstado(rs.getBoolean("estado"));
                }
            }
        }catch(SQLException e) {
            System.out.println("Error al buscar suplemento: " + e.getMessage());
        }finally{
            Database.desconectar(conexion);
        }
        return suplemento;
    }
    
    public boolean Actualizar(Suplemento suplemento) {
        Connection conexion = Database.conectar();
        String sql = "UPDATE suplemento SET nombre = ?, marca = ?, precio = ?, stock = ?, descripcion = ?, estado = ? WHERE idSu = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, suplemento.getNombre());
            stmt.setString(2, suplemento.getMarca());       
            stmt.setDouble(3, suplemento.getPrecio());     
            stmt.setInt(4, suplemento.getStock());         
            stmt.setString(5, suplemento.getDescripcion()); 
            stmt.setBoolean(6, suplemento.getEstado()); 
            stmt.setInt(7, suplemento.getIdSu());

            int filas = stmt.executeUpdate();
            return filas > 0;
        }catch(SQLException e) {
            System.out.println("Error al actualizar suplemento: " + e.getMessage());
            return false;
        }finally{
            Database.desconectar(conexion);
        }
    }

    public boolean Eliminar(int idSu) {
        Connection conexion = Database.conectar();
        String sql = "DELETE FROM suplemento WHERE idSu = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idSu);
            int filas = stmt.executeUpdate();
            return filas > 0;
        }catch(SQLException e) {
            System.out.println("Error al eliminar suplemento: " + e.getMessage());
            return false;
        }finally{
            Database.desconectar(conexion);
        }
    }
}
