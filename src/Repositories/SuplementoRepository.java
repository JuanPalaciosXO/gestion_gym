package Repositories;

import Models.Suplemento;
import java.util.ArrayList;
import java.util.List;

public class SuplementoRepository {
    
    private List<Suplemento> lsSuplemento;
    
    public SuplementoRepository (){
        this.lsSuplemento = new ArrayList<>();
        
        Suplemento proteina = new Suplemento(1,"Whey Protein","Optimum nutrition",142.000,20,"Aumenta masa muscular",true);
        Suplemento creatina = new Suplemento(2,"Creatina Micronizada","Optimum nutrition",125.000,15,"Produce energia rapida para los musculos",true);
        
        this.lsSuplemento.add(proteina);
        this.lsSuplemento.add(creatina);
    }
    public List<Suplemento> Traer (){
        return this.lsSuplemento;
    }
    public boolean Insertar (Suplemento suplemento){
        this.lsSuplemento.add(suplemento);
        return true;
    }
}
