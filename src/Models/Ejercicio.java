
package Models;


 public class Ejercicio {
    
     private int id ;
     private String nombre; 
     private String tipoMuscular; 
     private int series; 
     private int repeticiones; 
     private int duracion; 
     private boolean estado; 
     
     public Ejercicio(){}
     
     public Ejercicio (int id, String nombre, String tipoMuscular, int series, int repeticiones, int duracion, boolean estado){
     
      this.id = id ; 
      this.nombre = nombre; 
      this.tipoMuscular = tipoMuscular; 
      this.series = series; 
      this.repeticiones = repeticiones;
      this.duracion = duracion; 
      this.estado = estado ; 
  
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

    public String getTipoMuscular() {
        return tipoMuscular;
    }

    public void setTipoMuscular(String tipoMuscular) {
        this.tipoMuscular = tipoMuscular;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
     
     
}
