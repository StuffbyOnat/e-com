/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author demir
 */
public class Payment {
    private int paymentID;
    private int userID;
    private int orderID;

    private double amount;

    private String paymentMethod;
    private String paymentStatus;
   

    public Payment(
            double totalPrice,
            int paymentID,
            int userID,
            int orderID,
            double amount,
            String paymentMethod,
            String paymentStatus){

        this.paymentID = paymentID;
        this.userID = userID;
        this.orderID = orderID;

        this.amount = amount;

        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public int getUserID() {
        return userID;
    }

    public int getOrderID() {
        return orderID;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}
