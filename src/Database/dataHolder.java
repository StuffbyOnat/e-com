package Database;

import frames.product;

import java.util.ArrayList;

public class dataHolder {

   public static ArrayList<product> products=new ArrayList<>();

   public static product findProductById(int id){
       for(product p : products){
           if(p.getProductID()==id){
               return p;
           }
       }
       return null; // Eğer bulunamazsa null döndür
   }
}
