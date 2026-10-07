
package Controllers;

import Models.Membresia;
import java.util.List;
import Services.MembresiaService;

public class MembController {
    private MembresiaService service;
    
    public MembController(){
        this.service = new MembresiaService();
    }
    
    public boolean Guardar(int idMemb, String nombre, double precio, int duracionMes, String descripcion, boolean estado){
        Membresia membresia = new Membresia(idMemb, nombre, precio, duracionMes, descripcion, estado);
        return this.service.Guardar(membresia);
    }
    
    public boolean Actualizar(Membresia membresia) {
        return this.service.Actualizar(membresia);
    }
    
    public boolean Eliminar(int idMemb){
        return this.service.Eliminar(idMemb);
    }
    
    public Membresia consultarPorId(int id) {
    return this.service.ConsultarPorId(id);
    }
    
    public List<Membresia> Listar(){
        return service.Listar();
    }
}
