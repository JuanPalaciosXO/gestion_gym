
package Views;

import Models.Maquina;
import Controllers.MaquinaController;
import Controllers.ReporteMaquinaController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class MaquinaTblView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MaquinaTblView.class.getName());
    private final MaquinaController maquinaController = new MaquinaController();
    private final ReporteMaquinaController reporteMaquinaController = new ReporteMaquinaController();

    public MaquinaTblView() {
        initComponents();
        btnBuscar.addActionListener(this::txtBuscarActionPerformed);
        setSize(741, 439);
        setLocationRelativeTo(null);
        cargarMaquinas();
        cargarEstadisticas();
    }
    
    private void cargarMaquinas() {
        llenarTabla(maquinaController.Listar());
    }

    private void llenarTabla(List<Maquina> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblMaquinas.getModel();
        modelo.setRowCount(0);
        for (Maquina m : lista) {
            modelo.addRow(new Object[]{
                m.getIdMn(),
                m.getNombre(),
                m.getTipo(),
                m.getUbicacion(),
                m.getDescripcion(),
                m.getEstado() ? "Activa" : "Inactiva"
            });
        }
    }
    
    private int idSeleccionado() {
        int fila = tblMaquinas.getSelectedRow();
        if (fila < 0) {
            return -1;
        }
        int filaModelo = tblMaquinas.convertRowIndexToModel(fila);
        return Integer.parseInt(tblMaquinas.getModel().getValueAt(filaModelo, 0).toString());
    }
    
    private void cargarEstadisticas() {
        try {
            int activas = reporteMaquinaController.obtenerConteoMaquinas(1);
            int inactivas = reporteMaquinaController.obtenerConteoMaquinas(0);

            TblTotalActivas.setText("Máquinas Activas: " + activas);
            TblTotalInactivas.setText("Máquinas Inactivas: " + inactivas);

            TblTotalActivas.setForeground(new java.awt.Color(0, 153, 51));
            TblTotalInactivas.setForeground(java.awt.Color.RED);
        } catch (Exception e) {
            System.out.println("Error al cargar estadísticas: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTotalInactivas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMaquinas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        btnDesactivar = new javax.swing.JButton();
        TblTotalInactivas = new javax.swing.JLabel();
        TblTotalActivas = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnReporte = new javax.swing.JButton();
        btnActivar = new javax.swing.JButton();
        btnFrecuencia = new javax.swing.JButton();
        btnGrafica = new javax.swing.JButton();
        btnHistorial = new javax.swing.JButton();

        lblTotalInactivas.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblMaquinas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Tipo", "Ubicacion", "Descripcion", "Estado"
            }
        ));
        jScrollPane1.setViewportView(tblMaquinas);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Tabla de Maquinas");

        btnNuevo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnVolver.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        btnDesactivar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDesactivar.setText("Desactivar");
        btnDesactivar.addActionListener(this::btnDesactivarActionPerformed);

        TblTotalInactivas.setText("Total Inactivas");

        TblTotalActivas.setText("Total Activas");

        txtBuscar.addActionListener(this::txtBuscarActionPerformed);

        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setText("Buscar");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Buscar");

        btnReporte.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReporte.setText("Ver reporte");
        btnReporte.addActionListener(this::btnReporteActionPerformed);

        btnActivar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActivar.setText("Activar");
        btnActivar.addActionListener(this::btnActivarActionPerformed);

        btnFrecuencia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFrecuencia.setText("Ver frecuencia");
        btnFrecuencia.addActionListener(this::btnFrecuenciaActionPerformed);

        btnGrafica.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGrafica.setText("Ver grafica");
        btnGrafica.addActionListener(this::btnGraficaActionPerformed);

        btnHistorial.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHistorial.setText("Ver historial");
        btnHistorial.addActionListener(this::btnHistorialActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TblTotalInactivas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TblTotalActivas, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(btnDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(442, 442, 442)
                                .addComponent(btnActivar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(btnGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnFrecuencia, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                    .addComponent(btnReporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 55, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TblTotalActivas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TblTotalInactivas)))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDesactivar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnActivar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnReporte)
                    .addComponent(btnGrafica))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFrecuencia)
                    .addComponent(btnHistorial))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
            new MaquinaView(null, this::cargarMaquinas).setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
    int id = idSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(this, "Primero selecciona una máquina de la tabla.");
            return;
        }
        Maquina m = maquinaController.consultarPorId(id);
        if (m == null) {
            JOptionPane.showMessageDialog(this, "Esa máquina ya no existe.");
            cargarMaquinas();
            return;
        }
        new MaquinaView(m, this::cargarMaquinas).setVisible(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        new Views.MenuView().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarActionPerformed
        int id = idSeleccionado();
        if (id != -1) {
            boolean exito = maquinaController.desactivar(id);

            if (exito) {
                cargarMaquinas();     
                cargarEstadisticas();
                javax.swing.JOptionPane.showMessageDialog(this, "Máquina desactivada correctamente.");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "No se pudo desactivar la máquina.");
            }
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Por favor seleccione una máquina.");
            }
    }//GEN-LAST:event_btnDesactivarActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        List<Maquina> resultados = maquinaController.buscar(txtBuscar.getText());
        llenarTabla(resultados);
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
       new Views.ReporteMaquinaView().setVisible(true);
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarActionPerformed
    int filaSeleccionada = tblMaquinas.getSelectedRow();
    if (filaSeleccionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Por favor selecciona una máquina de la tabla para activar.", 
            "Aviso", 
            javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int idMn = Integer.parseInt(tblMaquinas.getValueAt(filaSeleccionada, 0).toString());
    boolean exito = reporteMaquinaController.activarMaquina(idMn);
    
        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "¡Máquina activada con éxito!", 
                "Éxito", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            cargarMaquinas();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error al activar la máquina.", 
                "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActivarActionPerformed

    private void btnFrecuenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrecuenciaActionPerformed
        new FrecuenciaMaqView().setVisible(true);
        
    }//GEN-LAST:event_btnFrecuenciaActionPerformed

    private void btnGraficaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficaActionPerformed
        new ListadoMaqView().setVisible(true);
        
    }//GEN-LAST:event_btnGraficaActionPerformed

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed
        new HistorialMaqView().setVisible(true);
        
    }//GEN-LAST:event_btnHistorialActionPerformed
    
    
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

        java.awt.EventQueue.invokeLater(() -> new MaquinaTblView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TblTotalActivas;
    private javax.swing.JLabel TblTotalInactivas;
    private javax.swing.JButton btnActivar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnDesactivar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnFrecuencia;
    private javax.swing.JButton btnGrafica;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotalInactivas;
    private javax.swing.JTable tblMaquinas;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
