
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
   
}
