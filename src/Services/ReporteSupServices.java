package Services;
import Repositories.ReporteSupRepository;
import java.util.List;

public class ReporteSupServices {
    private ReporteSupRepository repository;

    public ReporteSupServices() {
        this.repository = new ReporteSupRepository();
    }

    public double obtenerValorInventario() {
        return repository.calcularValorInventario();
    }

    public List<Object[]> obtenerStockBajo(int stockMinimo, int stockMeta) {
        return repository.obtenerReporteStockBajo(stockMinimo, stockMeta);
    }

    public List<Object[]> simularDescuento(double porcentaje) {
        return repository.simularPreciosDescuento(porcentaje);
    }
}
