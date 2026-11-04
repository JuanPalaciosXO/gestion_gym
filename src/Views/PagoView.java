
package Views;
import Controllers.MembController;
import Controllers.PagoController;
import Controllers.UsuarioController;
import Models.Membresia;
import Models.Pago;
import Models.Usuario;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class PagoView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PagoView.class.getName());

    private final PagoController pagoController = new PagoController();
    private final UsuarioController usuarioController = new UsuarioController();
    private final MembController membController = new MembController();

    private final Pago pagoEditar;
    private final Runnable alGuardar;

    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Membresia> membresias = new ArrayList<>();

    public PagoView() {
        this(null, null);
    }

    public PagoView(Pago pago, Runnable alGuardar) {
        this.pagoEditar = pago;
        this.alGuardar = alGuardar;
        initComponents();
        setSize(741, 439);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cmbMetodo.setModel(new DefaultComboBoxModel<>(
                new String[]{"Efectivo", "Tarjeta", "Transferencia"}));
        cargarUsuarios();
        cargarMembresias();

        if (pago == null) {
            setTitle("Registrar pago");
            txtFecha.setText(LocalDate.now().toString());
            chkEstado.setSelected(true);
        } else {
            setTitle("Editar pago");
            txtFecha.setText(String.valueOf(pago.getFecha()));
            txtValor.setText(String.valueOf(pago.getValor()));
            cmbMetodo.setSelectedItem(pago.getMetodoPago());
            chkEstado.setSelected(pago.isEstado());
        }
    }

    private void cargarUsuarios() {
        cmbUsuario.removeAllItems();
        usuarios.clear();
        for (Usuario u : usuarioController.Listar()) {
            boolean esElActual = pagoEditar != null && pagoEditar.getIdUsuario() == u.getIdUsuario();
            if (u.isEstado() || esElActual) {
                usuarios.add(u);
                cmbUsuario.addItem(u.getNombre() + " - " + u.getDocumento());
                if (esElActual) {
                    cmbUsuario.setSelectedIndex(usuarios.size() - 1);
                }
            }
        }
    }

    private void cargarMembresias() {
        cmbMembresia.removeAllItems();
        membresias.clear();
        for (Membresia m : membController.Listar()) {
            boolean esLaActual = pagoEditar != null && pagoEditar.getIdMembresia() == m.getIdMemb();
            if (m.getEstado() || esLaActual) {
                membresias.add(m);
                cmbMembresia.addItem(m.getNombre());
                if (esLaActual) {
                    cmbMembresia.setSelectedIndex(membresias.size() - 1);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombre = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        lblDuracion = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmbUsuario = new javax.swing.JComboBox<>();
        cmbMembresia = new javax.swing.JComboBox<>();
        lblDuracion1 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        lblPrecio1 = new javax.swing.JLabel();
        cmbMetodo = new javax.swing.JComboBox<>();
        chkEstado = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombre.setText("Usuario");

        lblPrecio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPrecio.setText("Membresia");

        lblDuracion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDuracion.setText("Fecha");

        txtFecha.setText("AAAA-MM-DD");
        txtFecha.addActionListener(this::txtFechaActionPerformed);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel1.setText("Registrar Pago");

        cmbUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbMembresia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblDuracion1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDuracion1.setText("Valor");

        lblPrecio1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPrecio1.setText("Metodo de pago");

        cmbMetodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        chkEstado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkEstado.setSelected(true);
        chkEstado.setText("Activo");

        btnGuardar.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnVolver)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDuracion1)
                        .addGap(236, 236, 236))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDuracion)
                            .addComponent(lblPrecio)
                            .addComponent(cmbUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbMembresia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPrecio1)
                            .addComponent(chkEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValor)
                            .addComponent(cmbMetodo, 0, 170, Short.MAX_VALUE))
                        .addGap(104, 104, 104))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombre)
                                    .addComponent(lblDuracion1))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(txtValor)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(lblPrecio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbMembresia, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblPrecio1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkEstado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        int iUsuario = cmbUsuario.getSelectedIndex();
        int iMembresia = cmbMembresia.getSelectedIndex();
        if (iUsuario < 0 || iMembresia < 0) {
            JOptionPane.showMessageDialog(this,
                    "Necesitas al menos un usuario activo y una membresía activa para registrar un pago.");
            return;
        }
        try {
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            int valor = Integer.parseInt(txtValor.getText().trim());
            String metodo = (String) cmbMetodo.getSelectedItem();
            boolean estado = chkEstado.isSelected();
            int idUsuario = usuarios.get(iUsuario).getIdUsuario();
            int idMembresia = membresias.get(iMembresia).getIdMemb();

            boolean ok;
            if (pagoEditar == null) {
                ok = pagoController.Guardar(0, fecha, valor, metodo, estado, idUsuario, idMembresia);
            } else {
                ok = pagoController.Actualizar(new Pago(
                        pagoEditar.getIdPago(), fecha, valor, metodo, estado, idUsuario, idMembresia));
            }

            if (ok) {
                JOptionPane.showMessageDialog(this, pagoEditar == null
                        ? "Pago registrado correctamente."
                        : "Pago actualizado correctamente.");
                if (alGuardar != null) {
                    alGuardar.run();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo guardar. Revisa que la fecha no sea futura y que el valor sea mayor que 0.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "La fecha debe tener el formato AAAA-MM-DD (por ejemplo, 2026-11-04).",
                    "Fecha inválida", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El valor debe ser un número entero, sin puntos ni comas.",
                    "Valor inválido", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

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

        java.awt.EventQueue.invokeLater(() -> new PagoView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JComboBox<String> cmbMembresia;
    private javax.swing.JComboBox<String> cmbMetodo;
    private javax.swing.JComboBox<String> cmbUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDuracion;
    private javax.swing.JLabel lblDuracion1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblPrecio1;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
