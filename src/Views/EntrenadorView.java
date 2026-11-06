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
    
    private void contarRutinasEntrenador() {
    if (idEntrenadorSeleccionado == 0) {
        JOptionPane.showMessageDialog(this, "Primero selecciona un entrenador de la tabla.");
        return;
    }

    String sql = "SELECT COUNT(*) AS total FROM rutina "
            + "WHERE idEntrenador = ? AND estado = 1";

    java.sql.Connection conexion = DB.Database.conectar();

    if (conexion == null) {
        JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
        return;
    }

    try (java.sql.PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idEntrenadorSeleccionado);

        try (java.sql.ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int total = rs.getInt("total");

                JOptionPane.showMessageDialog(this,
                        "Entrenador: " + txtNombre.getText()
                        + "\nRutinas activas asignadas: " + total,
                        "Estadísticas del entrenador",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (java.sql.SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error al contar rutinas: " + ex.getMessage());
    } finally {
        DB.Database.desconectar(conexion);
    }
}
    
    private void contarAlumnosEntrenador() {
    if (idEntrenadorSeleccionado == 0) {
        JOptionPane.showMessageDialog(this, "Primero selecciona un entrenador de la tabla.");
        return;
    }

    String sql = "SELECT COUNT(DISTINCT u.idUsuario) AS total "
            + "FROM usuarios u "
            + "INNER JOIN usuario_rutina ur ON u.idUsuario = ur.idUsuario "
            + "INNER JOIN rutina r ON ur.idRutina = r.idRutina "
            + "WHERE r.idEntrenador = ? "
            + "AND r.estado = 1 AND u.estado = 1";

    java.sql.Connection conexion = DB.Database.conectar();

    if (conexion == null) {
        JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
        return;
    }

    try (java.sql.PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idEntrenadorSeleccionado);

        try (java.sql.ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int total = rs.getInt("total");

                JOptionPane.showMessageDialog(this,
                        "Entrenador: " + txtNombre.getText()
                        + "\nAlumnos activos asignados: " + total,
                        "Alumnos del entrenador",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (java.sql.SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error al contar alumnos: " + ex.getMessage());
    } finally {
        DB.Database.desconectar(conexion);
    }
}
    
    private void analizarNivelesEntrenador() {
    if (idEntrenadorSeleccionado == 0) {
        JOptionPane.showMessageDialog(this, "Primero selecciona un entrenador.");
        return;
    }

    String sql = "SELECT nivel, COUNT(*) AS total "
            + "FROM rutina "
            + "WHERE idEntrenador = ? AND estado = 1 "
            + "GROUP BY nivel";

    java.sql.Connection conexion = DB.Database.conectar();

    if (conexion == null) {
        JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
        return;
    }

    int principiantes = 0;
    int intermedios = 0;
    int avanzados = 0;
    int sinNivel = 0;

    try (java.sql.PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idEntrenadorSeleccionado);

        try (java.sql.ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nivel = rs.getString("nivel");
                int total = rs.getInt("total");

                if (nivel == null) {
                    sinNivel += total;
                } else {
                    switch (nivel.trim().toLowerCase()) {
                        case "principiante":
                            principiantes += total;
                            break;
                        case "intermedio":
                            intermedios += total;
                            break;
                        case "avanzado":
                            avanzados += total;
                            break;
                        default:
                            sinNivel += total;
                    }
                }
            }
        }

        int totalRutinas = principiantes + intermedios + avanzados + sinNivel;

        JOptionPane.showMessageDialog(this,
                "Entrenador: " + txtNombre.getText()
                + "\n\nPrincipiante: " + principiantes
                + "\nIntermedio: " + intermedios
                + "\nAvanzado: " + avanzados
                + "\nSin nivel reconocido: " + sinNivel
                + "\n\nTotal de rutinas activas: " + totalRutinas,
                "Análisis de niveles",
                JOptionPane.INFORMATION_MESSAGE);

    } catch (java.sql.SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error al analizar niveles: " + ex.getMessage());
    } finally {
        DB.Database.desconectar(conexion);
    }
}
    
    private void calcularCargaEntrenamiento() {
    if (idEntrenadorSeleccionado == 0) {
        JOptionPane.showMessageDialog(this, "Primero selecciona un entrenador.");
        return;
    }

    String sql = "SELECT COUNT(DISTINCT r.idRutina) AS rutinas, "
            + "COUNT(re.idEjercicio) AS ejercicios, "
            + "COALESCE(SUM(e.series), 0) AS totalSeries, "
            + "COALESCE(SUM(e.series * e.repeticiones), 0) AS totalRepeticiones "
            + "FROM rutina r "
            + "LEFT JOIN rutina_ejercicio re ON r.idRutina = re.idRutina "
            + "LEFT JOIN ejercicio e ON re.idEjercicio = e.idEjercicio AND e.estado = 1 "
            + "WHERE r.idEntrenador = ? AND r.estado = 1";

    java.sql.Connection conexion = DB.Database.conectar();

    if (conexion == null) {
        JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
        return;
    }

    try (java.sql.PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setInt(1, idEntrenadorSeleccionado);

        try (java.sql.ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int rutinas = rs.getInt("rutinas");
                int ejercicios = rs.getInt("ejercicios");
                int series = rs.getInt("totalSeries");
                int repeticiones = rs.getInt("totalRepeticiones");

                JOptionPane.showMessageDialog(this,
                        "Entrenador: " + txtNombre.getText()
                        + "\nRutinas activas: " + rutinas
                        + "\nEjercicios asignados: " + ejercicios
                        + "\nTotal de series: " + series
                        + "\nTotal de repeticiones: " + repeticiones,
                        "Carga de entrenamiento",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (java.sql.SQLException ex) {
        JOptionPane.showMessageDialog(this,
                "Error al calcular carga: " + ex.getMessage());
    } finally {
        DB.Database.desconectar(conexion);
    }
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
        btnContarRutinas = new javax.swing.JButton();
        btnContarAlumnos = new javax.swing.JButton();
        btnAnalizarNiveles = new javax.swing.JButton();
        btnCalcularCarga = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTelefono.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTelefono.setText("Teléfono");

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        txtTelefono.addActionListener(this::txtTelefonoActionPerformed);

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
        chkEstado.addActionListener(this::chkEstadoActionPerformed);

        lblDocumento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDocumento.setText("Documento");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        btnContarRutinas.setText("Contar rutinas");
        btnContarRutinas.addActionListener(this::btnContarRutinasActionPerformed);

        btnContarAlumnos.setText("Contar alumnos");
        btnContarAlumnos.addActionListener(this::btnContarAlumnosActionPerformed);

        btnAnalizarNiveles.setText("Analizar niveles");
        btnAnalizarNiveles.addActionListener(this::btnAnalizarNivelesActionPerformed);

        btnCalcularCarga.setText("Calcular carga");
        btnCalcularCarga.addActionListener(this::btnCalcularCargaActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNombre)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(lblTelefono)
                            .addComponent(lblEspecialidad)
                            .addComponent(txtNombre)
                            .addComponent(txtEspecialidad))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblDocumento)
                                .addGap(308, 308, 308))
                            .addComponent(txtDocumento)
                            .addComponent(lblCorreo)
                            .addComponent(txtCorreo)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chkEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblTitulo)
                        .addGap(308, 308, 308)))
                .addGap(34, 34, 34))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(184, 184, 184)
                .addComponent(btnContarRutinas)
                .addGap(18, 18, 18)
                .addComponent(btnContarAlumnos)
                .addGap(18, 18, 18)
                .addComponent(btnAnalizarNiveles)
                .addGap(18, 18, 18)
                .addComponent(btnCalcularCarga)
                .addGap(0, 211, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(btnGuardar)
                .addGap(18, 18, 18)
                .addComponent(btnActualizar)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar)
                .addGap(18, 18, 18)
                .addComponent(btnLimpiar)
                .addGap(18, 18, 18)
                .addComponent(btnVolver)
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
                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCorreo)
                            .addComponent(lblTelefono))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblEspecialidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContarRutinas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContarAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnalizarNiveles, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCalcularCarga, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(chkEstado)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
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

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void chkEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkEstadoActionPerformed

    private void btnContarRutinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContarRutinasActionPerformed
        contarRutinasEntrenador();
    }//GEN-LAST:event_btnContarRutinasActionPerformed

    private void btnContarAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContarAlumnosActionPerformed
        contarAlumnosEntrenador();
    }//GEN-LAST:event_btnContarAlumnosActionPerformed

    private void btnAnalizarNivelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarNivelesActionPerformed
        analizarNivelesEntrenador();
    }//GEN-LAST:event_btnAnalizarNivelesActionPerformed

    private void btnCalcularCargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularCargaActionPerformed
        calcularCargaEntrenamiento();
    }//GEN-LAST:event_btnCalcularCargaActionPerformed

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
    private javax.swing.JButton btnAnalizarNiveles;
    private javax.swing.JButton btnCalcularCarga;
    private javax.swing.JButton btnContarAlumnos;
    private javax.swing.JButton btnContarRutinas;
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
