
package Views;

import Controllers.PagoController;
import Controllers.UsuarioController;
import Controllers.MembController;

import Models.Pago;
import Models.PagoSuplemento;
import Models.Usuario;
import Models.Membresia;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.util.List;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.Locale;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Font;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.Locale;

public class ComprobantePagoView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ComprobantePagoView.class.getName());
    
    private PagoController pagoController = new PagoController();
    private UsuarioController usuarioController = new UsuarioController();
    private MembController membController = new MembController();

    private int idPagoActual;
    private Pago pagoActual;

    private List<PagoSuplemento> suplementosActuales;
    
    public ComprobantePagoView(int idPago) {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.idPagoActual = idPago;

        cargarComprobante();
    }
    
    private void cargarComprobante() {

    // Consultar el pago
    pagoActual = pagoController.consultarPorId(idPagoActual);

    if (pagoActual == null) {

        JOptionPane.showMessageDialog(
                this,
                "No se encontró el pago seleccionado."
        );

        dispose();
        return;
    }

    String nombreCliente = "Desconocido";

    for (Usuario u : usuarioController.Listar()) {

        if (u.getIdUsuario() == pagoActual.getIdUsuario()) {
            nombreCliente = u.getNombre();
            break;
        }
    }

    String nombreMembresia = "Desconocida";

    for (Membresia m : membController.Listar()) {

        if (m.getIdMemb() == pagoActual.getIdMembresia()) {
            nombreMembresia = m.getNombre();
            break;
        }
    }

    suplementosActuales =
            pagoController.ListarSuplementos(idPagoActual);

    if (suplementosActuales == null) {
        suplementosActuales = new ArrayList<>();
    }

    NumberFormat formato =
            NumberFormat.getCurrencyInstance(
                    new Locale("es", "CO")
            );

    lblNoPago.setText(String.valueOf(pagoActual.getIdPago()));

    lblFecha.setText(pagoActual.getFecha().toString());

    lblCliente.setText(nombreCliente);

    lblTotal.setText(
            "Total: " + formato.format(pagoActual.getValor())
    );

    lblMetodoPago.setText(pagoActual.getMetodoPago());

    lblEstado.setText(
            pagoActual.isEstado() ? "Activo" : "Inactivo"
    );

    DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Concepto", "Cantidad", "Valor"}, 0
    ) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

        double totalSuplementos = 0;

        for (PagoSuplemento ps : suplementosActuales) {
            totalSuplementos += ps.getSubtotal();
        }

        double valorMembresia =
                pagoActual.getValor() - totalSuplementos;

        modelo.addRow(new Object[]{
            nombreMembresia,
            1,
            formato.format(valorMembresia)
        });

        for (PagoSuplemento ps : suplementosActuales) {

            modelo.addRow(new Object[]{
                ps.getNombre(),
                ps.getCantidad(),
                formato.format(ps.getSubtotal())
            });
        }
    
        tblDetalle.setModel(modelo);
    }
    
    private void descargarPDF() {

    if (pagoActual == null) {
        JOptionPane.showMessageDialog(
                this,
                "No hay información del pago para generar el comprobante."
        );
        return;
    }

    JFileChooser selector = new JFileChooser();

    selector.setDialogTitle("Guardar comprobante de pago");

    selector.setSelectedFile(
            new File("Comprobante_Pago_"
                    + pagoActual.getIdPago() + ".pdf")
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

    Document documento = new Document();

    try (FileOutputStream salida = new FileOutputStream(archivo)) {

        PdfWriter.getInstance(documento, salida);

        documento.open();

        Font titulo = new Font(
                Font.HELVETICA, 18, Font.BOLD
        );

        Font subtitulo = new Font(
                Font.HELVETICA, 12, Font.BOLD
        );

        Font normal = new Font(
                Font.HELVETICA, 10, Font.NORMAL
        );

        Paragraph encabezado = new Paragraph(
                "Gestion Gimnasio Los brochachos", titulo
        );

        encabezado.setAlignment(Element.ALIGN_CENTER);
        documento.add(encabezado);

        documento.add(new Paragraph(" "));

        Paragraph comprobante = new Paragraph(
                "COMPROBANTE DE PAGO", subtitulo
        );

        comprobante.setAlignment(Element.ALIGN_CENTER);
        documento.add(comprobante);

        documento.add(new Paragraph(" "));

        documento.add(new Paragraph(
                "Numero de pago: " + pagoActual.getIdPago(), normal
        ));

        documento.add(new Paragraph(
                "Fecha: " + pagoActual.getFecha(), normal
        ));

        documento.add(new Paragraph(
                "Cliente: " + lblCliente.getText(), normal
        ));

        documento.add(new Paragraph(" "));

        documento.add(new Paragraph(
                "DETALLE DEL PAGO", subtitulo
        ));

        documento.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(3);

        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{3, 1, 2});

        tabla.addCell(new PdfPCell(new Phrase("Concepto")));
        tabla.addCell(new PdfPCell(new Phrase("Cantidad")));
        tabla.addCell(new PdfPCell(new Phrase("Valor")));

        NumberFormat formato =
                NumberFormat.getCurrencyInstance(
                        new Locale("es", "CO")
                );

        double totalSuplementos = 0;

        if (suplementosActuales != null) {

            for (PagoSuplemento ps : suplementosActuales) {
                totalSuplementos += ps.getSubtotal();
            }
        }

        double valorMembresia =
                pagoActual.getValor() - totalSuplementos;

        tabla.addCell("Membresia");
        tabla.addCell("1");
        tabla.addCell(formato.format(valorMembresia));

        if (suplementosActuales != null) {

            for (PagoSuplemento ps : suplementosActuales) {

                tabla.addCell(ps.getNombre());

                tabla.addCell(
                        String.valueOf(ps.getCantidad())
                );

                tabla.addCell(
                        formato.format(ps.getSubtotal())
                );
            }
        }

        documento.add(tabla);

        documento.add(new Paragraph(" "));

        Paragraph total = new Paragraph(
                "TOTAL: " + formato.format(pagoActual.getValor()),
                subtitulo
        );

        total.setAlignment(Element.ALIGN_RIGHT);

        documento.add(total);

        documento.add(new Paragraph(" "));

        documento.add(new Paragraph(
                "Metodo de pago: " + pagoActual.getMetodoPago(),
                normal
        ));

        documento.add(new Paragraph(
                "Estado: " +
                (pagoActual.isEstado() ? "Activo" : "Inactivo"),
                normal
        ));

        documento.add(new Paragraph(" "));

        Paragraph gracias = new Paragraph(
                "Gracias por elegirnos."
        );

        gracias.setAlignment(Element.ALIGN_CENTER);

        documento.add(gracias);

        documento.close();

        JOptionPane.showMessageDialog(
                this,
                "¡Comprobante PDF generado correctamente!\n\n"
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
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblMetodoPago = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        pnlCliente = new javax.swing.JPanel();
        pnlDetalle = new javax.swing.JPanel();
        pnlNoPago = new javax.swing.JPanel();
        lblNoPago = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();
        btnVoklver = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        pnlFecha = new javax.swing.JPanel();
        lblFecha = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblMetodoPago1 = new javax.swing.JLabel();
        lblEstado1 = new javax.swing.JLabel();
        lblCliente1 = new javax.swing.JLabel();
        lblFecha1 = new javax.swing.JLabel();
        lblNoPago1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel1.setText("Datos del Cliente");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel2.setText("Gestion Gimnasio Los brochachos");

        jLabel5.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel5.setText("Comprobante de pago");

        lblCliente.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblCliente.setText("Cliente");

        jLabel7.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel7.setText("Detalle del pago");

        lblMetodoPago.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblMetodoPago.setText("Metodo de pago");

        jLabel10.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel10.setText("Gracias por elegirnos.");

        lblEstado.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblEstado.setText("Estado");

        javax.swing.GroupLayout pnlClienteLayout = new javax.swing.GroupLayout(pnlCliente);
        pnlCliente.setLayout(pnlClienteLayout);
        pnlClienteLayout.setHorizontalGroup(
            pnlClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        pnlClienteLayout.setVerticalGroup(
            pnlClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlDetalleLayout = new javax.swing.GroupLayout(pnlDetalle);
        pnlDetalle.setLayout(pnlDetalleLayout);
        pnlDetalleLayout.setHorizontalGroup(
            pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 123, Short.MAX_VALUE)
        );
        pnlDetalleLayout.setVerticalGroup(
            pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        lblNoPago.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblNoPago.setText("N.º de pago");

        javax.swing.GroupLayout pnlNoPagoLayout = new javax.swing.GroupLayout(pnlNoPago);
        pnlNoPago.setLayout(pnlNoPagoLayout);
        pnlNoPagoLayout.setHorizontalGroup(
            pnlNoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNoPagoLayout.createSequentialGroup()
                .addComponent(lblNoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 38, Short.MAX_VALUE))
        );
        pnlNoPagoLayout.setVerticalGroup(
            pnlNoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNoPagoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNoPago)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnImprimir.setText("Imprimir Recibo");
        btnImprimir.addActionListener(this::btnImprimirActionPerformed);

        btnVoklver.setText("Volver");
        btnVoklver.addActionListener(this::btnVoklverActionPerformed);

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDetalle);

        lblFecha.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblFecha.setText("Fecha");

        javax.swing.GroupLayout pnlFechaLayout = new javax.swing.GroupLayout(pnlFecha);
        pnlFecha.setLayout(pnlFechaLayout);
        pnlFechaLayout.setHorizontalGroup(
            pnlFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFechaLayout.createSequentialGroup()
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );
        pnlFechaLayout.setVerticalGroup(
            pnlFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFechaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFecha)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        lblTotal.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblTotal.setText("Total:");

        lblMetodoPago1.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblMetodoPago1.setText("Metodo de pago");

        lblEstado1.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblEstado1.setText("Estado");

        lblCliente1.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblCliente1.setText("Cliente");

        lblFecha1.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblFecha1.setText("Fecha");

        lblNoPago1.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblNoPago1.setText("N.º de pago");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(btnVoklver, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 6, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pnlCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblMetodoPago1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(89, 89, 89))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(pnlNoPago, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                        .addComponent(lblEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(44, 44, 44)
                                                        .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(lblNoPago1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(pnlDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pnlFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(26, 26, 26)))
                        .addGap(153, 153, 153))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(71, 71, 71))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFecha1)
                    .addComponent(lblNoPago1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlNoPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(pnlCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotal)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMetodoPago)
                            .addComponent(lblMetodoPago1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pnlDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVoklver)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        descargarPDF();
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnVoklverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoklverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVoklverActionPerformed

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

        java.awt.EventQueue.invokeLater(() -> new ComprobantePagoView(1).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnVoklver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblCliente1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblEstado1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFecha1;
    private javax.swing.JLabel lblMetodoPago;
    private javax.swing.JLabel lblMetodoPago1;
    private javax.swing.JLabel lblNoPago;
    private javax.swing.JLabel lblNoPago1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnlCliente;
    private javax.swing.JPanel pnlDetalle;
    private javax.swing.JPanel pnlFecha;
    private javax.swing.JPanel pnlNoPago;
    private javax.swing.JTable tblDetalle;
    // End of variables declaration//GEN-END:variables
}
