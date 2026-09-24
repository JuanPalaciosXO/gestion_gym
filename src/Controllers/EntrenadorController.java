
package Controllers;

import Models.Entrenador;
import Services.EntrenadorService;

public class EntrenadorController {
    
    private EntrenadorService entrenadorService;
    
    public EntrenadorController(){
        entrenadorService = new EntrenadorService();
    }
    
    public void Guardar(int idEntrenador, String nombre, String documento, String telefono, String correo, String especialidad, boolean estado){
        Entrenador entrenador = new Entrenador(
            idEntrenador,
            nombre,
            documento,
            telefono,
            correo,
            especialidad,
            estado
        );
        entrenadorService.Guardar(entrenador);
    }
    
    public void Actualizar(int idEntrenador, String nombre, String documento, String telefono, String correo, String especialidad, boolean estado){
        Entrenador entrenador = new Entrenador(
            idEntrenador,
            nombre,
            documento,
            telefono,
            correo,
            especialidad,
            estado
        );
        entrenadorService.Actualizar(entrenador);
    }
    
    public void Eliminar(int idEntrenador){
        entrenadorService.Eliminar(idEntrenador);
    }
    
    public void Listar(){
        entrenadorService.Listar();
    }
}
