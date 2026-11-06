package Views;

import Controllers.MembController;
import Controllers.PagoController;
import Controllers.UsuarioController;
import Models.Membresia;
import Models.Pago;
import Models.Usuario;
import Models.PagoSuplemento;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Font;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;

public class PagoListaView extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PagoListaView.class.getName());

    private final PagoController pagoController = new PagoController();
    private final UsuarioController usuarioController = new UsuarioController();
    private final MembController membController = new MembController();

    public PagoListaView() {
        initComponents();
        setSize(741, 439);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cargarPagos();
    }
    
    private void abrir(javax.swing.JFrame ventana) {
        setVisible(false);
        ventana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                PagoListaView.this.setVisible(true);
            }
        });
        ventana.setVisible(true);
    }

    private void cargarPagos() {
    Map<Integer, String> nombresUsuario = new HashMap<>();
    for (Usuario u : usuarioController.Listar()) {
        nombresUsuario.put(u.getIdUsuario(), u.getNombre());
    }
    Map<Integer, String> nombresMembresia = new HashMap<>();
    for (Membresia m : membController.Listar()) {
        nombresMembresia.put(m.getIdMemb(), m.getNombre());
    }

    DefaultTableModel modelo = (DefaultTableModel) tblPagos.getModel();
    modelo.setRowCount(0);
    for (Pago p : pagoController.Listar()) {
        modelo.addRow(new Object[]{
            p.getIdPago(),
            p.getFecha(),
            nombresUsuario.getOrDefault(p.getIdUsuario(), "(desconocido)"),
            nombresMembresia.getOrDefault(p.getIdMembresia(), "(desconocida)"),
            resumenSuplementos(p.getIdPago()),
            p.getValor(),
            p.getMetodoPago(),
            p.isEstado() ? "Vigente" : "Anulado"
        });
    }
}

    private String resumenSuplementos(int idPago) {
        List<PagoSuplemento> lista = pagoController.ListarSuplementos(idPago);
        if (lista == null || lista.isEmpty()) {
            return "-";
        }
        StringBuilder sb = new StringBuilder();
        for (PagoSuplemento ps : lista) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(ps.getNombre()).append(" x").append(ps.getCantidad());
        }
        return sb.toString();
    }

    private int idSeleccionado() {
        int fila = tblPagos.getSelectedRow();
        if (fila < 0) {
            return -1;
        }
        int filaModelo = tblPagos.convertRowIndexToModel(fila);
        return Integer.parseInt(tblPagos.getModel().getValueAt(filaModelo, 0).toString());
    }
    
    private void descargarRegistroPDF() {

        if (tblPagos.getRowCount() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay pagos registrados para exportar."
            );

            return;
        }

        JFileChooser selector = new JFileChooser();

        selector.setDialogTitle("Guardar registro de pagos");

        selector.setSelectedFile(
                new File("Registro_Pagos.pdf")
        );

        selector.setFileFilter(
                new FileNameExtensionFilter("Archivos PDF", "pdf")
        );

        int opcion = selector.showSaveDialog(this);

        if (opcion != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();

        if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
            archivo = new File(archivo.getAbsolutePath() + ".pdf");
        }

        if (archivo.exists()) {

            int confirmar = JOptionPane.showConfirmDialog(
                    this,
                    "El archivo ya existe. ¿Deseas reemplazarlo?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmar != JOptionPane.YES_OPTION) {
                return;
            }
        }

        Document documento = new Document(PageSize.A4.rotate());

        try (FileOutputStream salida = new FileOutputStream(archivo)) {

            PdfWriter.getInstance(documento, salida);

            documento.open();

            Font titulo = new Font(
                    Font.HELVETICA, 18, Font.BOLD
            );

            Font subtitulo = new Font(
                    Font.HELVETICA, 11, Font.NORMAL
            );

            Paragraph encabezado = new Paragraph(
                    "GESTION GYM", titulo
            );

            encabezado.setAlignment(Element.ALIGN_CENTER);
            documento.add(encabezado);

            Paragraph nombre = new Paragraph(
                    "REGISTRO DE PAGOS", subtitulo
            );

            nombre.setAlignment(Element.ALIGN_CENTER);
            documento.add(nombre);

            documento.add(new Paragraph(" "));

            documento.add(new Paragraph(
                    "Fecha de generacion: " + LocalDate.now()
            ));

            documento.add(new Paragraph(
                    "Total de registros: " + tblPagos.getRowCount()
            ));

            documento.add(new Paragraph(" "));

            int columnas = tblPagos.getColumnCount();

            PdfPTable tabla = new PdfPTable(columnas);

            tabla.setWidthPercentage(100);

            for (int columna = 0; columna < columnas; columna++) {

                String encabezadoColumna =
                        tblPagos.getColumnName(columna);

                tabla.addCell(encabezadoColumna);
            }

            for (int fila = 0; fila < tblPagos.getRowCount(); fila++) {

                for (int columna = 0; columna < columnas; columna++) {

                    Object valor = tblPagos.getValueAt(fila, columna);

                    tabla.addCell(
                            valor == null ? "" : valor.toString()
                    );
                }
            }

            documento.add(tabla);

            documento.close();

            JOptionPane.showMessageDialog(
                    this,
                    "¡Registro de pagos exportado correctamente!\n\n"
                    + archivo.getAbsolutePath()
            );

        } catch (Exception e) {

            if (documento.isOpen()) {
                documento.close();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Error al generar el PDF: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPagos = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        btnEliminar1 = new javax.swing.JButton();
        btnEliminar2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel1.setText("Lista de Pagos");

        tblPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Fecha", "Usuario", "Membresia", "Suplementos", "Valor", "Método de pago", "Estado"
            }
        ));
        jScrollPane1.setViewportView(tblPagos);

        btnEditar.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnEliminar.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnNuevo.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        btnEliminar1.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        btnEliminar1.setText("Ver Comprobante");
        btnEliminar1.addActionListener(this::btnEliminar1ActionPerformed);

        btnEliminar2.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        btnEliminar2.setText("Ver Resumen");
        btnEliminar2.addActionListener(this::btnEliminar2ActionPerformed);

        jButton1.setText("Descargar registro");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar2)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVolver)
                        .addGap(204, 204, 204)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addGap(9, 9, 9)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEliminar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(47, 47, 47))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        int id = idSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(this, "Primero selecciona un pago de la tabla.");
            return;
        }
        Pago p = pagoController.consultarPorId(id);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Ese pago ya no existe.");
            cargarPagos();
            return;
        }
        new PagoView(p, this::cargarPagos).setVisible(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        int id = idSeleccionado();
        if (id == -1) {
            JOptionPane.showMessageDialog(this, "Primero selecciona un pago de la tabla.");
            return;
        }
        Pago actual = pagoController.consultarPorId(id);
        if (actual != null && !actual.isEstado()) {
            JOptionPane.showMessageDialog(this, "Ese pago ya está anulado.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que quieres anular este pago?\nQuedará registrado como Anulado; no se borra.",
                "Confirmar anulación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        if (pagoController.Eliminar(id)) {
            JOptionPane.showMessageDialog(this, "Pago anulado correctamente.");
            cargarPagos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo anular el pago.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed

        new PagoView(null, this::cargarPagos).setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        int filaSeleccionada = tblPagos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona un pago primero."
            );
            return;
        }

        int filaModelo = tblPagos.convertRowIndexToModel(
                filaSeleccionada
        );

        int idPago = Integer.parseInt(
                tblPagos.getModel()
                        .getValueAt(filaModelo, 0).toString()
        );

        ComprobantePagoView comprobante =
                new ComprobantePagoView(idPago);

        comprobante.setVisible(true);
    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private void btnEliminar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar2ActionPerformed
        abrir(new DashboardPagosView());
    }//GEN-LAST:event_btnEliminar2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        descargarRegistroPDF();
    }//GEN-LAST:event_jButton1ActionPerformed

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

        java.awt.EventQueue.invokeLater(() -> new PagoListaView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnEliminar2;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnVolver;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPagos;
    // End of variables declaration//GEN-END:variables
}
