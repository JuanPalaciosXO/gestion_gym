package Models;

public class PagoSuplemento {
    private int idPago;
    private int idSu;
    private int cantidad;
    private double precioUnitario;
    private String nombre;

    public PagoSuplemento() {}

    public PagoSuplemento(int idPago, int idSu, int cantidad, double precioUnitario, String nombre) {
        this.idPago = idPago;
        this.idSu = idSu;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.nombre = nombre;
    }

    public int getIdPago(){ 
        return idPago; 
    }
    
    public void setIdPago(int idPago){
        this.idPago = idPago; 
    }

    public int getIdSu(){ 
        return idSu; 
    }
    
    public void setIdSu(int idSu){ 
        this.idSu = idSu; 
    }

    public int getCantidad(){ 
        return cantidad; 
    }
    
    public void setCantidad(int cantidad){ 
        this.cantidad = cantidad; 
    }

    public double getPrecioUnitario(){ 
        return precioUnitario; 
    }
    
    public void setPrecioUnitario(double precioUnitario){ 
        this.precioUnitario = precioUnitario; 
    }

    public String getNombre(){ 
        return nombre; 
    }
   
    public void setNombre(String nombre){ 
        this.nombre = nombre; 
    }

    public double getSubtotal(){ 
        return cantidad * precioUnitario; 
    }
}
