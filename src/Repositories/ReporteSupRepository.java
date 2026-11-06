package Repositories;
import DB.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReporteSupRepository {
    public double calcularValorInventario() {
        double total = 0;
        String sql = "SELECT SUM(precio * stock) AS valor_total FROM suplemento";
        try (Connection con = Database.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble("valor_total");
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular valor del inventario: " + e.getMessage());
        }
        return total;
    }
    
    public List<Object[]> obtenerReporteStockBajo(int stockMinimo, int stockMeta) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT nombre, precio, stock, " +
                     "CASE WHEN stock < ? THEN (? - stock) ELSE 0 END AS unidades_a_comprar, " +
                     "CASE WHEN stock < ? THEN ((? - stock) * precio) ELSE 0 END AS inversion_necesaria " +
                     "FROM suplemento WHERE stock < ?";

        try (Connection con = Database.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, stockMinimo);
            ps.setInt(2, stockMeta);
            ps.setInt(3, stockMinimo);
            ps.setInt(4, stockMeta);
            ps.setInt(5, stockMinimo);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    int stock = rs.getInt("stock");
                    int comprar = rs.getInt("unidades_a_comprar");
                    double inversion = rs.getDouble("inversion_necesaria");
                    
                    lista.add(new Object[]{nombre, precio, stock, comprar, inversion});
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener stock bajo: " + e.getMessage());
        }
        return lista;
    }
    
    public List<Object[]> simularPreciosDescuento(double porcentajeDescuento) {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT nombre, precio, stock FROM suplemento";

        try (Connection con = Database.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                double precioOriginal = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                
                double precioDescuento = precioOriginal - (precioOriginal * (porcentajeDescuento / 100.0));
                
                lista.add(new Object[]{nombre, precioOriginal, precioDescuento, stock});
            }
        } catch (SQLException e) {
            System.err.println("Error al simular descuentos: " + e.getMessage());
        }
        return lista;
    }
}
