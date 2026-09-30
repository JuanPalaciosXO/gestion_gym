package Repositories;

import Models.Entrenador;
import java.util.ArrayList;
import java.util.List;

public class EntrenadorRepository {

    private List<Entrenador> lstEntrenadores;

    public EntrenadorRepository() {
        this.lstEntrenadores = new ArrayList<>();
    }

    public boolean Insertar(Entrenador entrenador) {
        this.lstEntrenadores.add(entrenador);
        return true;
    }

    public List<Entrenador> Traer() {
        return this.lstEntrenadores;
    }

    public boolean Actualizar(Entrenador entrenador) {

        for (int i = 0; i < lstEntrenadores.size(); i++) {

            if (lstEntrenadores.get(i).getIdEntrenador()
                    == entrenador.getIdEntrenador()) {

                lstEntrenadores.set(i, entrenador);
                return true;
            }
        }

        return false;
    }

    public boolean Eliminar(int idEntrenador) {

        for (int i = 0; i < lstEntrenadores.size(); i++) {

            if (lstEntrenadores.get(i).getIdEntrenador() == idEntrenador) {
                lstEntrenadores.remove(i);
                return true;
            }
        }

        return false;
    }
}
