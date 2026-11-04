package Views;

import Controllers.EntrenadorController;
import Models.Entrenador;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EntrenadorView extends javax.swing.JFrame {

    private final EntrenadorController entrenadorController = new EntrenadorController();
    private int idEntrenadorSeleccionado = 0;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EntrenadorView.class.getName());

    public EntrenadorView() {
        initComponents();
        setLocationRelativeTo(null);
        cargarEntrenadores();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTelefono = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        txtTelefono = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEntrenadores = new javax.swing.JTable();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblEspecialidad = new javax.swing.JLabel();
        txtEspecialidad = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        chkEstado = new javax.swing.JCheckBox();
        lblDocumento = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        txtDocumento = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTelefono.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTelefono.setText("Teléfono");

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        tblEntrenadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Documento", "Teléfono", "Correo", "Especialidad", "Estado"
            }
        ));
        tblEntrenadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEntrenadoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEntrenadores);

        lblCorreo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCorreo.setText("Correo");

        lblEspecialidad.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEspecialidad.setText("Especialidad");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Gestión de Entrenadores");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombre.setText("Nombre");

        txtNombre.addActionListener(this::txtNombreActionPerformed);

        chkEstado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkEstado.setSelected(true);
        chkEstado.setText("Activo");

        lblDocumento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDocumento.setText("Documento");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNombre)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                    .addComponent(txtTelefono)
                                    .addComponent(txtEspecialidad))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblCorreo)
                                    .addComponent(lblDocumento)
                                    .addComponent(txtDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                                    .addComponent(txtCorreo)))
                            .addComponent(lblTelefono)
                            .addComponent(lblEspecialidad)
                            .addComponent(chkEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(lblTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)
                        .addGap(18, 18, 18)
                        .addComponent(btnVolver)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(lblDocumento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDocumento))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCorreo)
                    .addComponent(lblTelefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTelefono)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblEspecialidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chkEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (idEntrenadorSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un entrenador de la tabla.");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de eliminar este entrenador?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (entrenadorController.Eliminar(idEntrenadorSeleccionado)) {
                JOptionPane.showMessageDialog(this, "Entrenador eliminado correctamente.");
                limpiarFormulario();
                cargarEntrenadores();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el entrenador.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblEntrenadoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEntrenadoresMouseClicked
        int fila = tblEntrenadores.getSelectedRow();
        if (fila < 0) {
            return;
        }
        int f = tblEntrenadores.convertRowIndexToModel(fila);
        DefaultTableModel modelo = (DefaultTableModel) tblEntrenadores.getModel();
        idEntrenadorSeleccionado = Integer.parseInt(valorCelda(modelo, f, 0));
        txtNombre.setText(valorCelda(modelo, f, 1));
        txtDocumento.setText(valorCelda(modelo, f, 2));
        txtTelefono.setText(valorCelda(modelo, f, 3));
        txtCorreo.setText(valorCelda(modelo, f, 4));
        txtEspecialidad.setText(valorCelda(modelo, f, 5));
        chkEstado.setSelected("Activo".equals(valorCelda(modelo, f, 6)));
    }//GEN-LAST:event_tblEntrenadoresMouseClicked

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed

    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (!validarFormulario()) {
            return;
        }
        try {
            boolean guardado = entrenadorController.Guardar(0,
                    txtNombre.getText().trim(), txtDocumento.getText().trim(),
                    txtTelefono.getText().trim(), txtCorreo.getText().trim(),
                    txtEspecialidad.getText().trim(), chkEstado.isSelected());
            if (guardado) {
                JOptionPane.showMessageDialog(this, "Entrenador registrado correctamente.");
                limpiarFormulario();
                cargarEntrenadores();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar. Verifica los datos y duplicados.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (idEntrenadorSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un entrenador de la tabla.");
            return;
        }
        if (!validarFormulario()) {
            return;
        }
        try {
            boolean actualizado = entrenadorController.Actualizar(idEntrenadorSeleccionado,
                    txtNombre.getText().trim(), txtDocumento.getText().trim(),
                    txtTelefono.getText().trim(), txtCorreo.getText().trim(),
                    txtEspecialidad.getText().trim(), chkEstado.isSelected());
            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Entrenador actualizado correctamente.");
                limpiarFormulario();
                cargarEntrenadores();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar. Verifica los datos y duplicados.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void cargarEntrenadores() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblEntrenadores.getModel();
            modelo.setRowCount(0);
            for (Entrenador e : entrenadorController.Listar()) {
                modelo.addRow(new Object[]{
                    e.getIdEntrenador(), e.getNombre(), e.getDocumento(),
                    e.getTelefono(), e.getCorreo(), e.getEspecialidad(),
                    e.isEstado() ? "Activo" : "Inactivo"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar entrenadores: " + ex.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtEspecialidad.setText("");
        chkEstado.setSelected(true);
        idEntrenadorSeleccionado = 0;
        tblEntrenadores.clearSelection();
        txtNombre.requestFocusInWindow();
    }

    private boolean validarFormulario() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
            txtNombre.requestFocusInWindow();
            return false;
        }
        if (txtDocumento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El documento es obligatorio.");
            txtDocumento.requestFocusInWindow();
            return false;
        }
        String correo = txtCorreo.getText().trim();
        if (!correo.isEmpty() && !correo.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this, "Ingresa un correo válido.");
            txtCorreo.requestFocusInWindow();
            return false;
        }
        return true;
    }

    private String valorCelda(DefaultTableModel modelo, int fila, int columna) {
        Object valor = modelo.getValueAt(fila, columna);
        return valor == null ? "" : valor.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new EntrenadorView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDocumento;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblEntrenadores;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtEspecialidad;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
