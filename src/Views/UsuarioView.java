package Views;

import Controllers.UsuarioController;
import Models.Usuario;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UsuarioView extends javax.swing.JFrame {

    private final UsuarioController usuarioController = new UsuarioController();

    private int idUsuarioSeleccionado = 0;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UsuarioView.class.getName());

    public UsuarioView() {
        initComponents();
        setLocationRelativeTo(null);
        cargarUsuarios();
    }

    private void cargarUsuarios() {

        DefaultTableModel modelo = (DefaultTableModel) tblUsuarios.getModel();

        modelo.setRowCount(0);

        try {
            for (Usuario usuario : usuarioController.Listar()) {

                modelo.addRow(new Object[]{
                    usuario.getIdUsuario(),
                    usuario.getNombre(),
                    usuario.getDocumento(),
                    usuario.getTelefono(),
                    usuario.getCorreo(),
                    usuario.getFechaNacimiento(),
                    usuario.isEstado() ? "Activo" : "Inactivo"
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar usuarios: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblDocumento = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblFechaNacimiento = new javax.swing.JLabel();
        txtFechaNacimiento = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        chkEstado = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Gestión de Usuarios");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombre.setText("Nombre");

        txtNombre.addActionListener(this::txtNombreActionPerformed);

        lblDocumento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDocumento.setText("Documento");

        lblTelefono.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTelefono.setText("Teléfono");

        lblCorreo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCorreo.setText("Correo");

        lblFechaNacimiento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaNacimiento.setText("Fecha de nacimiento");

        lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPassword.setText("Contraseña");

        chkEstado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Documento", "Teléfono", "Correo", "Fecha nacimiento", "Estado"
            }
        ));
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

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
                                    .addComponent(txtFechaNacimiento))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblPassword)
                                    .addComponent(lblCorreo)
                                    .addComponent(lblDocumento)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                                    .addComponent(txtCorreo)
                                    .addComponent(txtDocumento)))
                            .addComponent(lblTelefono)
                            .addComponent(lblFechaNacimiento)
                            .addComponent(chkEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(lblTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)
                        .addGap(18, 18, 18)
                        .addComponent(btnVolver)))
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
                    .addComponent(lblDocumento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtNombre))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCorreo)
                    .addComponent(lblTelefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTelefono)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechaNacimiento)
                    .addComponent(lblPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chkEstado)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar)
                        .addComponent(btnActualizar)
                        .addComponent(btnEliminar)
                        .addComponent(btnLimpiar)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El nombre del usuario es obligatorio.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            txtNombre.requestFocusInWindow();
            return;
        }
        String documentoIngresado = txtDocumento.getText().trim();

        if (documentoIngresado.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El documento es obligatorio.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            txtDocumento.requestFocusInWindow();
            return;
        }

        String correoValidar = txtCorreo.getText().trim();

        if (!correoValidar.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingresa un correo electrónico válido.",
                    "Correo inválido",
                    JOptionPane.WARNING_MESSAGE
            );

            txtCorreo.requestFocusInWindow();
            return;
        }

        char[] caracteresPassword = txtPassword.getPassword();

        if (caracteresPassword.length < 8
                || caracteresPassword.length > 72) {

            JOptionPane.showMessageDialog(
                    this,
                    "La contraseña debe tener entre 8 y 72 caracteres.",
                    "Contraseña inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            txtPassword.requestFocusInWindow();
            java.util.Arrays.fill(caracteresPassword, '\0');
            return;
        }

        java.util.Arrays.fill(caracteresPassword, '\0');

        String documentoValidar = txtDocumento.getText().trim();

        try {
            for (Usuario usuario : usuarioController.Listar()) {

                if (usuario.getDocumento().equals(documentoValidar)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Ya existe un usuario registrado con ese documento.",
                            "Documento duplicado",
                            JOptionPane.WARNING_MESSAGE
                    );

                    txtDocumento.requestFocusInWindow();
                    return;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo verificar el documento: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            String nombre = txtNombre.getText().trim();
            String documento = txtDocumento.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();
            String fechaTexto = txtFechaNacimiento.getText().trim();

            LocalDate fechaNacimiento = fechaTexto.isEmpty()
                    ? null
                    : LocalDate.parse(fechaTexto);

            boolean estado = chkEstado.isSelected();

            String password = new String(txtPassword.getPassword());

            boolean guardado = usuarioController.Guardar(
                    0,
                    nombre,
                    documento,
                    telefono,
                    correo,
                    fechaNacimiento,
                    estado,
                    new ArrayList<>(),
                    password
            );

            if (guardado) {
                JOptionPane.showMessageDialog(this,
                        "Usuario registrado correctamente.");

                txtNombre.setText("");
                txtDocumento.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                txtFechaNacimiento.setText("");
                txtPassword.setText("");
                chkEstado.setSelected(true);
                cargarUsuarios();

            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo registrar el usuario.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "La fecha debe tener el formato AAAA-MM-DD.",
                    "Fecha inválida",
                    JOptionPane.WARNING_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked

        int fila = tblUsuarios.getSelectedRow();

        if (fila >= 0) {

            int filaModelo = tblUsuarios.convertRowIndexToModel(fila);

            idUsuarioSeleccionado = Integer.parseInt(
                    tblUsuarios.getModel().getValueAt(filaModelo, 0).toString()
            );

            txtNombre.setText(
                    tblUsuarios.getModel().getValueAt(filaModelo, 1).toString()
            );

            txtDocumento.setText(
                    tblUsuarios.getModel().getValueAt(filaModelo, 2).toString()
            );

            txtTelefono.setText(
                    tblUsuarios.getModel().getValueAt(filaModelo, 3).toString()
            );

            txtCorreo.setText(
                    tblUsuarios.getModel().getValueAt(filaModelo, 4).toString()
            );

            Object fecha = tblUsuarios.getModel().getValueAt(filaModelo, 5);
            txtFechaNacimiento.setText(
                    fecha == null ? "" : fecha.toString()
            );

            chkEstado.setSelected(
                    "Activo".equals(
                            tblUsuarios.getModel().getValueAt(filaModelo, 6).toString()
                    )
            );
            txtPassword.setText("");
        }

    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed

        if (idUsuarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this,
                    "Primero debes seleccionar un usuario de la tabla.");
            return;
        }

        try {
            String fechaTexto = txtFechaNacimiento.getText().trim();

            LocalDate fechaNacimiento = fechaTexto.isEmpty()
                    ? null
                    : LocalDate.parse(fechaTexto);

            boolean actualizado = usuarioController.Actualizar(
                    idUsuarioSeleccionado,
                    txtNombre.getText().trim(),
                    txtDocumento.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtCorreo.getText().trim(),
                    fechaNacimiento,
                    chkEstado.isSelected(),
                    new ArrayList<>()
            );

            if (actualizado) {
                JOptionPane.showMessageDialog(this,
                        "Usuario actualizado correctamente.");

                idUsuarioSeleccionado = 0;

                txtNombre.setText("");
                txtDocumento.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                txtFechaNacimiento.setText("");
                txtPassword.setText("");
                chkEstado.setSelected(true);

                cargarUsuarios();

            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo actualizar el usuario.");
            }

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "La fecha debe tener el formato AAAA-MM-DD.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        if (idUsuarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this,
                    "Primero debes seleccionar un usuario de la tabla.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar este usuario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean eliminado = usuarioController.Eliminar(
                    idUsuarioSeleccionado
            );

            if (eliminado) {
                JOptionPane.showMessageDialog(this,
                        "Usuario eliminado correctamente.");

                idUsuarioSeleccionado = 0;

                txtNombre.setText("");
                txtDocumento.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                txtFechaNacimiento.setText("");
                txtPassword.setText("");
                chkEstado.setSelected(true);

                cargarUsuarios();

            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo eliminar el usuario.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed

        txtNombre.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtFechaNacimiento.setText("");
        txtPassword.setText("");

        chkEstado.setSelected(true);

        idUsuarioSeleccionado = 0;

        tblUsuarios.clearSelection();

        txtNombre.requestFocusInWindow();

    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new UsuarioView().setVisible(true));
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
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtFechaNacimiento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
