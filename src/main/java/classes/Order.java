
package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Chathushi Ranasinghe
 */

public class Order {
    private int orderID;
    private String customerName;
    private String orderDate;
    private double amount;
    private String address;
    private String mobileNo;
    private Vector<Book>books;
    private String orderStatus;

    public Order(int orderID, String customerName, String orderDate, double amount, String address, String mobileNo, Vector<Book> books, String orderStatus) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.amount = amount;
        this.address = address;
        this.mobileNo = mobileNo;
        this.books = books;
        this.orderStatus = orderStatus;
    }
    
    public Order(){
        
    }
    
    //getters and setters
    public int getOrderId() {
        return orderID;
    }

    public void setOrderId(int orderId) {
        this.orderID = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Vector<Book> getBooks() {
        return books;
    }

    public void setBooks(Vector<Book> books) {
        this.books = books;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public String getaddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    
    //get maximum order id from the database
    public int getMaxOrderID(){
        Connection connection = null;
        PreparedStatement psQuery = null;
        ResultSet queryResultSet = null;
        try{
            String result;
            int orderId = 0;
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery = connection.prepareStatement("SELECT MAX(ORDERID) FROM ORDERS");
            queryResultSet = psQuery.executeQuery();
            while (queryResultSet.next()) {
                
                result = queryResultSet.getString(1);
                if(result == null){
                    orderId = 1;
                }else{
                    orderId = Integer.parseInt(result)+1;
                }
            }
            return orderId;
        }catch(SQLException e){
            System.out.println("Unable to get max order id: "+e.getMessage());
            return 0;
        }
        finally{   //clean up database resources
            try {
                if (psQuery != null) {
                    psQuery.close();
                }
            } catch (SQLException ex) {
                System.out.println("Unable to close PreparedStatement: "+ex.getMessage());
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exc) {
                System.out.println("Unable to close connection: "+exc.getMessage());
            }
            try {
                if (queryResultSet != null) {
                    queryResultSet.close();
                }
            } catch (SQLException excp) {
                System.out.println("Unable to close ResultSet: "+excp.getMessage());
            }
        }
    }
    
    //print order list
    public void displayOrders(Order order){
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Order date: " + order.getOrderDate());
        System.out.println("Customer Name: " + order.getCustomerName());
        System.out.println("Address: " + order.getaddress());
        System.out.println("Mobile No: " + order.getMobileNo());
        System.out.println("Total Amount: " + order.getAmount());
        System.out.println("Order status: " + order.getOrderStatus());
        System.out.println("\nBooks ordered: ");
        Vector<Book> orderedBks = order.getBooks();
        for(Book book:orderedBks){
            System.out.println(book.getBookID()+","+book.getTitle()+","+book.getQuantity()+","+book.getPrice());
        }                            
    }
}
