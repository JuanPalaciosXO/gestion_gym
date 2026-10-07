
package Repositories;
import Models.Membresia;
import java.util.ArrayList;
import java.util.List;


public class MembRepository {
    private List<Membresia> lstMemb;
    
    public MembRepository(){
        this.lstMemb = new ArrayList<>();
        Membresia plan_semestral = new Membresia(1, "Plan semestral", 700.000, 6, "Plan semestral con todo incluido", true);
        Membresia plan_anual = new Membresia(1, "Plan anual", 1.200000, 12, "Plan anual con todo incluido", false);
        this.lstMemb.add(plan_semestral);
        this.lstMemb.add(plan_anual);
    }
    
    public List<Membresia> Consultar(){
        return this.lstMemb;
    }
    
    public boolean Insertar(Membresia membresia){
        this.lstMemb.add(membresia);
        return true;
    }
    
    public boolean Actualizar(Membresia membresia) {
    for (int i = 0; i < lstMemb.size(); i++) {
        if (lstMemb.get(i).getIdMemb() == membresia.getIdMemb()) {
            lstMemb.set(i, membresia);
            return true;
        }
    }
        return false;
    }
    
    public boolean Eliminar(int idMemb) {
    for (int i = 0; i < lstMemb.size(); i++) {
        if (lstMemb.get(i).getIdMemb() == idMemb) {
            lstMemb.remove(i);
            return true;
        }
    }
        return false;
    }
    
    public Membresia ConsultarPorId(int idMemb) {
    for (int i = 0; i < lstMemb.size(); i++) {
        if (lstMemb.get(i).getIdMemb() == idMemb) {
            return lstMemb.get(i);
        }
    }
        return null;
    }
   
}
