
package Models;

public class Membresia {
    private int idMemb;
    private String nombre;
    private double precio;
    private String descripcion;
    private boolean estado;
    
    public Membresia(){}
    
    public Membresia(int idMemb, String nombre, double precio, String descripcion, boolean estado){
        this.idMemb = idMemb;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
    }
    
    public int getIdMemb(){
        return this.idMemb;
    }
    
    public void setIdMemb(int idMemb){
        this.idMemb = idMemb;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public double precio(){
        return this.precio;
    }
    
    public void setPrecio(double precio){
        this.precio = precio;
    }
    
    public String getDescripcion(){
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
    public boolean getEstado(){
        return this.estado;
    }
    
    public void setEstado(boolean estado){
        this.estado = estado;
    }
    
}
