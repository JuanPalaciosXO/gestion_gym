package Views;

import Controllers.RutinaController;
import Models.Rutina;
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

public class rutinaView extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(rutinaView.class.getName());

    private final RutinaController controller = new RutinaController();
    private final JTextField txtId = new JTextField(10);
    private final JTextField txtNombre = new JTextField(18);
    private final JTextField txtObjetivo = new JTextField(18);
    private final JTextField txtNivel = new JTextField(15);
    private final JTextField txtDescripcion = new JTextField(18);
    private final JCheckBox chkEstado = new JCheckBox("Activo", true);
    
    private final DefaultTableModel modelo = new DefaultTableModel(
        new Object[]{"ID", "Nombre", "Objetivo", "Nivel", "Descripción", "Estado"}, 0
    ) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    
    private final JTable tabla = new JTable(modelo);


    public rutinaView() {
        setTitle("Gestión de rutinas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 570);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout(10, 10));

    
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Datos de la rutina"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        campo(formulario, c, 0, "ID:", txtId);
        campo(formulario, c, 1, "Nombre:", txtNombre);
        campo(formulario, c, 2, "Objetivo:", txtObjetivo);
        campo(formulario, c, 3, "Nivel:", txtNivel);
        campo(formulario, c, 4, "Descripción:", txtDescripcion);

        c.gridx = 2;
        c.gridy = 0;
        formulario.add(chkEstado, c);
        
        add(formulario, BorderLayout.NORTH);

  
        add(new JScrollPane(tabla), BorderLayout.CENTER);

  
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


        guardar.addActionListener(e -> guardar());
        actualizar.addActionListener(e -> actualizar());
        eliminar.addActionListener(e -> eliminar());
        buscar.addActionListener(e -> buscarRutina()); 
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

    private Rutina leer() {
        return new Rutina(
            Integer.parseInt(txtId.getText().trim()),
            txtNombre.getText().trim(),
            txtObjetivo.getText().trim(),
            txtNivel.getText().trim(),
            txtDescripcion.getText().trim(),
            chkEstado.isSelected()
        );
    }

    private void guardar() {
        try {
            if (controller.Guardar(leer())) {
                mensaje("Rutina guardada exitosamente.");
                limpiar();
                cargarTabla();
            } else {
                mensaje("No se pudo guardar. Revisa los datos o que el ID no esté repetido.");
            }
        } catch (Exception ex) {
            mensaje("El ID debe ser un número entero válido.");
        }
    }

    private void actualizar() {
        try {
            if (controller.Actualizar(leer())) {
                mensaje("Rutina actualizada correctamente.");
                limpiar();
                cargarTabla();
            } else {
                mensaje("No existe una rutina con ese ID o los datos no son válidos.");
            }
        } catch (Exception ex) {
            mensaje("Verifica los datos ingresados.");
        }
    }

    private void eliminar() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar la rutina seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (controller.Eliminar(id)) {
                    mensaje("Rutina eliminada.");
                    limpiar();
                    cargarTabla();
                } else {
                    mensaje("No se encontró la rutina.");
                }
            }
        } catch (Exception ex) {
            mensaje("Selecciona un ID válido.");
        }
    }

    private void buscarRutina() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                mensaje("Por favor, ingresa un ID para buscar.");
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());
            Rutina rutina = controller.ConsultarPorId(id);

            if (rutina != null) {
                // Rellenamos los campos de la interfaz
                txtNombre.setText(rutina.getNombre());
                txtObjetivo.setText(rutina.getObjetivo());
                txtNivel.setText(rutina.getNivel());
                txtDescripcion.setText(rutina.getDescripcion());
                chkEstado.setSelected(rutina.isEstado());
                
                // Mensaje detallado flotante con la información encontrada
                String detalle = "¡Rutina encontrada!\n\n" +
                                 "• Nombre: " + rutina.getNombre() + "\n" +
                                 "• Objetivo: " + rutina.getObjetivo() + "\n" +
                                 "• Nivel: " + rutina.getNivel() + "\n" +
                                 "• Descripción: " + rutina.getDescripcion() + "\n" +
                                 "• Estado: " + (rutina.isEstado() ? "Activo" : "Inactivo");
                
                JOptionPane.showMessageDialog(this, detalle, "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            } else {
                mensaje("No se encontró ninguna rutina con el ID: " + id);
                limpiar();
            }
            
        } catch (NumberFormatException ex) {
            mensaje("El ID debe ser un número entero válido.");
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Rutina r : controller.Listar()) {
            modelo.addRow(new Object[]{
                r.getId(), 
                r.getNombre(), 
                r.getObjetivo(), 
                r.getNivel(), 
                r.getDescripcion(), 
                r.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }

    private void cargarSeleccion() {
        int f = tabla.getSelectedRow();
        if (f >= 0) {
            txtId.setText(modelo.getValueAt(f, 0).toString());
            txtNombre.setText(modelo.getValueAt(f, 1).toString());
            txtObjetivo.setText(modelo.getValueAt(f, 2).toString());
            txtNivel.setText(modelo.getValueAt(f, 3).toString());
            txtDescripcion.setText(modelo.getValueAt(f, 4).toString());
            chkEstado.setSelected("Activo".equals(modelo.getValueAt(f, 5)));
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtObjetivo.setText("");
        txtNivel.setText("");
        txtDescripcion.setText("");
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

        SwingUtilities.invokeLater(() -> new rutinaView().setVisible(true));
    }
}