package Views;
import Controllers.ReporteSupController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DescuentoSupView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DescuentoSupView.class.getName());
    private ReporteSupController controller;
    
    public DescuentoSupView() {
        initComponents();
        controller = new ReporteSupController();
        setLocationRelativeTo(null);
        cargarSimulacion(10.0);
    }
    
    private void cargarSimulacion(double porcentaje) {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setColumnIdentifiers(new Object[]{"Suplemento", "Precio Original", "Precio con Descuento", "Stock"});
        modelo.setRowCount(0);

        List<Object[]> datos = controller.simularDescuento(porcentaje);

        if (datos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay suplementos registrados para simular descuentos.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Object[] fila : datos) {
                String nombre = (String) fila[0];
                double precioOriginal = (double) fila[1];
                double precioConDescuento = (double) fila[2];
                int stock = (int) fila[3];

                modelo.addRow(new Object[]{
                    nombre,
                    "$" + String.format("%.2f", precioOriginal),
                    "$" + String.format("%.2f", precioConDescuento),
                    stock
                });
            }
        }
    }
    
    private void ejecutarSimulacion() {
        try {
            double porcentaje = Double.parseDouble(txtPorcentaje.getText().trim());
            if (porcentaje < 0 || porcentaje > 100) {
                JOptionPane.showMessageDialog(this, "Por favor ingresa un porcentaje válido entre 0 y 100.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            cargarSimulacion(porcentaje);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El valor ingresado no es un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new DescuentoSupView().setVisible(true));
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnSimular = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnVolver1 = new javax.swing.JButton();
        txtPorcentaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Descuentos en Suplementos");

        btnSimular.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSimular.setText("Simular");
        btnSimular.addActionListener(this::btnSimularActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnVolver1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnVolver1.setText("Volver");
        btnVolver1.addActionListener(this::btnVolver1ActionPerformed);

        txtPorcentaje.setText("Valor");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnVolver1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(txtPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(202, 202, 202)
                        .addComponent(btnSimular))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnVolver1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimular)
                    .addComponent(txtPorcentaje))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolver1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolver1ActionPerformed
        new SuplementoTblView().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVolver1ActionPerformed

    private void btnSimularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimularActionPerformed
        ejecutarSimulacion();
    }//GEN-LAST:event_btnSimularActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimular;
    private javax.swing.JButton btnVolver1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel txtPorcentaje;
    // End of variables declaration//GEN-END:variables
}
