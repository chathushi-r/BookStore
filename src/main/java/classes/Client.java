
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

public class Client extends User {
    Connection connection = null;
    PreparedStatement psQuery = null;
    
    //browseBooks() is overloaded
    public Book browseBooks(String bookTitle){
        ResultSet queryResultSet = null;
        try{
            //JDBC connection
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery=connection.prepareStatement("SELECT * FROM BOOKS WHERE TITLE LIKE ?");
            //set parameters for the sql query
            psQuery.setString(1,bookTitle);
            queryResultSet =  psQuery.executeQuery();
            if(queryResultSet.next()){
               Book book = new Book();
               book.setBookID(Integer.parseInt(queryResultSet.getString(1)));
               book.setTitle(queryResultSet.getString(2));
               book.setAuthor(queryResultSet.getString(3));
               book.setYearPublished(Integer.parseInt(queryResultSet.getString(4)));
               book.setQuantity(Integer.parseInt(queryResultSet.getString(5)));
               book.setPrice(Double.parseDouble(queryResultSet.getString(6)));
               return book;
            }
            else{
                return null;
            }
        }catch(SQLException e){
            System.out.println("Unable to perform search operation: "+e.getMessage());
            return null;
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
                System.out.println("Unable to close result set: "+excp.getMessage());
            }
        }
    }
    
    //add new book to the database
    public boolean addOrder(Order order){
        PreparedStatement psOrder = null;
        ResultSet queryResultSet=null;
        try{
            //JDBC connection
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery=connection.prepareStatement("INSERT into ORDERS(ORDERID,ORDERDATE,CUSTOMERNAME,ADDRESS,MOBILENO,TOTAL,ORDERSTATUS) values(?,?,?,?,?,?,?)");
            //set parameters for the sql query
            psQuery.setInt(1, order.getOrderId());
            psQuery.setString(2, order.getOrderDate());
            psQuery.setString(3, order.getCustomerName());
            psQuery.setString(4, order.getaddress());
            psQuery.setString(5,order.getMobileNo());
            psQuery.setDouble(6, order.getAmount());
            psQuery.setString(7, order.getOrderStatus());
            psQuery.executeUpdate();
            
            Vector<Book> orderedBooks = order.getBooks();
            for(Book b:orderedBooks){
                psOrder = connection.prepareStatement("INSERT into ORDERBOOK(ORDERID,BOOKID,TITLE,AUTHOR,QUANTITY,PRICE) values(?,?,?,?,?,?)");
                psOrder.setInt(1, order.getOrderId());
                psOrder.setInt(2, b.getBookID());
                psOrder.setString(3, b.getTitle());
                psOrder.setString(4, b.getAuthor());
                psOrder.setInt(5,b.getQuantity());
                psOrder.setDouble(6,b.getPrice());
                psOrder.executeUpdate();   
                
                psQuery = connection.prepareStatement("SELECT * FROM BOOKS WHERE BOOKID=?");
                psQuery.setInt(1, b.getBookID());
                queryResultSet = psQuery.executeQuery();
                int qtyStock = 0;
                while (queryResultSet.next()) {
                    qtyStock = Integer.parseInt(queryResultSet.getString(5));
                }
                int quantityOrdered = b.getQuantity();
                int quantityAvailable = qtyStock - quantityOrdered;
                
                psQuery = connection.prepareStatement("UPDATE BOOKS SET QUANTITY=? WHERE BOOKID=?");
                psQuery.setInt(1,quantityAvailable);
                psQuery.setInt(2, b.getBookID());
                psQuery.executeUpdate();
            }
            return true;
        }catch(SQLException e){
            System.out.println("Unable to add new order: "+e.getMessage());
            return false;
        }finally{   //clean up resources
            try {
                if (psQuery != null) {
                    psQuery.close();
                }
            } catch (SQLException ex) {
                System.out.println("Unable to close PreparedStatement psQuery: "+ex.getMessage());
            }
            try {
                if (psOrder != null) {
                    psOrder.close();
                }
            } catch (SQLException exp) {
                System.out.println("Unable to close PreparedStatement psOrder: "+exp.getMessage());
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
                System.out.println("Unable to close result set: "+excp.getMessage());
            }
        }
    }
    
}
