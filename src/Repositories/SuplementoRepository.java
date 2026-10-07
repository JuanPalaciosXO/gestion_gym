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
    public boolean Actualizar(Suplemento suplemento) {
    for (int i = 0; i < lsSuplemento.size(); i++) {
        if (lsSuplemento.get(i).getIdSu() == suplemento.getIdSu()) {
            lsSuplemento.set(i, suplemento);
            return true;
        }
    }
    return false;
    }
    public boolean Eliminar(int idSu) {
    for (int i = 0; i < lsSuplemento.size(); i++) {
        if (lsSuplemento.get(i).getIdSu() == idSu) {
            lsSuplemento.remove(i);
            return true;
        }
    }
    return false;
    }
    
    public Suplemento ConsultarPorId(int idSu) {
    for (int i = 0; i < lsSuplemento.size(); i++) {
        if (lsSuplemento.get(i).getIdSu() == idSu) {
            return lsSuplemento.get(i);
        }
    }
    return null;
    }
}
