package frames;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Database.dataHolder;

/**
 *
 * @author onatu
 */
public class product {
    
    //Products table
   
    public int getProductID() {
        return productID;
    }
    public String getBrandName(){
    return brandName;
}
    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public String getShade() {
        return shade;
    }

    public String getSku_Code() {
        return sku_Code;
    }
     int productID;
    private final String brandName;
    String productName;
    String category;
    double basePrice;
    String description;
    //Variants table
    String color;
    String size;
    String shade;
    String sku_Code;
    
  public product(int productID,String productName,String category,double basePrice, String description,
               String color,
               String size,
               String shade,
               String sku_Code,
               String brandName){
    this.brandName=brandName;
    this.productID = productID;
    this.productName = productName;
    this.category = category;
    this.basePrice = basePrice;
    this.description = description;
    this.color = color;
    this.size = size;
    this.shade = shade;
    this.sku_Code = sku_Code;
}
    @Override
    public String toString() {
        return "product{" + "productID=" + productID + ", productName=" + productName + ", category=" + category + ", basePrice=" + basePrice + ", description=" + description + ", color=" + color + ", size=" + size + ", shade=" + shade + ", sku_Code=" + sku_Code + '}';
    }

    public product findPoductById(int id){
        for(product p : dataHolder.products){
            if(p.productID==id){
                return p;
            }
        }
        return null; // Eğer bulunamazsa null döndür
    }
}
