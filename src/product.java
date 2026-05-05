/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author onatu
 */
public class product {
    
    //Products table
    int productID;
    String productName;
    String category;
    double basePrice;
    String description;
    //Variants table
    String color;
    String size;
    String shade;
    
    public product(int productID,String productName,String category,double basePrice,String description,String color,String size,String shade){
    this.productID=productID;
    this.productName=productName;
    this.category=category;
    this.basePrice=basePrice;
    this.description=description;
    this.color=color;
    this.size=size;
    this.shade=shade;
    }
}
