package Views;

import Controllers.EjercicioController;
import Models.Ejercicio;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EjercicioView extends javax.swing.JFrame {

    private final EjercicioController ejercicioController = new EjercicioController();
    private int idEjercicioSeleccionado = 0;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EjercicioView.class.getName());

    public EjercicioView() {
        initComponents();
        setLocationRelativeTo(null);
        cargarEjercicios();
    }

    private void cargarEjercicios() {
        DefaultTableModel modelo = (DefaultTableModel) tblEjercicios.getModel();
        modelo.setRowCount(0);

        try {
            for (Ejercicio ejercicio : ejercicioController.listar()) {
                modelo.addRow(new Object[]{
                    ejercicio.getId(),
                    ejercicio.getNombre(),
                    ejercicio.getTipoMuscular(),
                    ejercicio.getSeries(),
                    ejercicio.getRepeticiones(),
                    ejercicio.getDuracion(),
                    ejercicio.isEstado() ? "Activo" : "Inactivo"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar ejercicios: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        txtTipo = new javax.swing.JTextField();
        lblSeries = new javax.swing.JLabel();
        txtSeries = new javax.swing.JTextField();
        lblRepeticiones = new javax.swing.JLabel();
        txtRepeticiones = new javax.swing.JTextField();
        lblDuracion = new javax.swing.JLabel();
        txtDuracion = new javax.swing.JTextField();
        chkEstado = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEjercicios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setText("Gestión de Ejercicios");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblNombre.setText("Nombre");

        lblTipo.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblTipo.setText("Tipo muscular");

        lblSeries.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblSeries.setText("Series");

        lblRepeticiones.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblRepeticiones.setText("Repeticiones");

        lblDuracion.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblDuracion.setText("Duración (min)");

        chkEstado.setFont(new java.awt.Font("Segoe UI", 1, 12));
        chkEstado.setSelected(true);
        chkEstado.setText("Activo");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        tblEjercicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Tipo muscular", "Series", "Repeticiones", "Duración", "Estado"
            }
        ));
        tblEjercicios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEjerciciosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEjercicios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(18, 18, 18)
                .addComponent(btnActualizar)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar)
                .addGap(18, 18, 18)
                .addComponent(btnLimpiar)
                .addGap(198, 198, 198))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNombre)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                    .addComponent(txtSeries)
                                    .addComponent(txtDuracion))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblDuracion)
                                    .addComponent(lblRepeticiones)
                                    .addComponent(lblTipo)
                                    .addComponent(txtRepeticiones, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                                    .addComponent(txtTipo)))
                            .addComponent(lblSeries)
                            .addComponent(chkEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(lblTitulo)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(lblTipo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSeries)
                    .addComponent(lblRepeticiones))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSeries, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRepeticiones, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDuracion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chkEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del ejercicio es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocusInWindow();
            return;
        }

        try {
            String nombre = txtNombre.getText().trim();
            String tipo = txtTipo.getText().trim();
            int series = Integer.parseInt(txtSeries.getText().trim());
            int repeticiones = Integer.parseInt(txtRepeticiones.getText().trim());
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            boolean estado = chkEstado.isSelected();

            Ejercicio nuevoEjercicio = new Ejercicio(0, nombre, tipo, series, repeticiones, duracion, estado);
            boolean guardado = ejercicioController.guardar(nuevoEjercicio);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Ejercicio registrado correctamente.");
                txtNombre.setText("");
                txtTipo.setText("");
                txtSeries.setText("");
                txtRepeticiones.setText("");
                txtDuracion.setText("");
                chkEstado.setSelected(true);
                cargarEjercicios();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el ejercicio.", "Validación", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Series, repeticiones y duración deben ser números enteros válidos.", "Error numérico", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tblEjerciciosMouseClicked(java.awt.event.MouseEvent evt) {
        int fila = tblEjercicios.getSelectedRow();

        if (fila >= 0) {
            int filaModelo = tblEjercicios.convertRowIndexToModel(fila);

            idEjercicioSeleccionado = Integer.parseInt(
                    tblEjercicios.getModel().getValueAt(filaModelo, 0).toString()
            );

            txtNombre.setText(
                    tblEjercicios.getModel().getValueAt(filaModelo, 1).toString()
            );

            txtTipo.setText(
                    tblEjercicios.getModel().getValueAt(filaModelo, 2).toString()
            );

            txtSeries.setText(
                    tblEjercicios.getModel().getValueAt(filaModelo, 3).toString()
            );

            txtRepeticiones.setText(
                    tblEjercicios.getModel().getValueAt(filaModelo, 4).toString()
            );

            txtDuracion.setText(
                    tblEjercicios.getModel().getValueAt(filaModelo, 5).toString()
            );

            chkEstado.setSelected(
                    "Activo".equals(
                            tblEjercicios.getModel().getValueAt(filaModelo, 6).toString()
                    )
            );
        }
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idEjercicioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Primero debes seleccionar un ejercicio de la tabla.");
            return;
        }

        try {
            String nombre = txtNombre.getText().trim();
            String tipo = txtTipo.getText().trim();
            int series = Integer.parseInt(txtSeries.getText().trim());
            int repeticiones = Integer.parseInt(txtRepeticiones.getText().trim());
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            boolean estado = chkEstado.isSelected();

            Ejercicio ejercicioActualizado = new Ejercicio(idEjercicioSeleccionado, nombre, tipo, series, repeticiones, duracion, estado);
            boolean actualizado = ejercicioController.actualizar(ejercicioActualizado);

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Ejercicio actualizado correctamente.");
                idEjercicioSeleccionado = 0;
                txtNombre.setText("");
                txtTipo.setText("");
                txtSeries.setText("");
                txtRepeticiones.setText("");
                txtDuracion.setText("");
                chkEstado.setSelected(true);
                cargarEjercicios();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el ejercicio.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Series, repeticiones y duración deben ser numéricos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idEjercicioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Primero debes seleccionar un ejercicio de la tabla.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar este ejercicio?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean eliminado = ejercicioController.eliminar(idEjercicioSeleccionado);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Ejercicio eliminado correctamente.");
                idEjercicioSeleccionado = 0;
                txtNombre.setText("");
                txtTipo.setText("");
                txtSeries.setText("");
                txtRepeticiones.setText("");
                txtDuracion.setText("");
                chkEstado.setSelected(true);
                cargarEjercicios();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el ejercicio.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        txtNombre.setText("");
        txtTipo.setText("");
        txtSeries.setText("");
        txtRepeticiones.setText("");
        txtDuracion.setText("");
        chkEstado.setSelected(true);
        idEjercicioSeleccionado = 0;
        tblEjercicios.clearSelection();
        txtNombre.requestFocusInWindow();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new EjercicioView().setVisible(true));
    }

    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDuracion;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRepeticiones;
    private javax.swing.JLabel lblSeries;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblEjercicios;
    private javax.swing.JTextField txtDuracion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRepeticiones;
    private javax.swing.JTextField txtSeries;
    private javax.swing.JTextField txtTipo;
}