/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasikasir_rahma;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
import java.util.Date;
/**
 *
 * @author OWNER
 */
public class FormPenjualan extends javax.swing.JFrame {
Connection konek;
PreparedStatement pst, pst2;
ResultSet rst;
int inputstok, inputstok2, inputharga, inputjumlah, kurangistok, tambahstok;
String harga, idproduk, idprodukpenjualan, iddetail, jam, tanggal, sub_total;

    /**
     * Creates new form FormPenjualan
     */
    public FormPenjualan() {
       initComponents() ;
       konek = Koneksi.koneksiDB();
       this.setLocationRelativeTo(null);
       tampilJam();
       detail();
       autonumber();
       penjumlahan();
}
       
       private void simpan(){
        String tgl=txtTanggal.getText();
        String jam=txtJam.getText();
      try {
            String sql="insert into penjualan (PenjualanID,DetailID,TanggalPenjualan,JamPenjualan,TotalHarga) value (?,?,?,?,?)";
            pst=konek.prepareStatement(sql);
            pst.setString(1, txtIdPenjualan.getText());
            pst.setString(2, iddetail);
            pst.setString(3, tgl);
            pst.setString(4, jam);
            pst.setString(5, txtTotal.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Tersimpan");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
    }
       
       private void total(){
    int total, bayar, kembali;
        total= Integer.parseInt(txtBayar.getText());
        bayar= Integer.parseInt(txtTotal.getText());
        kembali=total-bayar;
        String ssub=String.valueOf(kembali);
        txtKembalian.setText(ssub);
    }
       public void clsr(){
    txtJumlah.setText("");
    }
       
       public void cari(){
    try {
        String sql="select * from produk where ProdukID LIKE '%"+txtIdProduk.getText()+"%'";
        pst=konek.prepareStatement(sql);
        rst=pst.executeQuery();
        tblProduk.setModel(DbUtils.resultSetToTableModel(rst));
       } catch (Exception e){ JOptionPane.showMessageDialog(null, e);} 
    }
       
       public void kurangi_stok(){
    int qty;
    qty=Integer.parseInt(txtJumlah.getText());
    kurangistok=inputstok-qty;
    }
       
       private void subtotal(){
    int jumlah, sub;
         jumlah= Integer.parseInt(txtJumlah.getText());
         sub=(jumlah*inputharga);
         sub_total=String.valueOf(sub);     
    }
       
       public void tambah_stok(){
    tambahstok=inputjumlah+inputstok2;
        try {
        String update="update produk set Stok='"+tambahstok+"' where ProdukID='"+idproduk+"'";
        pst2=konek.prepareStatement(update);
        pst2.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);}
    }
       
        public void ambil_stock(){
    try {
    String sql="select * from produk where ProdukID='"+idproduk+"'";
    pst=konek.prepareStatement(sql);
    rst=pst.executeQuery();
    if (rst.next()) {    
    String stok=rst.getString(("Stok"));
    inputstok2= Integer.parseInt(stok);
    }
    }catch (Exception e) {
    JOptionPane.showMessageDialog(null, e);}
    }
        
         public void penjumlahan(){
        int totalBiaya = 0;
        int subtotal;
        DefaultTableModel dataModel = (DefaultTableModel) tblPenjualan.getModel();
        int jumlah = tblPenjualan.getRowCount();
        for (int i=0; i<jumlah; i++){
        subtotal = Integer.parseInt(dataModel.getValueAt(i, 4).toString());
        totalBiaya += subtotal;
        }
        txtTotal.setText(String.valueOf(totalBiaya));
    }
         
         public void autonumber(){
    try{
        String sql = "SELECT MAX(RIGHT(PenjualanID,3)) AS NO FROM penjualan";
        pst=konek.prepareStatement(sql);
        rst=pst.executeQuery();
        while (rst.next()) {
                if (rst.first() == false) {
                    txtIdPenjualan.setText("IDP001");
                } else {
                    rst.last();
                    int auto_id = rst.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int NomorJual = no.length();
                    for (int j = 0; j < 3 - NomorJual; j++) {
                        no = "0" + no;
                    }
                   txtIdPenjualan.setText("IDP" + no);
                }
            }
        rst.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);}
    }
         
          public void detail(){
    try {
        String Kode_detail=txtIdPenjualan.getText();
        String KD="D"+Kode_detail;
        String sql="select * from detailpenjualan where DetailID='"+KD+"'";
        pst=konek.prepareStatement(sql);
        rst=pst.executeQuery();
        tblPenjualan.setModel(DbUtils.resultSetToTableModel(rst));
       } catch (Exception e){ 
           JOptionPane.showMessageDialog(null, e);} 
    }
          
          public void tampilJam(){
    Thread clock=new Thread(){
        public void run(){
            for(;;){
                Calendar cal=Calendar.getInstance();
                SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd");
                txtJam.setText(format.format(cal.getTime()));
                 txtTanggal.setText(format2.format(cal.getTime()));
                
            try { sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(FormPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }
      };
    clock.start();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtIdProduk = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduk = new javax.swing.JTable();
        btnTambah = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtIdPenjualan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPenjualan = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtBayar = new javax.swing.JTextField();
        txtKembalian = new javax.swing.JTextField();
        btnHapus = new javax.swing.JButton();
        btnBayar = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        txtJam = new javax.swing.JTextField();
        txtTanggal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-transaction-40.png"))); // NOI18N
        jLabel1.setText("FORM TRANSAKSI PENJUALAN");

        btnCari.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-search-20.png"))); // NOI18N
        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        tblProduk.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProduk);

        btnTambah.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-plus-20.png"))); // NOI18N
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Kode Transaksi");

        txtIdPenjualan.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Data Transaksi");

        tblPenjualan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPenjualanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPenjualan);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-bill-20.png"))); // NOI18N
        jLabel4.setText("Total");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-coins-20.png"))); // NOI18N
        jLabel5.setText("Bayar");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-return-20.png"))); // NOI18N
        jLabel6.setText("Kembalian");

        txtTotal.setEnabled(false);

        txtKembalian.setEnabled(false);

        btnHapus.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-delete-20 (1).png"))); // NOI18N
        btnHapus.setText("HAPUS");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBayar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-coins-20.png"))); // NOI18N
        btnBayar.setText("BAYAR");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        btnKeluar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8-exit-20.png"))); // NOI18N
        btnKeluar.setText("KELUAR");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        txtJam.setEnabled(false);

        txtTanggal.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("JUMLAH");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Masukan Id Produk");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtIdPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtIdProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCari))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(btnBayar)
                                        .addGap(28, 28, 28)
                                        .addComponent(btnKeluar))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnHapus)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(jLabel7)
                                .addGap(41, 41, 41)
                                .addComponent(btnTambah)))
                        .addContainerGap(72, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtJam, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(btnTambah))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnHapus)
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBayar)
                            .addComponent(btnKeluar))))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
cari();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
subtotal();
        kurangi_stok();
        try {
            String Kode_detail=txtIdPenjualan.getText();
            iddetail="D"+Kode_detail;
            String sql="insert into detailpenjualan (DetailID,ProdukID,Harga,JumlahProduk,Subtotal) value (?,?,?,?,?)";
            String update="update produk set Stok='"+kurangistok+"' where ProdukID='"+idproduk+"'";
            pst=konek.prepareStatement(sql);
            pst2=konek.prepareStatement(update);
            pst.setString(1, iddetail);
            pst.setString(2, idproduk);
            pst.setString(3, harga);
            pst.setString(4, txtJumlah.getText());
            pst.setString(5, sub_total);
            pst.execute();
            pst2.execute();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            }
        detail();
        penjumlahan();
        cari();
        clsr();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
try {
            String sql="delete from detailpenjualan where ProdukID=?";
            pst=konek.prepareStatement(sql);
            pst.setString(1, idprodukpenjualan);
            pst.execute();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        detail();
        penjumlahan();
        tambah_stok();
        cari();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
total();
     simpan();
     autonumber();
     detail();
     txtTotal.setText("");
     txtBayar.setText("");
     txtKembalian.setText("");
     txtIdProduk.setText("");
     cari();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBayarActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void tblProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdukMouseClicked
try {
    int row=tblProduk.getSelectedRow();
    String tabel_klik=(tblProduk.getModel().getValueAt(row, 0).toString());
    String sql="select * from produk where ProdukID='"+tabel_klik+"'";
    pst=konek.prepareStatement(sql);
    rst=pst.executeQuery();
    if (rst.next()) {
    idproduk=rst.getString(("ProdukID"));    
    String stok=rst.getString(("Stok"));
    inputstok= Integer.parseInt(stok);
    harga=rst.getString(("Harga"));
    inputharga= Integer.parseInt(harga);
    }
}catch (Exception e) {
    JOptionPane.showMessageDialog(null, e);
}

        // TODO add your handling code here:
    }//GEN-LAST:event_tblProdukMouseClicked

    private void tblPenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPenjualanMouseClicked
try {
    int row=tblPenjualan.getSelectedRow();
    idprodukpenjualan=(tblPenjualan.getModel().getValueAt(row, 1).toString());
    String sql="select * from detailpenjualan where ProdukID='"+idprodukpenjualan+"'";
    pst=konek.prepareStatement(sql);
    rst=pst.executeQuery();
    if (rst.next()) {   
    String jumlah=rst.getString(("JumlahProduk"));
    inputjumlah= Integer.parseInt(jumlah);
    }
}catch (Exception e) {
    JOptionPane.showMessageDialog(null, e);
}
ambil_stock();
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPenjualanMouseClicked

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblPenjualan;
    private javax.swing.JTable tblProduk;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtIdPenjualan;
    private javax.swing.JTextField txtIdProduk;
    private javax.swing.JTextField txtJam;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
