
package Views;

import Controllers.PagoController;
import Controllers.UsuarioController;
import Controllers.MembController;

import Models.Pago;
import Models.Usuario;
import Models.Membresia;

import java.time.LocalDate;
import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Locale;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;

public class DashboardPagosView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DashboardPagosView.class.getName());
    
    private final PagoController pagoController = new PagoController();
    private final UsuarioController usuarioController = new UsuarioController();
    private final MembController membController = new MembController();
    
    public DashboardPagosView() {
        initComponents();
        setSize(741, 439);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cargarDashboard();
    }
    
    private void cargarDashboard() {

    List<Pago> pagos = pagoController.Listar();

    LocalDate hoy = LocalDate.now();

    int activos = 0;
    int inactivos = 0;
    int ingresosMes = 0;

    for (Pago p : pagos) {

        if (p.isEstado()) {

            activos++;

            if (p.getFecha().getMonthValue() == hoy.getMonthValue()
                    && p.getFecha().getYear() == hoy.getYear()) {

                ingresosMes += p.getValor();
            }

        } else {
            inactivos++;
        }
    }

    NumberFormat formato =
            NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    lblTotalIngresos.setText(formato.format(ingresosMes));

    lblPagosRealizados.setText(String.valueOf(activos));

    lblPagosInactivos.setText(String.valueOf(inactivos));

    Map<Integer, String> usuarios = new HashMap<>();

    for (Usuario u : usuarioController.Listar()) {
        usuarios.put(u.getIdUsuario(), u.getNombre());
    }

    Map<Integer, String> membresias = new HashMap<>();

    for (Membresia m : membController.Listar()) {
        membresias.put(m.getIdMemb(), m.getNombre());
    }

    List<Pago> recientes = new ArrayList<>(pagos);

    recientes.sort(
            Comparator.comparing(Pago::getFecha).reversed()
    );

    DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Fecha", "Cliente", "Membresía", "Valor"}, 0
    ) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };

    for (int i = 0; i < Math.min(5, recientes.size()); i++) {

        Pago p = recientes.get(i);

        modelo.addRow(new Object[]{
            p.getFecha(),
            usuarios.getOrDefault(p.getIdUsuario(), "Desconocido"),
            membresias.getOrDefault(p.getIdMembresia(), "Desconocida"),
            formato.format(p.getValor())
        });
    }

    tblUltimosPagos.setModel(modelo);
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlIngresos = new javax.swing.JPanel();
        lblTotalIngresos = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pnlPendientes = new javax.swing.JPanel();
        lblPagosInactivos = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pnlRealizados = new javax.swing.JPanel();
        lblPagosRealizados = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUltimosPagos = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel1.setText("Resumen de Pagos");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel2.setText("Información financiera del gimnasio");

        lblTotalIngresos.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblTotalIngresos.setText("Ingresos del mes: 0");

        jLabel4.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel4.setText("Ingresos del mes:");

        javax.swing.GroupLayout pnlIngresosLayout = new javax.swing.GroupLayout(pnlIngresos);
        pnlIngresos.setLayout(pnlIngresosLayout);
        pnlIngresosLayout.setHorizontalGroup(
            pnlIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIngresosLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalIngresos))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        pnlIngresosLayout.setVerticalGroup(
            pnlIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlIngresosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalIngresos)
                .addGap(24, 24, 24))
        );

        lblPagosInactivos.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblPagosInactivos.setText("Pagos Inactivos: 0");

        jLabel5.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel5.setText("Pagos inactivos:");

        javax.swing.GroupLayout pnlPendientesLayout = new javax.swing.GroupLayout(pnlPendientes);
        pnlPendientes.setLayout(pnlPendientesLayout);
        pnlPendientesLayout.setHorizontalGroup(
            pnlPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPendientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPagosInactivos))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        pnlPendientesLayout.setVerticalGroup(
            pnlPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPendientesLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPagosInactivos)
                .addGap(27, 27, 27))
        );

        lblPagosRealizados.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        lblPagosRealizados.setText("Pagos Activos: 0");

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel3.setText("Pagos Activos:");

        javax.swing.GroupLayout pnlRealizadosLayout = new javax.swing.GroupLayout(pnlRealizados);
        pnlRealizados.setLayout(pnlRealizadosLayout);
        pnlRealizadosLayout.setHorizontalGroup(
            pnlRealizadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRealizadosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlRealizadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRealizadosLayout.createSequentialGroup()
                        .addComponent(lblPagosRealizados)
                        .addGap(0, 96, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlRealizadosLayout.setVerticalGroup(
            pnlRealizadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRealizadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPagosRealizados)
                .addGap(23, 23, 23))
        );

        jLabel6.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jLabel6.setText("Ultimos Pagos");

        tblUltimosPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha", "Cliente", "Membresia", "Valor"
            }
        ));
        jScrollPane2.setViewportView(tblUltimosPagos);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVolver)
                        .addGap(165, 165, 165)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel1))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(pnlRealizados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addComponent(pnlPendientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(layout.createSequentialGroup()
                .addGap(323, 323, 323)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlRealizados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlIngresos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPendientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

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

        java.awt.EventQueue.invokeLater(() -> new DashboardPagosView().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblPagosInactivos;
    private javax.swing.JLabel lblPagosRealizados;
    private javax.swing.JLabel lblTotalIngresos;
    private javax.swing.JPanel pnlIngresos;
    private javax.swing.JPanel pnlPendientes;
    private javax.swing.JPanel pnlRealizados;
    private javax.swing.JTable tblUltimosPagos;
    // End of variables declaration//GEN-END:variables
}
