package Views;

import Controllers.RutinaController;
import Models.Rutina;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Controllers.EntrenadorController;
import Models.Entrenador;
import Controllers.RutinaEjercicioController;
import Controllers.EjercicioController;
import Models.Ejercicio;

public class rutinaView extends javax.swing.JFrame {

    private final RutinaController rutinaController = new RutinaController();
    private final EntrenadorController entrenadorController = new EntrenadorController();
    private final RutinaEjercicioController rutinaEjercicioController
            = new RutinaEjercicioController();

    private final EjercicioController ejercicioController
            = new EjercicioController();

    private final javax.swing.JComboBox<String> cmbEjercicio
            = new javax.swing.JComboBox<>();

    private final javax.swing.JLabel lblEjercicio
            = new javax.swing.JLabel("Ejercicio");

    private final javax.swing.JButton btnAsignarEjercicio
            = new javax.swing.JButton("Asignar ejercicio");
    private final javax.swing.JButton btnVerEjercicios
            = new javax.swing.JButton("Ver ejercicios");
    private final javax.swing.JButton btnQuitarEjercicio
            = new javax.swing.JButton("Quitar ejercicio");

    private final javax.swing.JComboBox<String> cmbEntrenador = new javax.swing.JComboBox<>();
    private final javax.swing.JLabel lblEntrenador = new javax.swing.JLabel("Entrenador");
    private int idRutinaSeleccionada = 0;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(rutinaView.class.getName());

    public rutinaView() {
        initComponents();
        setLocationRelativeTo(null);
        cargarRutinas();
        cargarEntrenadores();
        cargarEjerciciosDisponibles();
    }

    private void cargarRutinas() {
        DefaultTableModel modelo = (DefaultTableModel) tblRutinas.getModel();
        modelo.setRowCount(0);

        try {
            for (Rutina rutina : rutinaController.Listar()) {
                modelo.addRow(new Object[]{
                    rutina.getId(),
                    rutina.getNombre(),
                    rutina.getObjetivo(),
                    rutina.getNivel(),
                    rutina.getDescripcion(),
                    rutina.isEstado() ? "Activo" : "Inactivo"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar rutinas: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarEntrenadores() {
        cmbEntrenador.removeAllItems();
        cmbEntrenador.addItem("Seleccione un entrenador");

        try {
            for (Entrenador entrenador : entrenadorController.Listar()) {
                cmbEntrenador.addItem(
                        entrenador.getIdEntrenador() + " - " + entrenador.getNombre()
                );
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar entrenadores: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cargarEjerciciosDisponibles() {
        cmbEjercicio.removeAllItems();
        cmbEjercicio.addItem("Seleccione un ejercicio");

        try {
            for (Ejercicio ejercicio : ejercicioController.listar()) {
                if (ejercicio.isEstado()) {
                    cmbEjercicio.addItem(
                            ejercicio.getId() + " - " + ejercicio.getNombre()
                    );
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar ejercicios: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void mostrarEjerciciosAsignados() {

        if (idRutinaSeleccionada <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Primero debes seleccionar una rutina."
            );
            return;
        }

        java.util.ArrayList<Integer> idsEjercicios
                = rutinaEjercicioController.obtenerEjercicios(idRutinaSeleccionada);

        if (idsEjercicios.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Esta rutina no tiene ejercicios asignados."
            );
            return;
        }

        StringBuilder mensaje = new StringBuilder();

        mensaje.append("Ejercicios de la rutina ")
                .append(idRutinaSeleccionada)
                .append(":\n\n");

        for (Integer idEjercicio : idsEjercicios) {

            Ejercicio ejercicio
                    = ejercicioController.consultarPorId(idEjercicio);

            if (ejercicio != null) {
                mensaje.append("ID: ")
                        .append(ejercicio.getId())
                        .append(" - ")
                        .append(ejercicio.getNombre())
                        .append("\n");
            }
        }

        JOptionPane.showMessageDialog(
                this,
                mensaje.toString(),
                "Ejercicios asignados",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private Integer obtenerIdEntrenadorSeleccionado() {
        int indice = cmbEntrenador.getSelectedIndex();

        if (indice <= 0) {
            return null;
        }

        String seleccionado = cmbEntrenador.getSelectedItem().toString();
        String id = seleccionado.split(" - ", 2)[0];

        return Integer.valueOf(id);
    }

    private void seleccionarEntrenador(Integer idEntrenador) {
        cmbEntrenador.setSelectedIndex(0);

        if (idEntrenador == null) {
            return;
        }

        for (int i = 1; i < cmbEntrenador.getItemCount(); i++) {
            String opcion = cmbEntrenador.getItemAt(i);
            String id = opcion.split(" - ", 2)[0];

            if (Integer.parseInt(id) == idEntrenador) {
                cmbEntrenador.setSelectedIndex(i);
                return;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblObjetivo = new javax.swing.JLabel();
        txtObjetivo = new javax.swing.JTextField();
        lblNivel = new javax.swing.JLabel();
        cmbNivel = new javax.swing.JComboBox<>();
        cmbNivel.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"Principiante", "Intermedio", "Avanzado"}
        ));
        lblDescripcion = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        chkEstado = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRutinas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setText("Gestión de Rutinas");

        lblId.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblId.setText("ID");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblNombre.setText("Nombre");

        lblObjetivo.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblObjetivo.setText("Objetivo");

        lblNivel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblNivel.setText("Nivel");

        lblDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 12));
        lblDescripcion.setText("Descripción");

        chkEstado.setFont(new java.awt.Font("Segoe UI", 1, 12));
        chkEstado.setSelected(true);
        chkEstado.setText("Activo");
        lblEjercicio.setFont(new java.awt.Font("Segoe UI", 1, 12));
        cmbEjercicio.setPreferredSize(new java.awt.Dimension(337, 32));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        btnAsignarEjercicio.addActionListener(this::btnAsignarEjercicioActionPerformed);
        btnVerEjercicios.addActionListener(evt -> mostrarEjerciciosAsignados());
        btnQuitarEjercicio.addActionListener(this::btnQuitarEjercicioActionPerformed);

        tblRutinas.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null}
                },
                new String[]{
                    "ID", "Nombre", "Objetivo", "Nivel", "Descripción", "Estado"
                }
        ));
        tblRutinas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRutinasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblRutinas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTitulo)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblId)
                                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblObjetivo)
                                                        .addComponent(txtObjetivo, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblDescripcion)
                                                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblEntrenador)
                                                        .addComponent(cmbEntrenador, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblNombre)
                                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblNivel)
                                                        .addComponent(cmbNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblEjercicio)
                                                        .addComponent(cmbEjercicio, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(chkEstado)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnGuardar)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnActualizar)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnEliminar)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnBuscar)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnLimpiar))
                                        .addComponent(btnVolver)
                                        .addComponent(btnAsignarEjercicio)
                                        .addComponent(btnVerEjercicios)
                                        .addComponent(btnQuitarEjercicio)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitulo)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblId)
                                .addComponent(lblNombre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblObjetivo)
                                .addComponent(lblNivel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtObjetivo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblEntrenador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbEntrenador, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblEjercicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbEjercicio, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(chkEstado)
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnGuardar)
                                .addComponent(btnActualizar)
                                .addComponent(btnEliminar)
                                .addComponent(btnBuscar)
                                .addComponent(btnLimpiar))
                        .addGap(10, 10, 10)
                        .addComponent(btnVolver)
                        .addGap(12, 12, 12)
                        .addComponent(btnAsignarEjercicio)
                        .addGap(10, 10, 10)
                        .addComponent(btnVerEjercicios)
                        .addGap(10, 10, 10)
                        .addComponent(btnQuitarEjercicio)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pack();
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de la rutina es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocusInWindow();
            return;
        }

        try {
            int id = txtId.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String objetivo = txtObjetivo.getText().trim();
            String nivel = cmbNivel.getSelectedItem().toString();
            String descripcion = txtDescripcion.getText().trim();
            boolean estado = chkEstado.isSelected();

            Integer idEntrenador = obtenerIdEntrenadorSeleccionado();

            Rutina nuevaRutina = new Rutina(
                    id, nombre, objetivo, nivel, descripcion, estado, idEntrenador
            );
            boolean guardado = rutinaController.Guardar(nuevaRutina);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Rutina registrada correctamente.");
                txtId.setText("");
                txtNombre.setText("");
                txtObjetivo.setText("");
                cmbNivel.setSelectedIndex(0);
                txtDescripcion.setText("");
                chkEstado.setSelected(true);
                cargarRutinas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar la rutina.", "Validación", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.", "Error numérico", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tblRutinasMouseClicked(java.awt.event.MouseEvent evt) {
        int fila = tblRutinas.getSelectedRow();

        if (fila >= 0) {
            int filaModelo = tblRutinas.convertRowIndexToModel(fila);

            idRutinaSeleccionada = Integer.parseInt(
                    tblRutinas.getModel().getValueAt(filaModelo, 0).toString()
            );

            Rutina rutina = rutinaController.ConsultarPorId(idRutinaSeleccionada);

            if (rutina != null) {
                seleccionarEntrenador(rutina.getIdEntrenador());
            }
            idRutinaSeleccionada = Integer.parseInt(
                    tblRutinas.getModel().getValueAt(filaModelo, 0).toString()
            );

            txtId.setText(String.valueOf(idRutinaSeleccionada));
            txtNombre.setText(
                    tblRutinas.getModel().getValueAt(filaModelo, 1).toString()
            );
            txtObjetivo.setText(
                    tblRutinas.getModel().getValueAt(filaModelo, 2).toString()
            );
            cmbNivel.setSelectedItem(
                    tblRutinas.getModel().getValueAt(filaModelo, 3).toString()
            );
            txtDescripcion.setText(
                    tblRutinas.getModel().getValueAt(filaModelo, 4).toString()
            );
            chkEstado.setSelected(
                    "Activo".equals(
                            tblRutinas.getModel().getValueAt(filaModelo, 5).toString()
                    )
            );
        }
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idRutinaSeleccionada == 0 && txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debes seleccionar una rutina de la tabla o ingresar un ID.");
            return;
        }

        try {
            int id = idRutinaSeleccionada != 0 ? idRutinaSeleccionada : Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String objetivo = txtObjetivo.getText().trim();
            String nivel = cmbNivel.getSelectedItem().toString();
            String descripcion = txtDescripcion.getText().trim();
            boolean estado = chkEstado.isSelected();

            Integer idEntrenador = obtenerIdEntrenadorSeleccionado();

            Rutina rutinaActualizada = new Rutina(
                    id, nombre, objetivo, nivel, descripcion, estado, idEntrenador
            );
            boolean actualizado = rutinaController.Actualizar(rutinaActualizada);

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Rutina actualizada correctamente.");
                idRutinaSeleccionada = 0;
                txtId.setText("");
                txtNombre.setText("");
                txtObjetivo.setText("");
                cmbNivel.setSelectedIndex(0);
                txtDescripcion.setText("");
                chkEstado.setSelected(true);
                cargarRutinas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar la rutina.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idRutinaSeleccionada == 0 && txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debes seleccionar una rutina de la tabla o ingresar un ID.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar esta rutina?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int id = idRutinaSeleccionada != 0 ? idRutinaSeleccionada : Integer.parseInt(txtId.getText().trim());
            boolean eliminado = rutinaController.Eliminar(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Rutina eliminada correctamente.");
                idRutinaSeleccionada = 0;
                txtId.setText("");
                txtNombre.setText("");
                txtObjetivo.setText("");
                cmbNivel.setSelectedIndex(0);
                txtDescripcion.setText("");
                chkEstado.setSelected(true);
                cargarRutinas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la rutina.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID para buscar.");
                txtId.requestFocusInWindow();
                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());
            Rutina rutina = rutinaController.ConsultarPorId(id);

            if (rutina != null) {
                idRutinaSeleccionada = rutina.getId();
                txtNombre.setText(rutina.getNombre());
                txtObjetivo.setText(rutina.getObjetivo());
                cmbNivel.setSelectedIndex(0);
                txtDescripcion.setText(rutina.getDescripcion());
                chkEstado.setSelected(rutina.isEstado());
                seleccionarEntrenador(rutina.getIdEntrenador());

                JOptionPane.showMessageDialog(this, "¡Rutina encontrada!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ninguna rutina con el ID: " + id);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número entero válido.");
        }
    }

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        txtId.setText("");
        txtNombre.setText("");
        txtObjetivo.setText("");
        cmbNivel.setSelectedIndex(0);
        txtDescripcion.setText("");
        chkEstado.setSelected(true);
        idRutinaSeleccionada = 0;
        tblRutinas.clearSelection();
        txtId.requestFocusInWindow();
        cmbEntrenador.setSelectedIndex(0);
    }

    private void btnAsignarEjercicioActionPerformed(java.awt.event.ActionEvent evt) {

        if (idRutinaSeleccionada <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Primero debes seleccionar una rutina de la tabla."
            );
            return;
        }

        if (cmbEjercicio.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un ejercicio para asignar."
            );
            return;
        }

        try {
            String seleccionado = cmbEjercicio.getSelectedItem().toString();
            int idEjercicio = Integer.parseInt(
                    seleccionado.split(" - ", 2)[0]
            );

            boolean asignado = rutinaEjercicioController.asignarEjercicio(
                    idRutinaSeleccionada,
                    idEjercicio
            );

            if (asignado) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ejercicio asignado correctamente a la rutina."
                );
                cmbEjercicio.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo asignar el ejercicio. Verifica que no esté asignado previamente."
                );
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al asignar ejercicio: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void btnQuitarEjercicioActionPerformed(java.awt.event.ActionEvent evt) {

        if (idRutinaSeleccionada <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Primero debes seleccionar una rutina de la tabla."
            );
            return;
        }

        if (cmbEjercicio.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona el ejercicio que deseas quitar."
            );
            return;
        }

        try {
            String seleccionado = cmbEjercicio.getSelectedItem().toString();

            int idEjercicio = Integer.parseInt(
                    seleccionado.split(" - ", 2)[0]
            );

            java.util.ArrayList<Integer> ejerciciosAsignados
                    = rutinaEjercicioController.obtenerEjercicios(idRutinaSeleccionada);

            if (!ejerciciosAsignados.contains(idEjercicio)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Este ejercicio no está asignado a la rutina seleccionada."
                );
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas quitar este ejercicio de la rutina?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            boolean quitado = rutinaEjercicioController.quitarEjercicio(
                    idRutinaSeleccionada,
                    idEjercicio
            );

            if (quitado) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ejercicio quitado correctamente de la rutina."
                );
                cmbEjercicio.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo quitar el ejercicio."
                );
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al quitar ejercicio: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new rutinaView().setVisible(true));
    }

    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObjetivo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblRutinas;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtId;
    private javax.swing.JComboBox<String> cmbNivel;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtObjetivo;
}
