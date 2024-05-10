
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

public class Book {
    private int bookID;
    private String title;
    private String author;
    private int publishedYear;
    private int quantity;
    private double cost;
    
    //parameterized constructor
    public Book(int bookID,String title,String author,int publishedYear,int quantity,double cost){
        this.bookID = bookID;
        this.title = title;
        this.publishedYear = publishedYear;
        this.author = author;
        this.quantity = quantity;
        this.cost = cost;
    }
    
    //default constructor
    public Book(){
    }
    
    //getters and setters
    public void setBookID(int bookID){
        this.bookID = bookID;
    }
    
    public int getBookID(){
        return bookID;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getTitle(){
        return title;
    }
    
    public void setYearPublished(int publishedYear){
        this.publishedYear = publishedYear;
    }
    
    public int getYearPublished(){
        return publishedYear;
    }
    
    public void setAuthor(String author){
        this.author = author;
    }
    
    public String getAuthor(){
        return author;
    }
    
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    public void setPrice(double price){
        this.cost = price;
    }
    
    public double getPrice(){
        return cost;
    } 
    
    public Book checkBookID(int bookID){
        Vector<Book> v = new Vector();
        v = getBooksFromDatabase();
        for(Book bk: v){
            if(bookID == bk.getBookID()){
                return bk;
            }
        }
        return null;
    }
   
    //retrieve book list from database
    public Vector<Book> getBooksFromDatabase(){
        Vector<Book> v = new Vector();
        Connection connection = null;
        PreparedStatement psQuery = null;
        ResultSet queryResultSet = null;
        try{
            //JDBC connection
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery=connection.prepareStatement("SELECT * FROM BOOKS");
            queryResultSet =  psQuery.executeQuery();
            while(queryResultSet.next()){
               Book book = new Book();
               book.setBookID(Integer.parseInt(queryResultSet.getString(1)));
               book.setTitle(queryResultSet.getString(2));
               book.setAuthor(queryResultSet.getString(3));
               book.setYearPublished(Integer.parseInt(queryResultSet.getString(4)));
               book.setQuantity(Integer.parseInt(queryResultSet.getString(5)));
               book.setPrice(Double.parseDouble(queryResultSet.getString(6)));
               v.add(book);
            }
           // System.out.println("Vector sent");
            return v;
        }catch(SQLException e){
            System.out.println("Unable to retrieve book list: "+e.getMessage());
            return null;
        }
        finally{   //clean up resources
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
    
    //calculate total cost for a book order
    public double calculateTotal(int bookID, int quantity){
        Vector<Book> v = new Vector();
        double total=0;
        v = getBooksFromDatabase();
        for(Book bk: v){
            if(bookID == bk.getBookID()){
                total = quantity * bk.getPrice();
                return total;
            }
        }
        return 0;
        
    }
    
    //print book list
    public void displayBooks(Book b){
        System.out.println("Book ID: " + b.getBookID());
        System.out.println("Book Title: " + b.getTitle());
        System.out.println("Book Author: " + b.getAuthor());
        System.out.println("Year Published: " + b.getYearPublished());
        System.out.println("Quantity: " + b.getQuantity());
        System.out.println("Cost per book: " + b.getPrice());
    }
    
    //to get maximum book id from the database and assign the new book id for the new book
    public int getMaxBookID(){
        Connection connection = null;
        PreparedStatement psQuery = null;
        ResultSet queryResultSet = null;
        try{
            String result;
            int bookId = 0;
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery = connection.prepareStatement("SELECT MAX(BOOKID) FROM BOOKS");
            queryResultSet = psQuery.executeQuery();
            while (queryResultSet.next()) {
                result = queryResultSet.getString(1);
                if(result == null){
                    bookId = 1;
                }else{
                    bookId = Integer.parseInt(result)+1;
                }
            }
            return bookId;
        }catch(SQLException e){
            System.out.println("Unable to get max book id: "+e.getMessage());
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
                System.out.println("Unable to close result set: "+excp.getMessage());
            }
        }
    }
}
