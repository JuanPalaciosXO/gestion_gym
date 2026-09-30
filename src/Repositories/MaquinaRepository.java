package Repositories;

import Models.Maquina;
import java.util.ArrayList;
import java.util.List;

public class MaquinaRepository {
    private List<Maquina> lstMaquina;
    
    public MaquinaRepository(){
        this.lstMaquina = new ArrayList<>();
        
        Maquina press_banca = new Maquina(1,"Press de banca","Peso libre", true, "Segundo piso","Maquina especializada para entrenar pecho");
        Maquina remo = new Maquina(2,"Remo en T","Peso libre",true,"Segundo piso","Maquina especializada para entrenar la espalda");
        
        this.lstMaquina.add(press_banca);
        this.lstMaquina.add(remo);
    }
    
    public List<Maquina> Traer(){
        return this.lstMaquina;
    }
    
    public boolean Insertar(Maquina maquina){
        this.lstMaquina.add(maquina);
        return true;
    }
}
