package Views;

import Controllers.EjercicioController;
import Models.Ejercicio;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ejercicioView extends javax.swing.JFrame {

    private final EjercicioController ejercicioController = new EjercicioController();
    private int idEjercicioSeleccionado = 0;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ejercicioView.class.getName());

    public ejercicioView() {
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
        tblEjercicios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setText("Gestión de Ejercicios");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblNombre.setText("Nombre");

        txtNombre.addActionListener(this::txtNombreActionPerformed);

        lblDocumento.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblDocumento.setText("Tipo muscular");

        lblTelefono.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblTelefono.setText("Series");

        lblCorreo.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblCorreo.setText("Repeticiones");

        lblFechaNacimiento.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblFechaNacimiento.setText("Duración");

        lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblPassword.setText("ID (Opcional)");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechaNacimiento)
                    .addComponent(lblPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            int id = txtPassword.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtPassword.getText().trim());
            String nombre = txtNombre.getText().trim();
            String tipo = txtDocumento.getText().trim();
            int series = Integer.parseInt(txtTelefono.getText().trim());
            int repeticiones = Integer.parseInt(txtCorreo.getText().trim());
            int duracion = Integer.parseInt(txtFechaNacimiento.getText().trim());
            boolean estado = chkEstado.isSelected();

            Ejercicio nuevoEjercicio = new Ejercicio(id, nombre, tipo, series, repeticiones, duracion, estado);
            boolean guardado = ejercicioController.guardar(nuevoEjercicio);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Ejercicio registrado correctamente.");
                txtNombre.setText("");
                txtDocumento.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                txtFechaNacimiento.setText("");
                txtPassword.setText("");
                chkEstado.setSelected(true);
                cargarEjercicios();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el ejercicio.", "Validación", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Series, repeticiones, duración y ID deben ser números enteros válidos.", "Error numérico", JOptionPane.WARNING_MESSAGE);
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

            txtPassword.setText(String.valueOf(idEjercicioSeleccionado));
            txtNombre.setText(
                    tblEjercicios.getModel().getValueAt(filaModelo, 1).toString()
            );
            txtDocumento.setText(
                    tblEjercicios.getModel().getValueAt(filaModelo, 2).toString()
            );
            txtTelefono.setText(
                    tblEjercicios.getModel().getValueAt(filaModelo, 3).toString()
            );
            txtCorreo.setText(
                    tblEjercicios.getModel().getValueAt(filaModelo, 4).toString()
            );
            txtFechaNacimiento.setText(
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
        if (idEjercicioSeleccionado == 0 && txtPassword.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debes seleccionar un ejercicio de la tabla.");
            return;
        }

        try {
            int id = idEjercicioSeleccionado != 0 ? idEjercicioSeleccionado : Integer.parseInt(txtPassword.getText().trim());
            String nombre = txtNombre.getText().trim();
            String tipo = txtDocumento.getText().trim();
            int series = Integer.parseInt(txtTelefono.getText().trim());
            int repeticiones = Integer.parseInt(txtCorreo.getText().trim());
            int duracion = Integer.parseInt(txtFechaNacimiento.getText().trim());
            boolean estado = chkEstado.isSelected();

            Ejercicio ejercicioActualizado = new Ejercicio(id, nombre, tipo, series, repeticiones, duracion, estado);
            boolean actualizado = ejercicioController.actualizar(ejercicioActualizado);

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Ejercicio actualizado correctamente.");
                idEjercicioSeleccionado = 0;
                txtNombre.setText("");
                txtDocumento.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                txtFechaNacimiento.setText("");
                txtPassword.setText("");
                chkEstado.setSelected(true);
                cargarEjercicios();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el ejercicio.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Series, repeticiones, duración y ID deben ser numéricos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idEjercicioSeleccionado == 0 && txtPassword.getText().trim().isEmpty()) {
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
            int id = idEjercicioSeleccionado != 0 ? idEjercicioSeleccionado : Integer.parseInt(txtPassword.getText().trim());
            boolean eliminado = ejercicioController.eliminar(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Ejercicio eliminado correctamente.");
                idEjercicioSeleccionado = 0;
                txtNombre.setText("");
                txtDocumento.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                txtFechaNacimiento.setText("");
                txtPassword.setText("");
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
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtFechaNacimiento.setText("");
        txtPassword.setText("");
        chkEstado.setSelected(true);
        idEjercicioSeleccionado = 0;
        tblEjercicios.clearSelection();
        txtNombre.requestFocusInWindow();
    }

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new ejercicioView().setVisible(true));
    }

    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDocumento;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblEjercicios;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtFechaNacimiento;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtTelefono;
}