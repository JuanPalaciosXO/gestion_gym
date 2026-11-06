package Repositories;

import DB.Database;
import Models.Pago;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagoRepository {

    private Pago construir(ResultSet rs) throws SQLException {
        return new Pago(
                rs.getInt("idPago"),
                rs.getDate("fecha").toLocalDate(),
                rs.getInt("valor"),
                rs.getString("metodo_pago"),
                rs.getBoolean("estado"),
                rs.getInt("idUsuario"),
                rs.getInt("idMemb")
        );
    }

    public List<Pago> Consultar() {
        List<Pago> lista = new ArrayList<>();
        Connection conexion = Database.conectar();
        if (conexion == null) {
            return lista;
        }
        String sql = "SELECT idPago, fecha, valor, metodo_pago, estado, idUsuario, idMemb FROM pago";

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(construir(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pagos: " + e.getMessage());
        } finally {
            Database.desconectar(conexion);
        }
        return lista;
    }

    public boolean Insertar(Pago pago) {
        Connection conexion = Database.conectar();
        if (conexion == null) {
            return false;
        }

        String sql = "INSERT INTO pago (fecha, valor, metodo_pago, estado, idUsuario, idMemb) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(pago.getFecha()));
            stmt.setInt(2, pago.getValor());
            stmt.setString(3, pago.getMetodoPago());
            stmt.setBoolean(4, pago.isEstado());
            stmt.setInt(5, pago.getIdUsuario());
            stmt.setInt(6, pago.getIdMembresia());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar pago: " + e.getMessage());
            return false;
        } finally {
            Database.desconectar(conexion);
        }
    }

    public boolean Actualizar(Pago pago) {
        Connection conexion = Database.conectar();
        if (conexion == null) {
            return false;
        }
        String sql = "UPDATE pago SET fecha = ?, valor = ?, metodo_pago = ?, estado = ?, "
                + "idUsuario = ?, idMemb = ? WHERE idPago = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(pago.getFecha()));
            stmt.setInt(2, pago.getValor());
            stmt.setString(3, pago.getMetodoPago());
            stmt.setBoolean(4, pago.isEstado());
            stmt.setInt(5, pago.getIdUsuario());
            stmt.setInt(6, pago.getIdMembresia());
            stmt.setInt(7, pago.getIdPago());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar pago: " + e.getMessage());
            return false;
        } finally {
            Database.desconectar(conexion);
        }
    }

    public boolean Eliminar(int idPago) {
        Connection conexion = Database.conectar();
        if (conexion == null) {
            return false;
        }

        String sql = "UPDATE pago SET estado = 0 WHERE idPago = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPago);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al anular pago: " + e.getMessage());
            return false;
        } finally {
            Database.desconectar(conexion);
        }
    }

    public Pago ConsultarPorId(int idPago) {
        Connection conexion = Database.conectar();
        if (conexion == null) {
            return null;
        }
        String sql = "SELECT idPago, fecha, valor, metodo_pago, estado, idUsuario, idMemb "
                + "FROM pago WHERE idPago = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idPago);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construir(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar pago: " + e.getMessage());
        } finally {
            Database.desconectar(conexion);
        }
        return null;
    }
}
