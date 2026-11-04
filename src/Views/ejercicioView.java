package Views;

import Controllers.EjercicioController;
import Models.Ejercicio;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ejercicioView extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ejercicioView.class.getName());

    // Declaración de controladores y componentes
    private final EjercicioController controller = new EjercicioController();
    private final JTextField txtId = new JTextField(10);
    private final JTextField txtNombre = new JTextField(18);
    private final JTextField txtTipo = new JTextField(18);
    private final JTextField txtSeries = new JTextField(10);
    private final JTextField txtRepeticiones = new JTextField(10);
    private final JTextField txtDuracion = new JTextField(10);
    private final JCheckBox chkEstado = new JCheckBox("Activo", true);
    
    private final DefaultTableModel modelo = new DefaultTableModel(
        new Object[]{"ID", "Nombre", "Tipo muscular", "Series", "Repeticiones", "Duración (min)", "Estado"}, 0
    ) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    
    private final JTable tabla = new JTable(modelo);

    /**
     * Constructor principal de la vista
     */
    public ejercicioView() {
        setTitle("Gestión de ejercicios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 570);
        setLocationRelativeTo(null);
        
        // Layout principal de la ventana
        setLayout(new BorderLayout(10, 10));

        // 1. Panel del formulario (NORTE)
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del ejercicio"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        campo(formulario, c, 0, "ID:", txtId);
        campo(formulario, c, 1, "Nombre:", txtNombre);
        campo(formulario, c, 2, "Tipo muscular:", txtTipo);
        campo(formulario, c, 3, "Series:", txtSeries);
        campo(formulario, c, 4, "Repeticiones:", txtRepeticiones);
        campo(formulario, c, 5, "Duración (minutos):", txtDuracion);

        c.gridx = 2;
        c.gridy = 0;
        formulario.add(chkEstado, c);
        
        add(formulario, BorderLayout.NORTH);

        // 2. Tabla central con Scroll (CENTRO)
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // 3. Panel de botones (SUR)
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton guardar = new JButton("Guardar");
        JButton actualizar = new JButton("Actualizar");
        JButton eliminar = new JButton("Eliminar");
        JButton buscar = new JButton("Buscar"); 
        JButton limpiar = new JButton("Limpiar");

        botones.add(guardar);
        botones.add(actualizar);
        botones.add(eliminar);
        botones.add(buscar);   
        botones.add(limpiar);
        
        add(botones, BorderLayout.SOUTH);

        // Eventos de los botones
        guardar.addActionListener(e -> guardar());
        actualizar.addActionListener(e -> actualizar());
        eliminar.addActionListener(e -> eliminar());
        buscar.addActionListener(e -> buscarEjercicio()); 
        limpiar.addActionListener(e -> limpiar());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                cargarSeleccion();
            }
        });

        cargarTabla();
    }

    private void campo(JPanel p, GridBagConstraints c, int y, String l, JComponent x) {
        c.gridx = 0;
        c.gridy = y;
        p.add(new JLabel(l), c);
        c.gridx = 1;
        p.add(x, c);
    }

    private Ejercicio leer() {
        return new Ejercicio(
            Integer.parseInt(txtId.getText().trim()),
            txtNombre.getText().trim(),
            txtTipo.getText().trim(),
            Integer.parseInt(txtSeries.getText().trim()),
            Integer.parseInt(txtRepeticiones.getText().trim()),
            Integer.parseInt(txtDuracion.getText().trim()),
            chkEstado.isSelected()
        );
    }

    private void guardar() {
        try {
            if (controller.guardar(leer())) {
                mensaje("Ejercicio guardado.");
                limpiar();
                cargarTabla();
            } else {
                mensaje("No se pudo guardar. Revisa los datos o que el ID no esté repetido.");
            }
        } catch (Exception ex) {
            mensaje("ID, series, repeticiones y duración deben ser números enteros.");
        }
    }

    private void actualizar() {
        try {
            if (controller.actualizar(leer())) {
                mensaje("Ejercicio actualizado.");
                limpiar();
                cargarTabla();
            } else {
                mensaje("No existe un ejercicio con ese ID o los datos no son válidos.");
            }
        } catch (Exception ex) {
            mensaje("Verifica los datos numéricos.");
        }
    }

    private void eliminar() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar el ejercicio seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (controller.eliminar(id)) {
                    mensaje("Ejercicio eliminado.");
                    limpiar();
                    cargarTabla();
                } else {
                    mensaje("No se encontró el ejercicio.");
                }
            }
        } catch (Exception ex) {
            mensaje("Selecciona un ejercicio válido.");
        }
    }

    private void buscarEjercicio() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                mensaje("Por favor, ingresa un ID para buscar.");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());
            Ejercicio ejercicio = controller.consultarPorId(id);

            if (ejercicio != null) {
                // 1. Rellenar los campos de la interfaz visualmente
                txtNombre.setText(ejercicio.getNombre());
                txtTipo.setText(ejercicio.getTipoMuscular());
                txtSeries.setText(String.valueOf(ejercicio.getSeries()));
                txtRepeticiones.setText(String.valueOf(ejercicio.getRepeticiones()));
                txtDuracion.setText(String.valueOf(ejercicio.getDuracion()));
                chkEstado.setSelected(ejercicio.isEstado());
                
                // 2. Construir el mensaje con los detalles del ejercicio encontrado
                String detalle = "¡Ejercicio encontrado!\n\n" +
                                 "• Nombre: " + ejercicio.getNombre() + "\n" +
                                 "• Tipo Muscular: " + ejercicio.getTipoMuscular() + "\n" +
                                 "• Series: " + ejercicio.getSeries() + "\n" +
                                 "• Repeticiones: " + ejercicio.getRepeticiones() + "\n" +
                                 "• Duración: " + ejercicio.getDuracion() + " min\n" +
                                 "• Estado: " + (ejercicio.isEstado() ? "Activo" : "Inactivo");
                
                JOptionPane.showMessageDialog(this, detalle, "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            } else {
                mensaje("No se encontró ningún ejercicio con el ID: " + id);
                limpiar();
            }
            
        } catch (NumberFormatException ex) {
            mensaje("El ID debe ser un número entero válido.");
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Ejercicio e : controller.listar()) {
            modelo.addRow(new Object[]{
                e.getId(), 
                e.getNombre(), 
                e.getTipoMuscular(), 
                e.getSeries(), 
                e.getRepeticiones(), 
                e.getDuracion(), 
                e.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }

    private void cargarSeleccion() {
        int f = tabla.getSelectedRow();
        if (f >= 0) {
            txtId.setText(modelo.getValueAt(f, 0).toString());
            txtNombre.setText(modelo.getValueAt(f, 1).toString());
            txtTipo.setText(modelo.getValueAt(f, 2).toString());
            txtSeries.setText(modelo.getValueAt(f, 3).toString());
            txtRepeticiones.setText(modelo.getValueAt(f, 4).toString());
            txtDuracion.setText(modelo.getValueAt(f, 5).toString());
            chkEstado.setSelected("Activo".equals(modelo.getValueAt(f, 6)));
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtTipo.setText("");
        txtSeries.setText("");
        txtRepeticiones.setText("");
        txtDuracion.setText("");
        chkEstado.setSelected(true);
        tabla.clearSelection();
    }

    private void mensaje(String s) {
        JOptionPane.showMessageDialog(this, s);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(() -> new ejercicioView().setVisible(true));
    }
}