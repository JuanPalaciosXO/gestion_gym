
package Models;
import java.time.LocalDate;

public class Pago {
    private int idPago;
    private LocalDate fecha;
    private int valor;
    private String metodoPago;
    private boolean estado;
    private int idUsuario;
    private int idMembresia;
    
    public Pago(){}
    
    public Pago(int idPago, LocalDate fecha, int valor, String metodoPago, boolean estado, int idUsuario, int idMembresia){
        this.idPago = idPago;
        this.fecha = fecha;
        this.valor = valor;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.idMembresia = idMembresia;
    }
    
    public int getIdPago(){
        return this.idPago;
    }
    
    public void setIdPago(int idPago){
        this.idPago = idPago;
    }
    
    public LocalDate getFecha(){
        return this.fecha;
    }
    
    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }
    
    public int getValor(){
        return this.valor;
    }
    
    public void setValor(int valor){
        this.valor = valor;
    }

    public String getMetodoPago(){
        return this.metodoPago;
    }

    public void setMetodoPago(String metodoPago){
        this.metodoPago = metodoPago;
    }

    public boolean isEstado(){
        return this.estado;
    }

    public void setEstado(boolean estado){
        this.estado = estado;
    }

    public int getIdUsuario(){
        return this.idUsuario;
    }

    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }

    public int getIdMembresia(){
        return this.idMembresia;
    }

    public void setIdMembresia(int idMembresia){
        this.idMembresia = idMembresia;
    } 
    
}
