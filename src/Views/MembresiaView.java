
package Views;
import Controllers.MembController;
import Models.Membresia;
import javax.swing.JOptionPane;

public class MembresiaView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MembresiaView.class.getName());

    private final MembController membController = new MembController();
    private final Membresia membresiaEditar;    
    private final Runnable alGuardar;
    
    private static final java.util.Map<String, Integer> TIPOS = new java.util.LinkedHashMap<>();
    static {
        TIPOS.put("Mensual", 1);
        TIPOS.put("Trimestral", 3);
        TIPOS.put("Semestral", 6);
        TIPOS.put("Anual", 12);
    }

    public MembresiaView() {
        this(null, null);
    }

    public MembresiaView(Membresia membresia, Runnable alGuardar) {
        this.membresiaEditar = membresia;
        this.alGuardar = alGuardar;
        initComponents();
        setSize(741, 439);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cargarTipos();

        if (membresia == null) {
            setTitle("Registrar membresía");
            jLabel1.setText("Registrar Membresía");
            chkEstado.setSelected(true);
            chkEstado.setVisible(false);
            if (cmbNombre.getItemCount() == 0) {
                btnGuardar.setEnabled(false);
                JOptionPane.showMessageDialog(this,
                        "Ya existen todos los tipos de membresía. Usa Editar para modificar alguno.");
            }
        } else {
            setTitle("Editar membresía");
            jLabel1.setText("Editar Membresía");
            cmbNombre.setSelectedItem(membresia.getNombre());
            txtPrecio.setText(String.valueOf(membresia.precio()));
            txtDescripcion.setText(membresia.getDescripcion() == null ? "" : membresia.getDescripcion());
            chkEstado.setSelected(membresia.getEstado());
        }
    }
    
    private void cargarTipos() {
    cmbNombre.removeAllItems();

    for (String tipo : TIPOS.keySet()) {
        cmbNombre.addItem(tipo);
    }

    if (membresiaEditar != null) {
        cmbNombre.setSelectedItem(membresiaEditar.getNombre());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblDescripcion = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        chkEstado = new javax.swing.JCheckBox();
        btnVolver = new javax.swing.JButton();
        cmbNombre = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel1.setText("Registrar Membresia");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombre.setText("Nombre");

        lblPrecio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPrecio.setText("Precio");

        lblDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDescripcion.setText("Descripción");

        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btnGuardar.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        chkEstado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkEstado.setSelected(true);
        chkEstado.setText("Activo");
        chkEstado.addActionListener(this::chkEstadoActionPerformed);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        cmbNombre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbNombre.addActionListener(this::cmbNombreActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNombre)
                            .addComponent(lblPrecio)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(cmbNombre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblDescripcion)
                                .addGap(154, 154, 154))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chkEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(btnVolver)
                        .addGap(138, 138, 138)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(270, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPrecio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
 
        try {
            String nombre = (String) cmbNombre.getSelectedItem();
            if (nombre == null) {
                JOptionPane.showMessageDialog(this, "Selecciona el tipo de membresía.");
                return;
            }
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            String descripcion = txtDescripcion.getText().trim();
            boolean estado = chkEstado.isSelected();
 
            boolean ok;
            if (membresiaEditar == null) {
                ok = membController.Guardar(0, nombre, precio, descripcion, estado);
            } else {
                ok = membController.Actualizar(new Membresia(
                        membresiaEditar.getIdMemb(), nombre, precio, descripcion, estado));
            }
 
            if (ok) {
                JOptionPane.showMessageDialog(this, membresiaEditar == null
                        ? "Membresía registrada correctamente."
                        : "Membresía actualizada correctamente.");
                if (alGuardar != null) {
                    alGuardar.run();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo guardar. Revisa que ya no exista una membresía de ese tipo y que el precio sea mayor que 0.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El precio debe ser un número.",
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        }
                      
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void cmbNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNombreActionPerformed

    }//GEN-LAST:event_cmbNombreActionPerformed

    private void chkEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkEstadoActionPerformed

    public static void main(String args[]) {
      
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

        java.awt.EventQueue.invokeLater(() -> new MembresiaView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JComboBox<String> cmbNombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
