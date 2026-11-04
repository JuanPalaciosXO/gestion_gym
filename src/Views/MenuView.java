
package Views;

public class MenuView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuView.class.getName());

    public MenuView() {
        initComponents();
        setTitle("Gestión Gym - Menú principal");
        setSize(741, 439);
        setLocationRelativeTo(null);
    }
    
    private void abrir(javax.swing.JFrame ventana) {
        setVisible(false);
        ventana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                MenuView.this.setVisible(true);
            }
        });
        ventana.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnUsuarios = new javax.swing.JButton();
        btnEntrenadores = new javax.swing.JButton();
        btnPagos = new javax.swing.JButton();
        btnMembresias = new javax.swing.JButton();
        btnSuplementos = new javax.swing.JButton();
        btnEjercicios = new javax.swing.JButton();
        btnMaquinas = new javax.swing.JButton();
        btnRutinas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel1.setText("Bienvenido!");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel2.setText("Menú Gestion Gym Los Brochachos");

        btnUsuarios.setText("Usuarios");
        btnUsuarios.setName("Usuarios"); // NOI18N
        btnUsuarios.addActionListener(this::btnUsuariosActionPerformed);

        btnEntrenadores.setText("Entrenadores");
        btnEntrenadores.addActionListener(this::btnEntrenadoresActionPerformed);

        btnPagos.setText("Pagos");
        btnPagos.addActionListener(this::btnPagosActionPerformed);

        btnMembresias.setText("Membresias");
        btnMembresias.addActionListener(this::btnMembresiasActionPerformed);

        btnSuplementos.setText("Suplementos");
        btnSuplementos.addActionListener(this::btnSuplementosActionPerformed);

        btnEjercicios.setText("Ejercicios");
        btnEjercicios.addActionListener(this::btnEjerciciosActionPerformed);

        btnMaquinas.setText("Máquinas");
        btnMaquinas.addActionListener(this::btnMaquinasActionPerformed);

        btnRutinas.setText("Rutinas");
        btnRutinas.addActionListener(this::btnRutinasActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnMaquinas, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRutinas, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEjercicios, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuplementos, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEntrenadores, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMembresias, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPagos, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(295, 295, 295)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPagos, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMembresias, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEntrenadores, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMaquinas, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuplementos, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEjercicios, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRutinas, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEjerciciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjerciciosActionPerformed
        abrir(new ejercicioView());
    }//GEN-LAST:event_btnEjerciciosActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        abrir(new UsuarioView());
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnMembresiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMembresiasActionPerformed
        abrir(new MembListaView());
    }//GEN-LAST:event_btnMembresiasActionPerformed

    private void btnPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosActionPerformed
        abrir(new PagoListaView());
    }//GEN-LAST:event_btnPagosActionPerformed

    private void btnMaquinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaquinasActionPerformed
        abrir(new MaquinaTblView());
    }//GEN-LAST:event_btnMaquinasActionPerformed

    private void btnSuplementosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplementosActionPerformed
        abrir(new SuplementoTblView());
    }//GEN-LAST:event_btnSuplementosActionPerformed

    private void btnRutinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRutinasActionPerformed
        abrir(new rutinaView());
    }//GEN-LAST:event_btnRutinasActionPerformed

    private void btnEntrenadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrenadoresActionPerformed
        abrir(new EntrenadorView());
    }//GEN-LAST:event_btnEntrenadoresActionPerformed

    public static void main(String args[]) {
  
        java.awt.EventQueue.invokeLater(() -> new MenuView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEjercicios;
    private javax.swing.JButton btnEntrenadores;
    private javax.swing.JButton btnMaquinas;
    private javax.swing.JButton btnMembresias;
    private javax.swing.JButton btnPagos;
    private javax.swing.JButton btnRutinas;
    private javax.swing.JButton btnSuplementos;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
