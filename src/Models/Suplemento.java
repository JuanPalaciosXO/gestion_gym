package Models;

public class Suplemento {
    private int idSu;
    private String nombre;
    private String marca;
    private double precio;
    private int stock;
    private String descripcion;
    private boolean estado;
    
    public Suplemento (){}
    
    public Suplemento (int idSu, String nombre, String marca, double precio, int stock, String descripcion, boolean estado){
        this.idSu = idSu;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
        this.estado = estado;
    }
    
    public int getIdSu(){
        return idSu;
    }
    public void setIdSu(int idSu){
        this.idSu = idSu;
    }
    
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getMarca(){
        return marca;
    }
    public void setMarca(String marca){
        this.marca = marca;
    }
    
    public double getPrecio (){
        return precio;
    }
    public void setPrecio(double precio){
        this.precio = precio;
    }
    
    public int getStock(){
        return stock;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
    public boolean getEstado(){
        return estado;
    }
    public void setEstado(boolean estado){
        this.estado = estado;
    }
}
