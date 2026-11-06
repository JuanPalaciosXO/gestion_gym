package Controllers;

import Models.Maquina;
import java.util.List;
import Services.MaquinaService;


public class MaquinaController {
;
    private MaquinaService service;

    public MaquinaController() {
        this.service = new MaquinaService();
    }

    public boolean Guardar(int idMn, String nombre, String tipo, boolean estado, String ubicacion, String descripcion) {
        Maquina maquina = new Maquina(idMn, nombre, tipo, estado, ubicacion, descripcion);
        return this.service.Guardar(maquina);
    }

    public boolean Actualizar(Maquina maquina) {
        return this.service.Actualizar(maquina);
    }


    public Maquina consultarPorId(int id) {
        return this.service.ConsultarPorId(id);
    }

    public List<Maquina> Listar() {
        return service.Listar();
    }
    
    public List<Maquina> buscar(String texto) {
        return service.buscar(texto);
    }

    public boolean desactivar(int idMn) {
        return service.desactivar(idMn);
    }

}
