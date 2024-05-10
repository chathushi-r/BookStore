
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

public class Admin extends User{
    Connection connection = null;
    PreparedStatement psQuery = null;
    
    //add new book
    public boolean addBook(Book book){
        try{
            //JDBC connection
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery=connection.prepareStatement("INSERT into BOOKS(BOOKID,TITLE,AUTHOR,YEARPUBLISHED,QUANTITY,PRICE) values(?,?,?,?,?,?)");
            //set parameters for the sql query
            psQuery.setInt(1,book.getBookID());
            psQuery.setString(2,book.getTitle());
            psQuery.setString(3,book.getAuthor());
            psQuery.setInt(4,book.getYearPublished());
            psQuery.setInt(5,book.getQuantity());
            psQuery.setDouble(6,book.getPrice());
            psQuery.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;   
        }finally{   //clean database resources
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
        }
        
    }
    
    //update existing book details
    public boolean updateBook(Book book){
        
        try{
            //JDBC connection
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery=connection.prepareStatement("UPDATE BOOKS SET TITLE=?,AUTHOR=?,YEARPUBLISHED=?,QUANTITY=?,PRICE=? WHERE BOOKID=?");
            //set parameters for the sql query
            psQuery.setString(1,book.getTitle());
            psQuery.setString(2,book.getAuthor());
            psQuery.setInt(3,book.getYearPublished());
            psQuery.setInt(4,book.getQuantity());
            psQuery.setDouble(5,book.getPrice());
            psQuery.setInt(6,book.getBookID());
            psQuery.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Cannot connect to the database.");
            return false;
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
        }
    }
    
    //delete book from the database
    public boolean deleteBook(int bookId){
        
        try {
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery=connection.prepareStatement("DELETE from BOOKS where BOOKID = ?");
            psQuery.setInt(1,bookId);
            psQuery.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("Unable to delete book: " + e.getMessage());
            return false;
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
        }
    }
    
    //view order list
    public Vector<Order> viewOrders(){
        //Initialization
        Vector<Order> v = new Vector();
        
        try {
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery=connection.prepareStatement("SELECT * FROM ORDERS");
            ResultSet queryResultSet =  psQuery.executeQuery();
            
            while(queryResultSet.next()){
                Order or = new Order();
                or.setOrderId(Integer.parseInt(queryResultSet.getString(1)));
                or.setOrderDate(queryResultSet.getString(2));
                or.setCustomerName(queryResultSet.getString(3));
                or.setAddress(queryResultSet.getString(4));
                or.setMobileNo(queryResultSet.getString(5));
                or.setAmount(Double.parseDouble(queryResultSet.getString(6)));
                or.setOrderStatus(queryResultSet.getString(7));
                
                PreparedStatement psOrder = connection.prepareStatement("SELECT BOOKID,TITLE,QUANTITY,PRICE FROM ORDERBOOK WHERE ORDERID = ?");
                psOrder.setInt(1,or.getOrderId());
                ResultSet orderQueryResultSet =  psOrder.executeQuery();
                Vector<Book> b1 = new Vector();
                while(orderQueryResultSet.next())
                {
                    Book b = new Book();
                    b.setBookID(Integer.parseInt(orderQueryResultSet.getString(1)));
                    b.setTitle(orderQueryResultSet.getString(2));
                    b.setQuantity(Integer.parseInt(orderQueryResultSet.getString(3)));
                    b.setPrice(Double.parseDouble(orderQueryResultSet.getString(4)));
                    b1.add(b);
                }
                or.setBooks(b1);
                v.add(or);
            }
           return v;
        } catch (SQLException e) {
            System.out.println("Unable to display order list: "+ e.getMessage());
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
        }
    }
    
    //update order status of an existing order
    public boolean updateOrderStatus(String orderStatus,int orderID){
        
        try{
            //JDBC connection
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery=connection.prepareStatement("UPDATE ORDERS SET ORDERSTATUS=? WHERE ORDERID=?");
            //set parameters for the sql query
            psQuery.setString(1,orderStatus);
            psQuery.setInt(2,orderID);
            psQuery.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Unable to update order status: "+e.getMessage());
            return false;
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
        }
    }
    
    //browseBooks() is overloaded 
    public Book browseBooks(int bookID){
        
        ResultSet queryResultSet;
        try{
            //JDBC connection
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery=connection.prepareStatement("SELECT * FROM BOOKS WHERE BOOKID = ?");
            //set parameters for the sql query
            psQuery.setInt(1,bookID);
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
            System.out.println("Unable to perform search operation: " + e.getMessage());
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
        }
    }
    
    @Override
    protected void displayUserDetails(){
       try{
           Vector<User> userlist = getUsers();
           System.out.println("\nAdmin details:");
           for(User u : userlist){
               if (u.getRole().equalsIgnoreCase("admin")) {
                   System.out.println("Username: " + u.getUsername() + ", Email: " + u.getEmail()+ ", User Role: " + u.getRole());
               }
           }
           System.out.println("\nClient details:");
           for(User u : userlist){
               if (u.getRole().equalsIgnoreCase("client")) {
                   System.out.println("Username: " + u.getUsername() + ", Email: " + u.getEmail() + ", User Role: " + u.getRole());
               }
           }
       }catch(Exception e){
           System.out.println("Unable to display user details: "+e.getMessage());
       }
    }
  
}
