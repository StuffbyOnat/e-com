package frames;
import Database.User;
import Database.dataHolder;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 * @author onatu
 */
public class shoppingScreen extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(shoppingScreen.class.getName());
    Connection conn;
    ArrayList<product> products;
    ordersScreenCustomer os_Customer;
    ordersScreenAdmin os_Admin;

    public shoppingScreen(loginScreen loginScreen) {
        initComponents();
        this.setLocation(300, 0);
        this.setSize(650, 800);
        conn = loginScreen.conn;
        products = new ArrayList<>();
        initializeDatas();
        loadCategories();
        loadBrands();
    }

    public void initializeDatas() {
        gridPanel.removeAll();
        products.clear();
        dataHolder.products.clear();

        // Veritabanı sorgusu
        String sql = "SELECT * FROM Products p " +
                "JOIN Product_Variants pv ON p.productID = pv.productID " +
                "JOIN Categories c ON p.categoryID = c.categoryID";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int dbId = rs.getInt("productID");
                // HATA BURADAYDI: 'productName' yerine 'brandName' kullanıldı
                String dbName = rs.getString("brandName");
                double dbPrice = rs.getDouble("basePrice");
                String dbCategory = rs.getString("categoryName");
                String dbDescription = rs.getString("description");
                String dbColor = rs.getString("color");
                String dbSize = rs.getString("size");
                String dbShade = rs.getString("shade");
                String dbsku_Code = rs.getString("sku_Code");
                String dbBrandName = rs.getString("brandName");

                // Objeleri 10 parametreli yeni düzene göre oluşturuyoruz
                ProductPane yeniUrunObjesi = new ProductPane(dbId, dbName, dbCategory, dbPrice, dbDescription, dbColor, dbSize, dbShade, dbsku_Code, dbBrandName);
                product pObj = new product(dbId, dbName, dbCategory, dbPrice, dbDescription, dbColor, dbSize, dbShade, dbsku_Code, dbBrandName);

                products.add(pObj);
                dataHolder.products.add(pObj);
                yeniUrunObjesi.product = pObj;

                System.out.println("product added: " + dbBrandName);
                gridPanel.add(yeniUrunObjesi);
            }

            gridPanel.revalidate();
            gridPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadCategories() {
        categoryBox.removeAllItems();
        categoryBox.addItem("All");
        String sql = "SELECT DISTINCT categoryName FROM Categories";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categoryBox.addItem(rs.getString("categoryName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadBrands() {
        brandBox.removeAllItems();
        brandBox.addItem("All");
        String sql = "SELECT DISTINCT brandName FROM Products";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                brandBox.addItem(rs.getString("brandName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showProducts(ArrayList<product> productList) {
        gridPanel.removeAll();
        for (product p : productList) {
            ProductPane panel = new ProductPane(
                    p.getProductID(), p.getProductName(), p.getCategory(),
                    p.getBasePrice(), p.getDescription(), p.getColor(),
                    p.getSize(), p.getShade(), p.getSku_Code(), p.getBrandName()
            );
            panel.product = p;
            gridPanel.add(panel);
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        topPanel = new javax.swing.JPanel();
        shoppingLabel = new javax.swing.JLabel();
        ordersButton = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        categoryBox = new javax.swing.JComboBox<>();
        brandBox = new javax.swing.JComboBox<>();
        scrollPane = new javax.swing.JScrollPane();
        gridPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        shoppingLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18));
        shoppingLabel.setForeground(new java.awt.Color(255, 51, 255));
        shoppingLabel.setText("Shopping");

        ordersButton.setText("Orders");
        ordersButton.addActionListener(this::ordersButtonActionPerformed);

        searchField.setText("");
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });

        searchButton.setText("Search 🔎");
        searchButton.addActionListener(this::searchButtonActionPerformed);

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
                topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(topPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(shoppingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(searchButton)
                                .addGap(10, 10, 10)
                                .addComponent(categoryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(brandBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ordersButton)
                                .addContainerGap())
        );
        topPanelLayout.setVerticalGroup(
                topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(topPanelLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(shoppingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ordersButton)
                                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchButton)
                                        .addComponent(categoryBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(brandBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        getContentPane().add(topPanel, java.awt.BorderLayout.PAGE_START);
        gridPanel.setLayout(new java.awt.GridLayout(0, 4, 10, 10));
        scrollPane.setViewportView(gridPanel);
        getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);
        pack();
    }// </editor-fold>

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String text = searchField.getText().toLowerCase();
        ArrayList<product> results = new ArrayList<>();
        for (product p : dataHolder.products) {
            if (p.getProductName().toLowerCase().contains(text)) {
                results.add(p);
            }
        }
        showProducts(results);
    }

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {
        String text = searchField.getText().toLowerCase();
        String selectedCategory = categoryBox.getSelectedItem() != null ? categoryBox.getSelectedItem().toString() : "All";
        String selectedBrand = brandBox.getSelectedItem() != null ? brandBox.getSelectedItem().toString() : "All";

        ArrayList<product> results = new ArrayList<>();
        for (product p : dataHolder.products) {
            boolean matchesName = p.getProductName().toLowerCase().contains(text);
            boolean matchesCategory = selectedCategory.equals("All") || p.getCategory().equalsIgnoreCase(selectedCategory);
            boolean matchesBrand = selectedBrand.equals("All") || p.getBrandName().equalsIgnoreCase(selectedBrand);
            if (matchesName && matchesCategory && matchesBrand) {
                results.add(p);
            }
        }
        showProducts(results);
    }

    private void ordersButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (User.getRole().equalsIgnoreCase("customer")) {
            os_Customer = new ordersScreenCustomer(conn, this);
            os_Customer.setVisible(true);
            this.setVisible(false);
        } else if (User.getRole().equalsIgnoreCase("admin")) {
            os_Admin = new ordersScreenAdmin(conn, this);
            os_Admin.setVisible(true);
            this.setVisible(false);
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JComboBox<String> brandBox;
    private javax.swing.JComboBox<String> categoryBox;
    private javax.swing.JPanel gridPanel;
    private javax.swing.JButton ordersButton;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel shoppingLabel;
    private javax.swing.JPanel topPanel;
    // End of variables declaration
}