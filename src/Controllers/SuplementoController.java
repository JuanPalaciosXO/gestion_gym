
package Controllers;

import Models.Suplemento;
import java.util.List;
import Services.SupleService;

public class SuplementoController {
    
    private SupleService service;
    
    public SuplementoController(){
       this.service = new SupleService();
    }
    
    public boolean Guardar(int idSu, String nombre, String marca, double precio, int stock, String descripcion, boolean estado){
        Suplemento suplemento = new Suplemento(idSu,  nombre,  marca,  precio,  stock,  descripcion,  estado);
        return this.service.Guardar(suplemento);
    }
    public boolean Actualizar(Suplemento suplemento){
        return this.Actualizar(suplemento);
    }
    
    public boolean Eliminar(int idSu){
        return this.Eliminar(idSu);
    }
    
    public List<Suplemento> Listar(){
        return service.Listar();
    }
}
