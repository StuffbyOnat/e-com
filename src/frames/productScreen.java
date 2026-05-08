/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import Database.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author onatu
 */
public class productScreen extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(productScreen.class.getName());
    ProductPane productPane;
    boolean inStock;
    Connection conn;
    shoppingScreen shoppingScreen;
    /**
     * Creates new form productFrame
     */
    public productScreen(shoppingScreen shoppingScreen, ProductPane productPane) {
        this.shoppingScreen=shoppingScreen;
        this.setTitle("E-Commerce Platform for Cosmetics");
        initComponents();
        this.productPane = productPane;
        this.setLocation(468, 420);
        conn=shoppingScreen.conn;
        getStockInfo();
        
        // 1. Ekran boyutunu ve konumunu eski ekrandan birebir kopyala
        //this.setSize(shoppingScreen.getSize());
        this.setLocation(shoppingScreen.getLocation());
        
        
        if (productPane.product != null) {
            name.setText(productPane.product.getProductName());
            category.setText(productPane.product.getCategory());
            sku_code.setText(productPane.product.getSku_Code());
            description.setText(productPane.product.getDescription());
            color.setText(productPane.product.getColor());
            size.setText(productPane.product.getSize());
            shade.setText(productPane.product.getShade());
        }

        
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
                //shoppingScreen.setSize(productScreen.this.getSize());
                shoppingScreen.setLocation(productScreen.this.getLocation());
                
                
                shoppingScreen.initializeDatas();
                shoppingScreen.revalidate();
                shoppingScreen.repaint();
                shoppingScreen.setVisible(true);
                productScreen.this.dispose();
            }
        });
    }
    
    public void getStockInfo() {
        
        if (productPane.product == null) return;

        //Unique sku_code
        String skuCode = productPane.product.getSku_Code();
        
        // Sku_Code query.
        String sql = "SELECT SUM(i.stockQuantity) AS totalStock " +
                     "FROM Inventory i " +
                     "JOIN Product_Variants pv ON i.variantID = pv.variantID " +
                     "WHERE pv.sku_Code = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, skuCode); 

            try (ResultSet rs = pstmt.executeQuery()) {
                int stock = 0;
                if (rs.next()) {
                    stock = rs.getInt("totalStock"); 
                }

                
                if (stock > 0) {
                    stockStatus.setText("In Stock");
                    stockStatus.setForeground(new java.awt.Color(0, 153, 51)); // Yazıyı Yeşil yap
                    buyButton.setEnabled(true); // Sepete Ekle butonunu aktifleştir
                } else {
                    stockStatus.setText("Out of Stock");
                    stockStatus.setForeground(new java.awt.Color(204, 0, 0)); // Yazıyı Kırmızı yap
                    buyButton.setEnabled(false); // Stok yoksa butonu devre dışı bırak (Tıklanamaz)
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            stockStatus.setText("Stock Error");
            buyButton.setEnabled(false);
        }
    }
 private void buyProduct(){
        if (productPane.product == null) return;

        String skuCode = productPane.product.getSku_Code();
        double price = productPane.product.getBasePrice();
        
        
        int currentUserId = User.getUserID();

        try {
            String variantQuery = "SELECT variantID FROM Product_Variants WHERE sku_Code = ?";
            PreparedStatement pstVar = conn.prepareStatement(variantQuery);
            pstVar.setString(1, skuCode);
            ResultSet rsVar = pstVar.executeQuery();
            
            int variantId = -1;
            if(rsVar.next()) {
                variantId = rsVar.getInt("variantID");
            }

            if(variantId == -1) {
                JOptionPane.showMessageDialog(this, "Hata: Ürün varyantı bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Statement stmt = conn.createStatement();
            ResultSet rsOrd = stmt.executeQuery("SELECT COALESCE(MAX(orderID), 6000) + 1 FROM Orders");
            int newOrderId = 6000;
            if(rsOrd.next()) newOrderId = rsOrd.getInt(1);

            ResultSet rsDet = stmt.executeQuery("SELECT COALESCE(MAX(detailID), 7000) + 1 FROM Order_Details");
            int newDetailId = 7000;
            if(rsDet.next()) newDetailId = rsDet.getInt(1);

            String insertOrder = "INSERT INTO Orders (orderID, userID, orderDate, totalAmount, status) VALUES (?, ?, NOW(), ?, 'Processing')";
            PreparedStatement pstOrd = conn.prepareStatement(insertOrder);
            pstOrd.setInt(1, newOrderId);
            pstOrd.setInt(2, currentUserId);
            pstOrd.setDouble(3, price);
            pstOrd.executeUpdate();

            String insertDetail = "INSERT INTO Order_Details (detailID, orderID, variantID, quantity, unitPrice) VALUES (?, ?, ?, 1, ?)";
            PreparedStatement pstDet = conn.prepareStatement(insertDetail);
            pstDet.setInt(1, newDetailId);
            pstDet.setInt(2, newOrderId);
            pstDet.setInt(3, variantId);
            pstDet.setDouble(4, price);
            pstDet.executeUpdate();

            JOptionPane.showMessageDialog(this, "Order is successful and now being processed.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            PaymentScreen paymentScreen = new PaymentScreen(newOrderId,price,shoppingScreen);
            paymentScreen.setVisible(true);
            this.dispose();
            //getStockInfo(); 

        } catch (SQLException ex) {
            if(ex.getMessage().contains("Insufficient stock")) {
                JOptionPane.showMessageDialog(this, "Sorry,Out of stock!", "Stock Error", JOptionPane.WARNING_MESSAGE);
                getStockInfo(); // Ekranı güncelle ki "Out of Stock" yazsın
            } else {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
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

        mainPanel = new javax.swing.JPanel();
        productIcon = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextPane();
        descriptionLabel = new javax.swing.JLabel();
        categoryLabel = new javax.swing.JLabel();
        category = new javax.swing.JLabel();
        sku_codeLabel = new javax.swing.JLabel();
        sku_code = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        stockStatus = new javax.swing.JLabel();
        colorLabel = new javax.swing.JLabel();
        sizeLabel = new javax.swing.JLabel();
        shadeLabel = new javax.swing.JLabel();
        color = new javax.swing.JLabel();
        size = new javax.swing.JLabel();
        shade = new javax.swing.JLabel();
        buyButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        productIcon.setText("jLabel1");

        description.setEditable(false);
        jScrollPane1.setViewportView(description);

        descriptionLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        descriptionLabel.setText("Description ");

        categoryLabel.setText("Category:");

        category.setText("toFill");

        sku_codeLabel.setText("sku. code:");

        sku_code.setText("toFill");

        name.setText("toFill");

        nameLabel.setText("Name:");

        stockStatus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        stockStatus.setText("in stock");

        colorLabel.setText("Color:");

        sizeLabel.setText("Size:");

        shadeLabel.setText("Shade:");

        color.setText("jLabel1");

        size.setText("jLabel2");

        shade.setText("jLabel3");

        buyButton.setText("Buy");
        buyButton.addActionListener(this::buyButtonActionPerformed);

        backButton.setText("go back");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(category, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(categoryLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sku_codeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sku_code, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                                .addComponent(shadeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(shade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                                .addComponent(sizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(size, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                                .addComponent(colorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(color, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(stockStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(buyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(nameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(backButton))
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(descriptionLabel)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(43, 43, 43))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(productIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameLabel)
                            .addComponent(backButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(categoryLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(category)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sku_codeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sku_code)
                        .addGap(51, 51, 51)
                        .addComponent(stockStatus))
                    .addComponent(jScrollPane1))
                .addGap(30, 30, 30)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorLabel)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(color)
                        .addComponent(buyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sizeLabel)
                    .addComponent(size))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(shadeLabel)
                    .addComponent(shade))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        getContentPane().add(mainPanel, java.awt.BorderLayout.LINE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyButtonActionPerformed
        buyProduct();
    }//GEN-LAST:event_buyButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton buyButton;
    private javax.swing.JLabel category;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JLabel color;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JTextPane description;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel name;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel productIcon;
    private javax.swing.JLabel shade;
    private javax.swing.JLabel shadeLabel;
    private javax.swing.JLabel size;
    private javax.swing.JLabel sizeLabel;
    private javax.swing.JLabel sku_code;
    private javax.swing.JLabel sku_codeLabel;
    private javax.swing.JLabel stockStatus;
    // End of variables declaration//GEN-END:variables
}
