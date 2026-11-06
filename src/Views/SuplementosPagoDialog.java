package Views;

import Controllers.SuplementoController;
import Models.PagoSuplemento;
import Models.Suplemento;
import java.awt.Component;
import java.awt.Frame;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class SuplementosPagoDialog extends javax.swing.JDialog {

    private static final NumberFormat PESOS = NumberFormat.getIntegerInstance(Locale.forLanguageTag("es-CO"));
    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(SuplementosPagoDialog.class.getName());
    
    private final boolean soloLectura;
    private final List<PagoSuplemento> lineas = new ArrayList<>();
    private final Map<Integer, Suplemento> catalogo = new LinkedHashMap<>();
    private List<PagoSuplemento> resultado = null;   // null = el usuario canceló
    private DefaultTableModel modelo;

    public SuplementosPagoDialog(Frame dueno, List<PagoSuplemento> actuales, boolean soloLectura) {
        super(dueno, true);
        this.soloLectura = soloLectura;
        initComponents();
        setTitle(soloLectura ? "Suplementos del pago" : "Agregar suplementos");

        for (PagoSuplemento p : actuales) {
            lineas.add(new PagoSuplemento(p.getIdPago(), p.getIdSu(), p.getCantidad(),
                    p.getPrecioUnitario(), p.getNombre()));
        }
        if (!soloLectura) {
            // Solo se pueden vender suplementos activos y con stock
            for (Suplemento s : new SuplementoController().Listar()) {
                if (s.getEstado() && s.getStock() > 0) {
                    catalogo.put(s.getIdSu(), s);
                }
            }
        }

        configurarTabla();
        configurarModo();
        refrescar();
        setLocationRelativeTo(dueno);
    }

    private void configurarTabla() {
        modelo = new DefaultTableModel(new Object[]{"Suplemento", "Cantidad", "Precio unitario", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tblSuplementos.setModel(modelo);
    }

    private void configurarModo() {
        boolean edicion = !soloLectura;

        lblSuplemento.setVisible(edicion);
        cmbSuplemento.setVisible(edicion);
        lblCantidad.setVisible(edicion);
        spnCantidad.setVisible(edicion);
        btnAgregar.setVisible(edicion);
        lblAviso.setVisible(false);
        
        btnQuitar.setVisible(edicion);
        btnCancelar.setVisible(edicion);
        btnAceptar.setVisible(edicion);
        btnCerrar.setVisible(soloLectura);

        if (soloLectura) {
            return;
        }

        spnCantidad.setModel(new SpinnerNumberModel(1, 1, 999, 1));
        cmbSuplemento.removeAllItems();
        for (Suplemento s : catalogo.values()) {
            cmbSuplemento.addItem(s);
        }
        cmbSuplemento.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object valor, int indice,
                    boolean seleccionado, boolean foco) {
                super.getListCellRendererComponent(list, valor, indice, seleccionado, foco);
                if (valor instanceof Suplemento) {
                    Suplemento s = (Suplemento) valor;
                    setText(s.getNombre() + " (" + s.getMarca() + ") - $" + PESOS.format(s.getPrecio())
                            + " - stock " + s.getStock());
                }
                return this;
            }
        });

        boolean hayCatalogo = !catalogo.isEmpty();
        btnAgregar.setEnabled(hayCatalogo);
        lblAviso.setVisible(!hayCatalogo);
    }

    private void agregarSeleccionado() {
        Suplemento s = (Suplemento) cmbSuplemento.getSelectedItem();
        agregarLinea(s, (Integer) spnCantidad.getValue());
    }
    
    public boolean agregarLinea(Suplemento s, int cantidad) {
        if (s == null || cantidad <= 0) {
            return false;
        }
        PagoSuplemento existente = null;
        for (PagoSuplemento l : lineas) {
            if (l.getIdSu() == s.getIdSu()) {
                existente = l;
            }
        }
        int nueva = (existente == null ? 0 : existente.getCantidad()) + cantidad;
        if (nueva > s.getStock()) {
            JOptionPane.showMessageDialog(this,
                    "Solo hay " + s.getStock() + " unidades de «" + s.getNombre() + "».",
                    "Stock insuficiente", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (existente == null) {
            lineas.add(new PagoSuplemento(0, s.getIdSu(), cantidad, s.getPrecio(), s.getNombre()));
        } else {
            existente.setCantidad(nueva);
        }
        refrescar();
        return true;
    }

    private void quitarSeleccionado() {
        int fila = tblSuplementos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un suplemento de la tabla.");
            return;
        }
        lineas.remove(fila);
        refrescar();
    }

    private void refrescar() {
        modelo.setRowCount(0);
        double total = 0;
        for (PagoSuplemento l : lineas) {
            modelo.addRow(new Object[]{
                l.getNombre(),
                l.getCantidad(),
                "$" + PESOS.format(l.getPrecioUnitario()),
                "$" + PESOS.format(l.getSubtotal())
            });
            total += l.getSubtotal();
        }
        lblTotal.setText("Total suplementos: $" + PESOS.format(total));
    }

    public void aceptar() {
        resultado = new ArrayList<>(lineas);
        dispose();
    }

    public void cancelar() {
        resultado = null;
        dispose();
    }

    public List<PagoSuplemento> getResultado() {
        return resultado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbSuplemento = new javax.swing.JComboBox<>();
        lblAviso = new javax.swing.JLabel();
        lblSuplemento = new javax.swing.JLabel();
        spnCantidad = new javax.swing.JSpinner();
        btnAgregar = new javax.swing.JButton();
        lblCantidad = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSuplementos = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cmbSuplemento.addActionListener(this::cmbSuplementoActionPerformed);

        lblAviso.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblAviso.setText("no hay suplementos activos con stock");

        lblSuplemento.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblSuplemento.setText("Suplemento:");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

        lblCantidad.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblCantidad.setText("Cantidad:");

        tblSuplementos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblSuplementos);

        lblTotal.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblTotal.setText("Total suplementos: $0");

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(this::btnCerrarActionPerformed);

        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(this::btnQuitarActionPerformed);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(this::btnAceptarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblSuplemento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSuplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblCantidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAviso))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lblTotal)
                        .addGap(29, 29, 29)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSuplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSuplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAviso, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbSuplementoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSuplementoActionPerformed

    }//GEN-LAST:event_cmbSuplementoActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        agregarSeleccionado();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        aceptar();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        quitarSeleccionado();
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cancelar();
    }//GEN-LAST:event_btnCerrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox<Suplemento> cmbSuplemento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblSuplemento;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JSpinner spnCantidad;
    private javax.swing.JTable tblSuplementos;
    // End of variables declaration//GEN-END:variables
}
