
package Services;

import Models.Ejercicio;
import Repositories.EjercicioRepository;
import java.util.ArrayList;

public class EjercicioService {

    private EjercicioRepository repository;

    public EjercicioService() {
        this.repository = new EjercicioRepository();
    }

    public boolean guardar(Ejercicio ejercicio) {

    
        if (repository.ConsultarPorId(ejercicio.getId()) != null) {
            System.out.println("Error: El ID " + ejercicio.getId()
                    + " ya está registrado. No se puede guardar.");
            return false;
        }

     
        boolean resultado = repository.Guardar(ejercicio);

        if (resultado) {
            System.out.println("Se guardó exitosamente el ejercicio.");
        } else {
            System.out.println("No se pudo guardar el ejercicio.");
        }

        return resultado;
    }

    public boolean actualizar(Ejercicio ejercicio) {

        boolean resultado = repository.Actualizar(ejercicio);

        if (resultado) {
            System.out.println("Se actualizó el ejercicio.");
        }

        return resultado;
    }

    public boolean eliminar(int idEjercicio) {

        boolean resultado = repository.Eliminar(idEjercicio);

        if (resultado) {
            System.out.println("Se eliminó el ejercicio.");
        }

        return resultado;
    }

    public Ejercicio consultarId(int idEjercicio) {

        Ejercicio ejercicio = repository.ConsultarPorId(idEjercicio);

        if (ejercicio != null) {
            System.out.println("Se consultó exitosamente el ejercicio con ID: " + idEjercicio);
        } else {
            System.out.println("No se encontró ningún ejercicio con el ID: " + idEjercicio);
        }

        return ejercicio;
    }

    public ArrayList<Ejercicio> listar() {
        return repository.Listar();
    }
}
