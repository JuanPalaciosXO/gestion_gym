package Controllers;
import Services.ReporteSupServices;
import java.util.List;

public class ReporteSupController {
    private ReporteSupServices service;

    public ReporteSupController() {
        this.service = new ReporteSupServices();
    }

    public double obtenerValorInventario() {
        return service.obtenerValorInventario();
    }

    public List<Object[]> obtenerStockBajo(int stockMinimo, int stockMeta) {
        return service.obtenerStockBajo(stockMinimo, stockMeta);
    }

    public List<Object[]> simularDescuento(double porcentaje) {
        return service.simularDescuento(porcentaje);
    }
}
