package Repositories;

import Models.Ejercicio;
import java.util.ArrayList;

public class EjercicioRepository {

    private ArrayList<Ejercicio> ejercicios = new ArrayList<>();

    public boolean Guardar(Ejercicio ejercicio) {
        ejercicios.add(ejercicio);
        System.out.println("Ejercicio guardado en el repositorio");
        return true;
    }

    public boolean Actualizar(Ejercicio ejercicio) {
        for (int i = 0; i < ejercicios.size(); i++) {
            if (ejercicios.get(i).getId() == ejercicio.getId()) {
                ejercicios.set(i, ejercicio);
                return true;
            }
        }

        return false;
    }

    public boolean Eliminar(int idEjercicio) {
        for (int i = 0; i < ejercicios.size(); i++) {
            if (ejercicios.get(i).getId() == idEjercicio) {
                ejercicios.remove(i);
                return true;
            }
        }

        return false;
    }

    public Ejercicio ConsultarPorId(int idEjercicio) {
        for (int i = 0; i < ejercicios.size(); i++) {
            if (ejercicios.get(i).getId() == idEjercicio) {
                return ejercicios.get(i);
            }
        }

        return null;
    }
}