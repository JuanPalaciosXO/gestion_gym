package Services;

import Models.Maquina;
import Repositories.ReporteMaquinaRepository;
import java.util.ArrayList;
import java.util.List;

public class ReporteMaquinaService {
    
    private final ReporteMaquinaRepository repo = new ReporteMaquinaRepository();

    public int obtenerConteoMaquinas(int estado) {
        return repo.obtenerConteoMaquinas(estado);
    }

    public int contarTotalMaquinas() {
        return repo.obtenerConteoMaquinas(1) + repo.obtenerConteoMaquinas(0);
    }

    public boolean activarMaquina(int idMn) {
        return repo.cambiarEstadoMaquina(idMn, true);
    }

    public double calcularPorcentajeUtilizacion() {
        int activas = repo.obtenerConteoMaquinas(1);
        if (activas == 0) return 0;
        return repo.contarConEjercicios() * 100.0 / activas;
    }

    public List<Maquina> listarMaquinasSinUso() {
        List<Object[]> resultados = repo.obtenerMaquinasSinUso();
        List<Maquina> listaMaquinas = new ArrayList<>();
        
        for (Object[] fila : resultados) {
            Maquina m = new Maquina();
            m.setIdMn((int) fila[0]);
            m.setNombre((String) fila[1]);
            m.setTipo((String) fila[2]);
            m.setUbicacion((String) fila[3]);
            m.setEstado(true);
            listaMaquinas.add(m);
        }
        return listaMaquinas;
    }

    public List<Object[]> frecuenciaUso() {
        return repo.frecuenciaUso(0, "DESC");
    }
    
    public List<Object[]> masUtilizadas() {
        List<Object[]> ranking = new ArrayList<>();
        int pos = 1;
        for (Object[] f : repo.frecuenciaUso(5, "DESC")) {
            int veces = ((Number) f[3]).intValue();
            String clasificacion = veces >= 5 ? "Alta" : (veces >= 2 ? "Media" : "Baja");
            ranking.add(new Object[]{pos++, f[0], f[1], veces, clasificacion});
        }
        return ranking;
    }

    public String obtenerMaquinaMasUsada() {
        List<Object[]> lista = repo.frecuenciaUso(1, "DESC");
        if (!lista.isEmpty()) {
            return (String) lista.get(0)[0];
        }
        return null;
    }

    public String obtenerMaquinaMenosUsada() {
        List<Object[]> lista = repo.frecuenciaUso(1, "ASC");
        if (!lista.isEmpty()) {
            return (String) lista.get(0)[0];
        }
        return null;
    }
    
    public List<Object[]> listarMaquinasSinEjercicios() {
        return repo.sinEjercicios();
    }

    public List<Object[]> obtenerHistorialActividad() {
        return repo.obtenerHistorialActividad();
    }

}
