
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

public class User {
    private String username;
    private String password;
    private String email;
    private String userRole;
    
    //parameterized constructor
    public User(String username,String password, String email, String userRole){
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }
    
    //default constructor
    public User(){
        
    }
    
    //getters and setters 
    protected String getUsername(){
        return username;
    }
    
    protected String getPassword(){
        return password;
    }
    
    protected void setPassword(String password){
        this.password = password;
    }
    
    protected void setUsername(String username){
        this.username = username;
    }
    
    protected void setRole(String userRole){
        this.userRole = userRole;
    }
    
    protected String getRole(){
        return userRole;
    }
    
    protected void setEmail(String email){
        this.email = email;
    }
    
    protected String getEmail(){
        return email;
    }
    
    //user authentication function
    public User login(String username,String password ){
        Connection connection = null;
        PreparedStatement psQuery = null;
        ResultSet queryResultSet = null;
        try{
            //JDBC connection
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery=connection.prepareStatement("SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?");
            //set parameters for the sql query
            psQuery.setString(1,username);
            psQuery.setString(2,password);
            queryResultSet = psQuery.executeQuery();
            if(queryResultSet.next())
            {
                //create new user object and return
                String uname = queryResultSet.getString(2);
                String pwd = queryResultSet.getString(3);
                String email = queryResultSet.getString(4);
                String role = queryResultSet.getString(5);
                return new User(uname,pwd,email,role);
            }
            else{
                //if no user is found
                return null;
            }
        }catch(SQLException e){
            System.out.println("Unable to perform login operation: "+e.getMessage());
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
    
    //to retrieve the user list from the database
    public Vector<User> getUsers(){
        Vector<User>userlist = new Vector();
        Connection connection = null;
        PreparedStatement psQuery = null;
        ResultSet queryResultSet = null;
        try{
            //JDBC connection
            connection=DriverManager.getConnection("jdbc:derby://localhost:1527/BookStoreData","assgn3","1234");
            psQuery=connection.prepareStatement("SELECT * FROM USERS");
            queryResultSet = psQuery.executeQuery();
            while(queryResultSet.next())
            {
                String uname = queryResultSet.getString(2);
                String pwd = "--";
                String email = queryResultSet.getString(4);
                String role = queryResultSet.getString(5);
                User u = new User(uname,pwd,email,role);
                userlist.add(u);
            }
            return userlist;
        }catch(SQLException e){
            System.out.println("Unable to retrieve user list: "+e.getMessage());
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
    
    public Book browseBooks(){
         System.out.println("Performing a search..");
         return null;
    }
    
    //to print user details
    protected void displayUserDetails(){
        Vector<User> userlist = getUsers();
        System.out.println("User details:");
        for(User u : userlist){
            System.out.println("Username:" + u.getUsername()+ "Email:" + u.getEmail()+"User Role:" + u.getRole() );
        }
    }
}
