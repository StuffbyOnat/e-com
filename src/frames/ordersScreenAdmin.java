/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
/**
 *
 * @author onatu
 */
public class ordersScreenAdmin extends javax.swing.JFrame implements OrderView {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ordersScreenAdmin.class.getName());
    Connection conn;
    shoppingScreen shoppingScreen;
    /**
     * Creates new form ordersScreenAdmin
     */
    public ordersScreenAdmin(Connection conn,shoppingScreen shoppingScreen) {
        this.setTitle("E-Commerce Platform for Cosmetics");
        initComponents();
        this.shoppingScreen=shoppingScreen;
        this.conn=conn;
        this.setLocation(shoppingScreen.getLocation());
        this.setSize(551,402);
        getOrdersOnTable();
    }

    
    @Override
    public void getOrdersOnTable() {

        String sql = "select * from AdminOrderReport";
        try(PreparedStatement ps = conn.prepareStatement(sql)){

            try(ResultSet rs = ps.executeQuery()){

                String[] columnNames = {"Order ID", "Order Date", "Customer", "City", "Product", "Size", "Shade", "Quantity", "Status"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        // Sadece 8. indeks (Status) için kontrol yap
                        if (column == 8) {
                            Object statusValue = getValueAt(row, 8);
                            if (statusValue != null) {
                                String status = statusValue.toString();
                                // Eğer durum Delivered veya Cancelled ise DÜZENLEMEYİ KAPAT (false dön)
                                if (status.equalsIgnoreCase("Delivered") || status.equalsIgnoreCase("Cancelled")) {
                                    return false;
                                }
                            }
                            return true; // Pending, Processing, Shipped ise düzenlenebilir
                        }
                        return false; // Diğer tüm sütunlar kapalı
                    }
                };

                // Yeni modelimizi tabloya atıyoruz
                jTable1.setModel(model);

                // 2. Verileri Veritabanından Tabloya Aktar
                while (rs.next()) {
                    Object[] row = {
                            rs.getInt("orderID"),
                            rs.getTimestamp("orderDate"),
                            rs.getString("Customer"),
                            rs.getString("City"),
                            rs.getString("Product"),
                            rs.getString("size"),
                            rs.getString("shade"),
                            rs.getInt("quantity"),
                            rs.getString("status")
                    };
                    model.addRow(row);
                }

                // 3. JComboBox Editörünü Ekle
                javax.swing.JComboBox<String> statusCombo = new javax.swing.JComboBox<>(new String[]{
                        "Pending", "Processing", "Shipped", "Delivered", "Cancelled"
                });

                javax.swing.table.TableColumn statusColumn = jTable1.getColumnModel().getColumn(8);
                statusColumn.setCellEditor(new javax.swing.DefaultCellEditor(statusCombo));

                // Eski dinleyicileri temizle
                for(javax.swing.event.TableModelListener l : model.getTableModelListeners()) {
                    model.removeTableModelListener(l);
                }

                // 4. Tablodaki Değişiklikleri Dinle
                model.addTableModelListener(new javax.swing.event.TableModelListener() {
                    @Override
                    public void tableChanged(javax.swing.event.TableModelEvent e) {
                        if (e.getType() == javax.swing.event.TableModelEvent.UPDATE && e.getColumn() == 8) {
                            int row = e.getFirstRow();
                            if (row >= 0) {
                                int orderId = (int) model.getValueAt(row, 0);
                                String newStatus = (String) model.getValueAt(row, 8);

                                // Sadece veritabanını güncellemek yeterli!
                                // Tablo bir sonraki tıklamada yeni statüyü isCellEditable'da kendi kontrol edecek.
                                updateOrderStatus(orderId, newStatus);
                            }
                        }
                    }
                });
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Değişen Durumu Veritabanına Yazan Metod
    private void updateOrderStatus(int orderId, String newStatus) {
        String updateSql = "UPDATE Orders SET status = ? WHERE orderID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, orderId);
            pstmt.executeUpdate();
            System.out.println("Başarılı! Order ID: " + orderId + " -> Yeni Durum: " + newStatus);
        } catch (SQLException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Status güncellenemedi: " + ex.getMessage(), "Hata", javax.swing.JOptionPane.ERROR_MESSAGE);
        }

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        adminScreenLabel = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backButton.setText("Back");
        backButton.addActionListener(this::backButtonActionPerformed);

        adminScreenLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        adminScreenLabel.setText("Admin Screen");

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(this::refreshButtonActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(adminScreenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton)
                    .addComponent(refreshButton))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(backButton)
                .addGap(18, 18, 18)
                .addComponent(adminScreenLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refreshButton)
                .addContainerGap(187, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_END);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Order ID", "Order Date", "Customer", "City", "Product", "Size", "Shade", "Quantity", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        shoppingScreen.initializeDatas();
        shoppingScreen.revalidate();
        shoppingScreen.repaint();
        shoppingScreen.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        getOrdersOnTable();
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_refreshButtonActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adminScreenLabel;
    private javax.swing.JButton backButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables
}
