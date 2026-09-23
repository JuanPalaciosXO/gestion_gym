
package Services;
import Models.Pago;


public class PagoService {
    public boolean Guardar(Pago pago){
        System.out.println("El pago se ha guardado exitosamente");
        return true;
    }
    
    public boolean Actualizar(Pago pago){
        System.out.println("Se actualizó correctamente");
        return true;
    }
    
    public boolean Eliminar(int id){
        System.out.println("Se elimino correctamente");
        return true;
    }
}
