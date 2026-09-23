
package Models;


public class Maquina {
    private int idMn;
    private String nombre;
    private String tipo;
    private boolean estado;
    private String ubicacion;
    private String descripcion;
    
    public Maquina (){}
    
    public Maquina(int idMn, String nombre, String tipo, boolean estado, String ubicacion, String descripcion){
        this.idMn = idMn;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
    }
    
    public int getIdMn(){
        return idMn;
    }
    public void setIdMn(int idMn){
        this.idMn = idMn;
    }
            
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
                    
    public String getTipo(){
        return tipo;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
                    
    public boolean getEstado(){
        return estado;
    }
    public void setEstado(boolean estado){
        this.estado = estado;
    }
                    
    public String getUbicacion(){
        return ubicacion;
    }
    public void setUbicacion(String ubicacion){
        this.ubicacion = ubicacion;
    }
                    
    public String getDescripcion(){
        return descripcion;
    }
    public void setDescripcion (String descripcion){
        this.descripcion = descripcion;
    }
    
}
