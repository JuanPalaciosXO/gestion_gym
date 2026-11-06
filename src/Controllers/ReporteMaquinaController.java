package Controllers;

import Models.Maquina;
import Services.ReporteMaquinaService;
import java.util.List;

public class ReporteMaquinaController {
    private final ReporteMaquinaService service = new ReporteMaquinaService();

    public int obtenerConteoMaquinas(int estado) {
        return service.obtenerConteoMaquinas(estado);
    }

    public double calcularPorcentajeUtilizacion() {
        return service.calcularPorcentajeUtilizacion();
    }

    public List<Maquina> obtenerMaquinasSinUso() {
        return service.listarMaquinasSinUso();
    }

    public int contarTotalMaquinas() {
        return service.contarTotalMaquinas();
    }

    public boolean activarMaquina(int idMn) {
        return service.activarMaquina(idMn);
    }

    public List<Object[]> frecuenciaUso() {
        return service.frecuenciaUso();
    }

    public List<Object[]> masUtilizadas() {
        return service.masUtilizadas();
    }

    public List<Maquina> sinEjercicios() {
        return service.listarMaquinasSinUso();
    }

    public String obtenerMaquinaMasUsada() {
        return service.obtenerMaquinaMasUsada();
    }

    public String obtenerMaquinaMenosUsada() {
        return service.obtenerMaquinaMenosUsada();
    }
    
    public List<Object[]> obtenerMaquinasSinEjercicios() {
        return service.listarMaquinasSinEjercicios();
    }
    
    public List<Object[]> obtenerHistorialActividad() {
        return service.obtenerHistorialActividad();
    }
    
}
