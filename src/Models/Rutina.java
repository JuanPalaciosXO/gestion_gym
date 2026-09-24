
package Models;

/**
 *
 * @author Estudiantes
 */
public class Rutina {
    
    private int id; 
    private String nombre; 
    private String objetivo;
    private String nivel; 
    private String descripcion; 
    private boolean estado; 
    
    public Rutina(){}
    
    public Rutina (int id, String nombre, String objetivo,String nivel, String descripcion, boolean estado){
        
        this.id = id; 
        this.nombre = nombre; 
        this.objetivo = objetivo; 
        this.nivel = nivel;
        this.descripcion = descripcion; 
        this.estado = estado; 
    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
}
