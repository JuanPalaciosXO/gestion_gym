package Repositories;
 
import DB.Database;
import Models.Pago;
import Models.PagoSuplemento;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
public class PagoRepository {

    private String ultimoError;

    public String getUltimoError() {
        return ultimoError;
    }
 
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
        String sql = "SELECT idPago, fecha, valor, metodo_pago, estado, idUsuario, idMemb "
                   + "FROM pago ORDER BY idPago";
 
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
 
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
        return Insertar(pago, new ArrayList<>());
    }

    public boolean Insertar(Pago pago, List<PagoSuplemento> lineas) {
        ultimoError = null;
        Connection conexion = Database.conectar();
        if (conexion == null) {
            ultimoError = "No hay conexión con la base de datos.";
            return false;
        }
        String sqlPago = "INSERT INTO pago (idPago, fecha, valor, metodo_pago, estado, idUsuario, idMemb) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlStock = "UPDATE suplemento SET stock = stock - ? "
                        + "WHERE idSu = ? AND estado = 1 AND stock >= ?";
        String sqlLinea = "INSERT INTO pago_suplemento (idPago, idSu, cantidad, precio_unitario) "
                        + "VALUES (?, ?, ?, ?)";

        try {
            conexion.setAutoCommit(false);

            int idPago;
            try (PreparedStatement stmt = conexion.prepareStatement(sqlPago, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, pago.getIdPago());
                stmt.setDate(2, Date.valueOf(pago.getFecha()));
                stmt.setInt(3, pago.getValor());
                stmt.setString(4, pago.getMetodoPago());
                stmt.setBoolean(5, pago.isEstado());
                stmt.setInt(6, pago.getIdUsuario());
                stmt.setInt(7, pago.getIdMembresia());
                stmt.executeUpdate();

                int generado = 0;
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        generado = keys.getInt(1);
                    }
                }
                idPago = pago.getIdPago() > 0 ? pago.getIdPago() : generado;
            }

            for (PagoSuplemento linea : lineas) {
                try (PreparedStatement stmt = conexion.prepareStatement(sqlStock)) {
                    stmt.setInt(1, linea.getCantidad());
                    stmt.setInt(2, linea.getIdSu());
                    stmt.setInt(3, linea.getCantidad());
                    if (stmt.executeUpdate() == 0) {
                        throw new SQLException("No hay stock suficiente (o el suplemento está inactivo): id "
                                + linea.getIdSu());
                    }
                }
                try (PreparedStatement stmt = conexion.prepareStatement(sqlLinea)) {
                    stmt.setInt(1, idPago);
                    stmt.setInt(2, linea.getIdSu());
                    stmt.setInt(3, linea.getCantidad());
                    stmt.setDouble(4, linea.getPrecioUnitario());
                    stmt.executeUpdate();
                }
                linea.setIdPago(idPago);
            }

            conexion.commit();
            pago.setIdPago(idPago);
            return true;

        } catch (SQLException e) {
            deshacer(conexion);
            ultimoError = e.getMessage();
            System.out.println("Error al insertar pago: " + e.getMessage());
            return false;
        } finally {
            restaurarAutoCommit(conexion);
            Database.desconectar(conexion);
        }
    }
 
    public boolean Actualizar(Pago pago) {
        ultimoError = null;
        Connection conexion = Database.conectar();
        if (conexion == null) {
            ultimoError = "No hay conexión con la base de datos.";
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
            ultimoError = e.getMessage();
            System.out.println("Error al actualizar pago: " + e.getMessage());
            return false;
        } finally {
            Database.desconectar(conexion);
        }
    }

    public boolean Eliminar(int idPago) {
        ultimoError = null;
        Connection conexion = Database.conectar();
        if (conexion == null) {
            ultimoError = "No hay conexión con la base de datos.";
            return false;
        }

        try {
            conexion.setAutoCommit(false);

            try (PreparedStatement stmt = conexion.prepareStatement(
                    "UPDATE pago SET estado = 0 WHERE idPago = ? AND estado = 1")) {
                stmt.setInt(1, idPago);
                if (stmt.executeUpdate() == 0) {
                    deshacer(conexion);
                    ultimoError = "El pago no existe o ya estaba anulado.";
                    return false;
                }
            }

            List<int[]> lineas = new ArrayList<>();
            try (PreparedStatement stmt = conexion.prepareStatement(
                    "SELECT idSu, cantidad FROM pago_suplemento WHERE idPago = ?")) {
                stmt.setInt(1, idPago);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        lineas.add(new int[]{rs.getInt("idSu"), rs.getInt("cantidad")});
                    }
                }
            }

            try (PreparedStatement stmt = conexion.prepareStatement(
                    "UPDATE suplemento SET stock = stock + ? WHERE idSu = ?")) {
                for (int[] linea : lineas) {
                    stmt.setInt(1, linea[1]);
                    stmt.setInt(2, linea[0]);
                    stmt.executeUpdate();
                }
            }

            conexion.commit();
            return true;

        } catch (SQLException e) {
            deshacer(conexion);
            ultimoError = e.getMessage();
            System.out.println("Error al anular pago: " + e.getMessage());
            return false;
        } finally {
            restaurarAutoCommit(conexion);
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

    public List<PagoSuplemento> ConsultarSuplementos(int idPago) {
        List<PagoSuplemento> lista = new ArrayList<>();
        Connection conexion = Database.conectar();
        if (conexion == null) {
            return lista;
        }
        String sql = "SELECT ps.idPago, ps.idSu, ps.cantidad, ps.precio_unitario, s.nombre "
                   + "FROM pago_suplemento ps JOIN suplemento s ON s.idSu = ps.idSu "
                   + "WHERE ps.idPago = ? ORDER BY s.nombre";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idPago);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new PagoSuplemento(
                            rs.getInt("idPago"),
                            rs.getInt("idSu"),
                            rs.getInt("cantidad"),
                            rs.getDouble("precio_unitario"),
                            rs.getString("nombre")));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar suplementos del pago: " + e.getMessage());
        } finally {
            Database.desconectar(conexion);
        }
        return lista;
    }

    private void deshacer(Connection conexion) {
        try {
            conexion.rollback();
        } catch (SQLException ex) {
            System.out.println("Error al deshacer la transacción: " + ex.getMessage());
        }
    }

    private void restaurarAutoCommit(Connection conexion) {
        try {
            conexion.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("Error al restaurar la conexión: " + ex.getMessage());
        }
    }
}