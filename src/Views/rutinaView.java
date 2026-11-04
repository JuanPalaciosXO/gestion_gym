package Views;

import Controllers.RutinaController;
import Models.Rutina;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class rutinaView extends javax.swing.JFrame {

    private final RutinaController rutinaController = new RutinaController();
    private int idRutinaSeleccionada = 0;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(rutinaView.class.getName());

    public rutinaView() {
        initComponents();
        setLocationRelativeTo(null);
        cargarRutinas();
    }

    private void cargarRutinas() {
        DefaultTableModel modelo = (DefaultTableModel) tblRutinas.getModel();
        modelo.setRowCount(0);

        try {
            for (Rutina rutina : rutinaController.Listar()) {
                modelo.addRow(new Object[]{
                    rutina.getId(),
                    rutina.getNombre(),
                    rutina.getObjetivo(),
                    rutina.getNivel(),
                    rutina.getDescripcion(),
                    rutina.isEstado() ? "Activo" : "Inactivo"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar rutinas: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblObjetivo = new javax.swing.JLabel();
        txtObjetivo = new javax.swing.JTextField();
        lblNivel = new javax.swing.JLabel();
        txtNivel = new javax.swing.JTextField();
        lblDescripcion = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        chkEstado = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRutinas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setText("Gestión de Rutinas");

        lblId.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblId.setText("ID");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblNombre.setText("Nombre");

        lblObjetivo.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblObjetivo.setText("Objetivo");

        lblNivel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblNivel.setText("Nivel");

        lblDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblDescripcion.setText("Descripción");

        chkEstado.setFont(new java.awt.Font("Segoe UI", 1, 12));
        chkEstado.setSelected(true);
        chkEstado.setText("Activo");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        tblRutinas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Objetivo", "Nivel", "Descripción", "Estado"
            }
        ));
        tblRutinas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRutinasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRutinas);

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
                .addComponent(btnBuscar)
                .addGap(18, 18, 18)
                .addComponent(btnLimpiar)
                .addGap(145, 145, 145))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblId)
                                    .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                    .addComponent(txtObjetivo)
                                    .addComponent(txtDescripcion))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblDescripcion)
                                    .addComponent(lblNivel)
                                    .addComponent(lblNombre)
                                    .addComponent(txtNivel, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                                    .addComponent(txtNombre)))
                            .addComponent(lblObjetivo)
                            .addComponent(lblNivel)
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
                    .addComponent(lblId)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblObjetivo)
                    .addComponent(lblNivel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtObjetivo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescripcion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chkEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la rutina es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocusInWindow();
            return;
        }

        try {
            int id = txtId.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String objetivo = txtObjetivo.getText().trim();
            String nivel = txtNivel.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            boolean estado = chkEstado.isSelected();

            Rutina nuevaRutina = new Rutina(id, nombre, objetivo, nivel, descripcion, estado);
            boolean guardado = rutinaController.Guardar(nuevaRutina);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Rutina registrada correctamente.");
                txtId.setText("");
                txtNombre.setText("");
                txtObjetivo.setText("");
                txtNivel.setText("");
                txtDescripcion.setText("");
                chkEstado.setSelected(true);
                cargarRutinas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar la rutina.", "Validación", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.", "Error numérico", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tblRutinasMouseClicked(java.awt.event.MouseEvent evt) {
        int fila = tblRutinas.getSelectedRow();

        if (fila >= 0) {
            int filaModelo = tblRutinas.convertRowIndexToModel(fila);

            idRutinaSeleccionada = Integer.parseInt(
                    tblRutinas.getModel().getValueAt(filaModelo, 0).toString()
            );

            txtId.setText(String.valueOf(idRutinaSeleccionada));
            txtNombre.setText(
                    tblRutinas.getModel().getValueAt(filaModelo, 1).toString()
            );
            txtObjetivo.setText(
                    tblRutinas.getModel().getValueAt(filaModelo, 2).toString()
            );
            txtNivel.setText(
                    tblRutinas.getModel().getValueAt(filaModelo, 3).toString()
            );
            txtDescripcion.setText(
                    tblRutinas.getModel().getValueAt(filaModelo, 4).toString()
            );
            chkEstado.setSelected(
                    "Activo".equals(
                            tblRutinas.getModel().getValueAt(filaModelo, 5).toString()
                    )
            );
        }
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idRutinaSeleccionada == 0 && txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debes seleccionar una rutina de la tabla o ingresar un ID.");
            return;
        }

        try {
            int id = idRutinaSeleccionada != 0 ? idRutinaSeleccionada : Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String objetivo = txtObjetivo.getText().trim();
            String nivel = txtNivel.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            boolean estado = chkEstado.isSelected();

            Rutina rutinaActualizada = new Rutina(id, nombre, objetivo, nivel, descripcion, estado);
            boolean actualizado = rutinaController.Actualizar(rutinaActualizada);

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Rutina actualizada correctamente.");
                idRutinaSeleccionada = 0;
                txtId.setText("");
                txtNombre.setText("");
                txtObjetivo.setText("");
                txtNivel.setText("");
                txtDescripcion.setText("");
                chkEstado.setSelected(true);
                cargarRutinas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar la rutina.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idRutinaSeleccionada == 0 && txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debes seleccionar una rutina de la tabla o ingresar un ID.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar esta rutina?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int id = idRutinaSeleccionada != 0 ? idRutinaSeleccionada : Integer.parseInt(txtId.getText().trim());
            boolean eliminado = rutinaController.Eliminar(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Rutina eliminada correctamente.");
                idRutinaSeleccionada = 0;
                txtId.setText("");
                txtNombre.setText("");
                txtObjetivo.setText("");
                txtNivel.setText("");
                txtDescripcion.setText("");
                chkEstado.setSelected(true);
                cargarRutinas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la rutina.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID para buscar.");
                txtId.requestFocusInWindow();
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());
            Rutina rutina = rutinaController.ConsultarPorId(id);

            if (rutina != null) {
                idRutinaSeleccionada = rutina.getId();
                txtNombre.setText(rutina.getNombre());
                txtObjetivo.setText(rutina.getObjetivo());
                txtNivel.setText(rutina.getNivel());
                txtDescripcion.setText(rutina.getDescripcion());
                chkEstado.setSelected(rutina.isEstado());

                JOptionPane.showMessageDialog(this, "¡Rutina encontrada!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ninguna rutina con el ID: " + id);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.");
        }
    }

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        txtId.setText("");
        txtNombre.setText("");
        txtObjetivo.setText("");
        txtNivel.setText("");
        txtDescripcion.setText("");
        chkEstado.setSelected(true);
        idRutinaSeleccionada = 0;
        tblRutinas.clearSelection();
        txtId.requestFocusInWindow();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new rutinaView().setVisible(true));
    }

    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObjetivo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblRutinas;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNivel;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtObjetivo;
}