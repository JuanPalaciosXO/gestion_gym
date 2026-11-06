
package Views;
import Controllers.MembController;
import Models.Membresia;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MembListaView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MembListaView.class.getName());

    private final MembController membController = new MembController();

    public MembListaView() {
        initComponents();
        setSize(741, 439);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cargarMembresias();
    }

    private void cargarMembresias() {
        DefaultTableModel modelo = (DefaultTableModel) tblMembresias.getModel();
        modelo.setRowCount(0);
        for (Membresia m : membController.Listar()) {
            modelo.addRow(new Object[]{
                m.getIdMemb(), m.getNombre(), m.precio(),
                m.getDescripcion(), m.getEstado() ? "Activa" : "Inactiva"
            });
        }
    }

    private int idSeleccionado() {
        int fila = tblMembresias.getSelectedRow();
        if (fila < 0) {
            return -1;
        }
        int filaModelo = tblMembresias.convertRowIndexToModel(fila);
        return Integer.parseInt(tblMembresias.getModel().getValueAt(filaModelo, 0).toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMembresias = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel1.setText("Lista de Membresias");

        tblMembresias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Precio", "Descripción", "Estado"
            }
        ));
        jScrollPane1.setViewportView(tblMembresias);

        btnEditar.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnEliminar.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnNuevo.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(179, 179, 179))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnVolver)
                        .addGap(158, 158, 158)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        int id = idSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(this, "Primero selecciona una membresía de la tabla.");
            return;
        }
        Membresia m = membController.consultarPorId(id);
        if (m == null) {
            JOptionPane.showMessageDialog(this, "Esa membresía ya no existe.");
            cargarMembresias();
            return;
        }
        new MembresiaView(m, this::cargarMembresias).setVisible(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        
        int id = idSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(this, "Primero selecciona una membresía de la tabla.");
            return;
        }
        Membresia actual = membController.consultarPorId(id);
        if (actual != null && !actual.getEstado()) {
            JOptionPane.showMessageDialog(this, "Esa membresía ya está inactiva.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres inactivar esta membresía?\nDejará de ofrecerse para nuevos pagos, pero se conservan los pagos ya registrados.",
                "Confirmar inactivación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        if (membController.Eliminar(id)) {
            JOptionPane.showMessageDialog(this, "Membresía inactivada correctamente.");
            cargarMembresias();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo inactivar la membresía.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        new MembresiaView(null, this::cargarMembresias).setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

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

        java.awt.EventQueue.invokeLater(() -> new MembListaView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMembresias;
    // End of variables declaration//GEN-END:variables
}
