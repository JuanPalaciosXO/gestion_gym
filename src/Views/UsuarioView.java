package Views;

import Controllers.UsuarioController;
import Models.Usuario;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Controllers.RutinaController;
import Controllers.UsuarioRutinaController;
import Models.Rutina;

public class UsuarioView extends javax.swing.JFrame {

    private final UsuarioController usuarioController = new UsuarioController();
    private final RutinaController rutinaController = new RutinaController();

    private final UsuarioRutinaController usuarioRutinaController
            = new UsuarioRutinaController();

    private int idUsuarioSeleccionado = 0;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UsuarioView.class.getName());

    public UsuarioView() {
        initComponents();
        setLocationRelativeTo(null);
        cargarUsuarios();
        cargarRutinasDisponibles();
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

    private void cargarRutinasDisponibles() {
        cmbRutina.removeAllItems();
        cmbRutina.addItem("Seleccione una rutina");

        try {
            for (Rutina rutina : rutinaController.Listar()) {
                if (rutina.isEstado()) {
                    cmbRutina.addItem(
                            rutina.getId() + " - " + rutina.getNombre()
                    );
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar rutinas: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void calcularEdadUsuario() {
        if (idUsuarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Primero selecciona un usuario de la tabla.");
            return;
        }

        try {
            String fechaTexto = txtFechaNacimiento.getText().trim();

            if (fechaTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El usuario no tiene fecha de nacimiento registrada.");
                return;
            }

            LocalDate nacimiento = LocalDate.parse(fechaTexto);
            LocalDate hoy = LocalDate.now();

            if (nacimiento.isAfter(hoy)) {
                JOptionPane.showMessageDialog(this, "La fecha de nacimiento no puede ser futura.");
                return;
            }

            int edad = java.time.Period.between(nacimiento, hoy).getYears();

            JOptionPane.showMessageDialog(this,
                    "Edad del usuario: " + edad + " años");
        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "La fecha debe tener el formato AAAA-MM-DD.");
        }
    }

    private void verificarMembresiaUsuario() {
        if (idUsuarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Primero selecciona un usuario.");
            return;
        }

        String sql = "SELECT p.fecha, m.nombre, m.duracion_mes "
                + "FROM pago p "
                + "INNER JOIN membresia m ON p.idMemb = m.idMemb "
                + "WHERE p.idUsuario = ? AND p.estado = 1 "
                + "ORDER BY p.fecha DESC, p.idPago DESC LIMIT 1";

        java.sql.Connection conexion = DB.Database.conectar();

        if (conexion == null) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
            return;
        }

        try (java.sql.PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idUsuarioSeleccionado);

            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    LocalDate inicio = rs.getDate("fecha").toLocalDate();
                    int meses = rs.getInt("duracion_mes");

                    LocalDate vencimiento = inicio.plusMonths(meses);
                    boolean vigente = LocalDate.now().isBefore(vencimiento);

                    JOptionPane.showMessageDialog(this,
                            "Membresía: " + nombre
                            + "\nFecha de inicio: " + inicio
                            + "\nFecha de vencimiento: " + vencimiento
                            + "\nEstado: " + (vigente ? "VIGENTE" : "VENCIDA"));
                } else {
                    JOptionPane.showMessageDialog(this,
                            "El usuario no tiene pagos de membresía registrados.");
                }
            }
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al verificar membresía: " + ex.getMessage());
        } finally {
            DB.Database.desconectar(conexion);
        }
    }

    private void calcularDiasRestantes() {
        if (idUsuarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Primero selecciona un usuario.");
            return;
        }

        String sql = "SELECT p.fecha, m.nombre, m.duracion_mes "
                + "FROM pago p "
                + "INNER JOIN membresia m ON p.idMemb = m.idMemb "
                + "WHERE p.idUsuario = ? AND p.estado = 1 "
                + "ORDER BY p.fecha DESC, p.idPago DESC LIMIT 1";

        java.sql.Connection conexion = DB.Database.conectar();

        if (conexion == null) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
            return;
        }

        try (java.sql.PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idUsuarioSeleccionado);

            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    LocalDate inicio = rs.getDate("fecha").toLocalDate();
                    int meses = rs.getInt("duracion_mes");

                    LocalDate vencimiento = inicio.plusMonths(meses);

                    long dias = java.time.temporal.ChronoUnit.DAYS.between(
                            LocalDate.now(), vencimiento
                    );

                    String mensaje;

                    if (dias > 0) {
                        mensaje = "Le quedan " + dias + " días de membresía.";
                    } else if (dias == 0) {
                        mensaje = "La membresía vence hoy.";
                    } else {
                        mensaje = "La membresía venció hace " + Math.abs(dias) + " días.";
                    }

                    JOptionPane.showMessageDialog(this,
                            "Membresía: " + nombre
                            + "\nVencimiento: " + vencimiento
                            + "\n" + mensaje);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "El usuario no tiene pagos de membresía registrados.");
                }
            }
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al calcular días restantes: " + ex.getMessage());
        } finally {
            DB.Database.desconectar(conexion);
        }
    }

    private void alertaVencimientoMembresia() {
        if (idUsuarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Primero selecciona un usuario.");
            return;
        }

        String sql = "SELECT p.fecha, m.nombre, m.duracion_mes "
                + "FROM pago p "
                + "INNER JOIN membresia m ON p.idMemb = m.idMemb "
                + "WHERE p.idUsuario = ? AND p.estado = 1 "
                + "ORDER BY p.fecha DESC, p.idPago DESC LIMIT 1";

        java.sql.Connection conexion = DB.Database.conectar();

        if (conexion == null) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
            return;
        }

        try (java.sql.PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idUsuarioSeleccionado);

            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    LocalDate inicio = rs.getDate("fecha").toLocalDate();
                    int meses = rs.getInt("duracion_mes");

                    LocalDate vencimiento = inicio.plusMonths(meses);

                    long dias = java.time.temporal.ChronoUnit.DAYS.between(
                            LocalDate.now(), vencimiento
                    );

                    String mensaje;
                    int tipoMensaje;

                    if (dias < 0) {
                        mensaje = "ALERTA: La membresía está vencida desde hace "
                                + Math.abs(dias) + " días.";
                        tipoMensaje = JOptionPane.ERROR_MESSAGE;
                    } else if (dias == 0) {
                        mensaje = "ALERTA: La membresía vence hoy.";
                        tipoMensaje = JOptionPane.WARNING_MESSAGE;
                    } else if (dias <= 7) {
                        mensaje = "ALERTA: La membresía vence en " + dias + " días.";
                        tipoMensaje = JOptionPane.WARNING_MESSAGE;
                    } else {
                        mensaje = "La membresía está vigente. Faltan "
                                + dias + " días para su vencimiento.";
                        tipoMensaje = JOptionPane.INFORMATION_MESSAGE;
                    }

                    JOptionPane.showMessageDialog(
                            this,
                            "Membresía: " + nombre
                            + "\nVencimiento: " + vencimiento
                            + "\n" + mensaje,
                            "Estado de membresía",
                            tipoMensaje
                    );
                } else {
                    JOptionPane.showMessageDialog(this,
                            "ALERTA: El usuario no tiene pagos de membresía registrados.",
                            "Sin membresía",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al consultar membresía: " + ex.getMessage());
        } finally {
            DB.Database.desconectar(conexion);
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
        cmbRutina = new javax.swing.JComboBox<>();
        lblRutina = new javax.swing.JLabel();
        btnAsignarRutina = new javax.swing.JButton();
        btnVerRutinas = new javax.swing.JButton();
        btnQuitarRutina = new javax.swing.JButton();
        btnCalcularEdad = new javax.swing.JButton();
        btnVerificarMembresia = new javax.swing.JButton();
        btnDiasRestantes = new javax.swing.JButton();
        btnAlertaVencimiento = new javax.swing.JButton();

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

        txtCorreo.addActionListener(this::txtCorreoActionPerformed);

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

        cmbRutina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbRutina.addActionListener(this::cmbRutinaActionPerformed);

        lblRutina.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRutina.setText("Rutina");

        btnAsignarRutina.setText("Asignar rutina");
        btnAsignarRutina.addActionListener(this::btnAsignarRutinaActionPerformed);

        btnVerRutinas.setText("Ver rutinas");
        btnVerRutinas.addActionListener(this::btnVerRutinasActionPerformed);

        btnQuitarRutina.setText("Quitar rutina");
        btnQuitarRutina.addActionListener(this::btnQuitarRutinaActionPerformed);

        btnCalcularEdad.setText("Calcular edad");
        btnCalcularEdad.addActionListener(this::btnCalcularEdadActionPerformed);

        btnVerificarMembresia.setText("Verificar membresía");
        btnVerificarMembresia.addActionListener(this::btnVerificarMembresiaActionPerformed);

        btnDiasRestantes.setText("Días restantes");
        btnDiasRestantes.addActionListener(this::btnDiasRestantesActionPerformed);

        btnAlertaVencimiento.setText("Alerta vencimiento");
        btnAlertaVencimiento.addActionListener(this::btnAlertaVencimientoActionPerformed);

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
                .addGap(18, 18, 18)
                .addComponent(btnVolver)
                .addGap(201, 201, 201))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chkEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAsignarRutina)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnVerRutinas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnQuitarRutina)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCalcularEdad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnVerificarMembresia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDiasRestantes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAlertaVencimiento))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNombre)
                                    .addComponent(lblTelefono)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                                    .addComponent(txtTelefono)
                                    .addComponent(lblRutina)
                                    .addComponent(cmbRutina, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFechaNacimiento)
                                    .addComponent(txtFechaNacimiento))
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPassword)
                                    .addComponent(lblPassword)
                                    .addComponent(txtDocumento)
                                    .addComponent(lblDocumento)
                                    .addComponent(txtCorreo)
                                    .addComponent(lblCorreo)))
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(350, 350, 350)
                        .addComponent(lblTitulo)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombre)
                            .addComponent(lblDocumento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(txtDocumento))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCorreo)
                            .addComponent(lblTelefono))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                            .addComponent(txtTelefono))
                        .addGap(18, 18, 18)
                        .addComponent(lblFechaNacimiento)
                        .addGap(8, 8, 8)
                        .addComponent(txtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lblRutina)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbRutina, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAsignarRutina, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVerRutinas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuitarRutina, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCalcularEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVerificarMembresia, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDiasRestantes, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlertaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chkEstado)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
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

    private void cmbRutinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRutinaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRutinaActionPerformed

    private void btnAsignarRutinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarRutinaActionPerformed
        if (idUsuarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this,
                    "Primero debes seleccionar un usuario de la tabla.");
            return;
        }

        if (cmbRutina.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Debes seleccionar una rutina.");
            return;
        }

        try {
            String seleccion = cmbRutina.getSelectedItem().toString();
            int idRutina = Integer.parseInt(seleccion.split(" - ", 2)[0]);

            boolean asignado = usuarioRutinaController.asignarRutina(
                    idUsuarioSeleccionado,
                    idRutina
            );

            if (asignado) {
                JOptionPane.showMessageDialog(this,
                        "Rutina asignada correctamente al usuario.");
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo asignar la rutina. Verifica si ya está asignada.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al asignar rutina: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAsignarRutinaActionPerformed

    private void btnVerRutinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerRutinasActionPerformed
        if (idUsuarioSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario.");
            return;
        }

        try {
            java.util.List<Integer> ids
                    = usuarioRutinaController.obtenerRutinas(idUsuarioSeleccionado);

            if (ids.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El usuario no tiene rutinas asignadas.");
                return;
            }

            StringBuilder mensaje = new StringBuilder("Rutinas asignadas:\n");

            for (Rutina rutina : rutinaController.Listar()) {
                if (ids.contains(rutina.getId())) {
                    mensaje.append(rutina.getId())
                            .append(" - ")
                            .append(rutina.getNombre())
                            .append("\n");
                }
            }

            JOptionPane.showMessageDialog(this, mensaje.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnVerRutinasActionPerformed

    private void btnQuitarRutinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarRutinaActionPerformed
        if (idUsuarioSeleccionado == 0 || cmbRutina.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario y una rutina.");
            return;
        }

        try {
            String seleccion = cmbRutina.getSelectedItem().toString();
            int idRutina = Integer.parseInt(seleccion.split(" - ", 2)[0]);

            boolean quitada = usuarioRutinaController.quitarRutina(
                    idUsuarioSeleccionado, idRutina
            );

            JOptionPane.showMessageDialog(this,
                    quitada ? "Rutina retirada correctamente."
                            : "No se encontró esa asignación.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnQuitarRutinaActionPerformed

    private void btnCalcularEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularEdadActionPerformed
        calcularEdadUsuario();
    }//GEN-LAST:event_btnCalcularEdadActionPerformed

    private void btnVerificarMembresiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarMembresiaActionPerformed
        verificarMembresiaUsuario();
    }//GEN-LAST:event_btnVerificarMembresiaActionPerformed

    private void btnDiasRestantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiasRestantesActionPerformed
        calcularDiasRestantes();
    }//GEN-LAST:event_btnDiasRestantesActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void btnAlertaVencimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlertaVencimientoActionPerformed
        alertaVencimientoMembresia();
    }//GEN-LAST:event_btnAlertaVencimientoActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new UsuarioView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAlertaVencimiento;
    private javax.swing.JButton btnAsignarRutina;
    private javax.swing.JButton btnCalcularEdad;
    private javax.swing.JButton btnDiasRestantes;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnQuitarRutina;
    private javax.swing.JButton btnVerRutinas;
    private javax.swing.JButton btnVerificarMembresia;
    private javax.swing.JButton btnVolver;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JComboBox<String> cmbRutina;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDocumento;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRutina;
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
