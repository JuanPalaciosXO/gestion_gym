package Repositories;

import DB.Database;
import Models.Membresia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembRepository {

    private Membresia construir(ResultSet rs) throws SQLException {
        return new Membresia(
                rs.getInt("idMemb"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getString("descripcion"),
                rs.getBoolean("estado")
        );
    }

    public List<Membresia> Consultar() {
        List<Membresia> lista = new ArrayList<>();
        Connection conexion = Database.conectar();
        if (conexion == null) {
            return lista;
        }
        String sql = "SELECT idMemb, nombre, precio, descripcion, estado FROM membresia";

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(construir(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar membresías: " + e.getMessage());
        } finally {
            Database.desconectar(conexion);
        }
        return lista;
    }

    public boolean Insertar(Membresia membresia) {
        Connection conexion = Database.conectar();
        if (conexion == null) {
            return false;
        }

        String sql = "INSERT INTO membresia (nombre, precio, descripcion, estado) "
                + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, membresia.getNombre());
            stmt.setDouble(2, membresia.precio());
            stmt.setString(3, membresia.getDescripcion());
            stmt.setBoolean(4, membresia.getEstado());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar membresía: " + e.getMessage());
            return false;
        } finally {
            Database.desconectar(conexion);
        }
    }

    public boolean Actualizar(Membresia membresia) {
        Connection conexion = Database.conectar();
        if (conexion == null) {
            return false;
        }
        String sql = "UPDATE membresia SET nombre = ?, precio = ?, "
                + "descripcion = ?, estado = ? WHERE idMemb = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, membresia.getNombre());
            stmt.setDouble(2, membresia.precio());
            stmt.setString(3, membresia.getDescripcion());
            stmt.setBoolean(4, membresia.getEstado());
            stmt.setInt(5, membresia.getIdMemb());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar membresía: " + e.getMessage());
            return false;
        } finally {
            Database.desconectar(conexion);
        }
    }

    public boolean Eliminar(int idMemb) {
        Connection conexion = Database.conectar();
        if (conexion == null) {
            return false;
        }

        String sql = "UPDATE membresia SET estado = 0 WHERE idMemb = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idMemb);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al desactivar membresía: " + e.getMessage());
            return false;
        } finally {
            Database.desconectar(conexion);
        }
    }

    public Membresia ConsultarPorId(int idMemb) {
        Connection conexion = Database.conectar();
        if (conexion == null) {
            return null;
        }
        String sql = "SELECT idMemb, nombre, precio, descripcion, estado "
                + "FROM membresia WHERE idMemb = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idMemb);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construir(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar membresía: " + e.getMessage());
        } finally {
            Database.desconectar(conexion);
        }
        return null;
    }
}
