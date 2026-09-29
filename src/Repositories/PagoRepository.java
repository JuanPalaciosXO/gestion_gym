
package Repositories;
import Models.Pago;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class PagoRepository {
    private List<Pago> lstPagos;
    
    public PagoRepository(){
        this.lstPagos = new ArrayList<>();
        Pago juanLopez = new Pago(1, LocalDate.of(2026, 9, 23), 150000, "Efectivo", true, 1, 1);
        Pago sebasrojas = new Pago(1, LocalDate.of(2026, 7, 7), 150000, "Efectivo", true, 1, 1);
        this.lstPagos.add(juanLopez);
        this.lstPagos.add(sebasrojas);
    }
    
    public List<Pago> Consultar(){
        return this.lstPagos;
    }
}
