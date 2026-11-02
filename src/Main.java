
import Controllers.MembController;
import Controllers.PagoController;
import Controllers.UsuarioController;
import Models.Membresia;
import Models.Pago;
import Models.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        UsuarioController usuarioController = new UsuarioController();
        MembController membController = new MembController();
        PagoController pagoController = new PagoController();
        
        //usuarioController.Guardar(1, "Usuario Prueba", "1000000001", "3001234567", "prueba@correo.com", LocalDate.of(2000, 1, 15), true, new ArrayList<>(), "clave12345");
        //membController.Guardar(1, "Mensual", 700000.0, 1, "Membresia de prueba", true);
        //pagoController.Guardar(1, LocalDate.of(2026, 10, 1), 700000, "Efectivo", true, 1, 1);
        //System.out.println(pagoController.Listar().size());
    }
}   

