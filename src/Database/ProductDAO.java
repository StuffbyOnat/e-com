package Database;
import frames.product;
import java.sql.*;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author demir
 */
public class ProductDAO {
      public static void loadProducts(){
        Connection conn = initializeDatabase.connect();
        String query = "SELECT * FROM products";
        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
             while(rs.next()){
                product p = new product(
                        rs.getInt("productID"),
                        rs.getString("productName"),
                        rs.getString("category"),
                        rs.getDouble("basePrice"),
                        rs.getString("description"),
                        rs.getString("color"),
                        rs.getString("size"),
                        rs.getString("shade"),
                        rs.getString("sku_Code"),
                        rs.getString("BrandName")
                );
                dataHolder.products.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static ArrayList<product> searchProducts(String text){
        ArrayList<product> results = new ArrayList<>();
        for(product p : dataHolder.products){
           if(p.getProductName().toLowerCase().contains(text.toLowerCase())){
                results.add(p);
            }
        }
        return results;
    }
}
