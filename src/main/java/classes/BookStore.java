
package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Chathushi Ranasinghe
 */

public class BookStore {
    
    //admin menu
    public static void adminMenu(){
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("MAIN MENU");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("\nPlease select a menu option:");
        System.out.println("1) Add new book");
        System.out.println("2) Update existing book details");
        System.out.println("3) Display all books");
        System.out.println("4) Search book by bookID");
        System.out.println("5) Delete book");
        System.out.println("6) View orders");
        System.out.println("7) Update order status");
        System.out.println("8) Display users");
        System.out.println("9) Exit\n");
    }
    
    //client menu
    public static void clientMenu(){
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("MAIN MENU");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("\nPlease select a menu option:");
        System.out.println("1) Add new order");
        System.out.println("2) Search book by book title");
        System.out.println("3) Exit\n");
    }
    
    public static void main(String args[]) throws IOException{
        //Scanner scn = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String uname,pwd,title,author,updateTitle,updateAuthor, searchBookName;
        int mainOpt,menuOpt,quantity,yearPublished,updateQuantity,updateYearPublished,searchBookID;
        double price, updatePrice;
        boolean bookAdded, bookUpdated;
        User u = new User();
        Admin ad = new Admin();
        Book bk = new Book();
        Client cl = new Client();
        Order or = new Order();

            mainOpt = 0;
            while(mainOpt != 2)
            {
                try {
                    System.out.println("\n------------------------------------------------------------------------");
                    System.out.println("Welcome to the bookstore!");
                    System.out.println("------------------------------------------------------------------------");
                    System.out.println("\nSelect option: ");
                    System.out.println("1) Login");
                    System.out.println("2) Exit");
                    mainOpt = Integer.parseInt(reader.readLine());
                    
                    if(mainOpt == 1)
                    {
                        System.out.println("\nPlease login:");
                        System.out.println("Username:");
                        uname = reader.readLine().toLowerCase();
                        System.out.println("Password:");
                        pwd = reader.readLine().toLowerCase();
                        User userResults = u.login(uname, pwd);
                        if(userResults != null)
                        {
                            if("admin".equals(userResults.getRole())){
                                adminMenu();
                                menuOpt = Integer.parseInt(reader.readLine());
                                while(menuOpt != 9)
                                {
                                    switch(menuOpt)
                                    {
                                    case 1: System.out.println("\n------------------------------------------------------------------------");
                                            System.out.println("ADD NEW BOOK");
                                            System.out.println("------------------------------------------------------------------------");
                                            System.out.println("\nEnter book title: ");
                                            title = reader.readLine();
                                            System.out.println("\nEnter book author: ");
                                            author = reader.readLine();
                                            System.out.println("\nEnter book year published: ");
                                            yearPublished = Integer.parseInt(reader.readLine());
                                            System.out.println("\nEnter book quantity: ");
                                            quantity = Integer.parseInt(reader.readLine());
                                            System.out.println("\nEnter price: ");
                                            price = Double.parseDouble(reader.readLine());
                                            Book addNewBook = new Book(bk.getMaxBookID(),title,author,yearPublished,quantity,price);
                                            bookAdded = ad.addBook(addNewBook);
                                            if(bookAdded){
                                                System.out.println("\nBook successfully added.");
                                            }
                                            else{
                                                System.out.println("\nBook cannot be added.");
                                            }
                                            break;
                                    case 2: System.out.println("\n------------------------------------------------------------------------");
                                            System.out.println("UPDATE EXISTING BOOK DETAILS");
                                            System.out.println("------------------------------------------------------------------------");
                                            System.out.println("\nEnter book id to update: ");
                                            int bookID = Integer.parseInt(reader.readLine());
                                            Book validBook = bk.checkBookID(bookID);
                                            if(validBook == null){
                                                System.out.println("Invalid book ID. Try again.");
                                            }else{
                                                System.out.println("\n------------------------------------------------------------------------");
                                                System.out.println("Current book details");
                                                bk.displayBooks(validBook);
                                                System.out.println("\n------------------------------------------------------------------------");
                                                System.out.println("\nPlease update below:");
                                                System.out.println("\nEnter book title: ");
                                                updateTitle = reader.readLine();
                                                System.out.println("\nEnter book author: ");
                                                updateAuthor = reader.readLine();
                                                System.out.println("\nEnter book year published: ");
                                                updateYearPublished = Integer.parseInt(reader.readLine());
                                                System.out.println("\nEnter book quantity: ");
                                                updateQuantity = Integer.parseInt(reader.readLine());
                                                System.out.println("\nEnter price: ");
                                                updatePrice = Double.parseDouble(reader.readLine());
                                                Book newBook = new Book(bookID,updateTitle,updateAuthor,updateYearPublished,updateQuantity,updatePrice);
                                                bookUpdated= ad.updateBook(newBook);
                                                if(bookUpdated){
                                                    System.out.println("\nBook successfully updated.");
                                                }
                                                else{
                                                    System.out.println("\nBook cannot be updated.");
                                                }
                                            }
                                            break;
                                    case 3: System.out.println("\n------------------------------------------------------------------------");
                                            System.out.println("BOOK LIST");
                                            System.out.println("------------------------------------------------------------------------");
                                            Vector<Book> v = new Vector();
                                            v = bk.getBooksFromDatabase();
                                            if(v == null)
                                            {
                                                System.out.println("\nNo books available.");
                                            }
                                            else{
                                                for(Book b: v){
                                                    System.out.println("\n------------------------------------------------------------------------");
                                                    bk.displayBooks(b);
                                                }
                                                System.out.println("\n------------------------------------------------------------------------");
                                            }
                                            break;
                                    case 4: System.out.println("\n------------------------------------------------------------------------");
                                            System.out.println("SEARCH BOOK BY BOOK ID");
                                            System.out.println("------------------------------------------------------------------------");
                                            System.out.println("\nEnter book id: ");
                                            searchBookID = Integer.parseInt(reader.readLine());
                                            Book searchedBook = ad.browseBooks(searchBookID);
                                            if(searchedBook != null){
                                                System.out.println("------------------------------------------------------------------------");
                                                bk.displayBooks(searchedBook);
                                                System.out.println("------------------------------------------------------------------------");
                                            }
                                            else{
                                                System.out.println("\nNo books found.");
                                            }
                                            break;
                                    case 5: System.out.println("\n------------------------------------------------------------------------");
                                            System.out.println("DELETE BOOK");
                                            System.out.println("------------------------------------------------------------------------");
                                            System.out.println("\nEnter book id to delete: ");
                                            int deleteBookID = Integer.parseInt(reader.readLine());
                                            Book validDeleteBook = bk.checkBookID(deleteBookID);
                                            if(validDeleteBook == null){
                                                System.out.println("Invalid book ID. Try again.");
                                            }else{
                                                boolean deleteStatus =  ad.deleteBook(deleteBookID);
                                                if(deleteStatus){
                                                    System.out.println("\nBook deleted successfully.");
                                                }
                                                else{
                                                    System.out.println("\nBook cannot be deleted.");
                                                }
                                            }
                                            break;
                                    case 6: System.out.println("\n------------------------------------------------------------------------");
                                            System.out.println("ORDER LIST");
                                            System.out.println("------------------------------------------------------------------------");
                                            Vector<Order> allOrders = ad.viewOrders();
                                            for(Order order: allOrders){
                                                System.out.println("------------------------------------------------------------------------");
                                                or.displayOrders(order);
                                            }
                                            System.out.println("------------------------------------------------------------------------");
                                            break;
                                    case 7: System.out.println("\n------------------------------------------------------------------------");
                                            System.out.println("UPDATE ORDER STATUS");
                                            System.out.println("------------------------------------------------------------------------");
                                            System.out.println("\nEnter order id to update status: ");
                                            int updateStatusOrderID = Integer.parseInt(reader.readLine());
                                            System.out.println("Enter order status: ");
                                            String updateStatus = reader.readLine();
                                            boolean statusupdated = ad.updateOrderStatus(updateStatus, updateStatusOrderID);
                                            if(statusupdated){
                                                System.out.println("\nOrder status updated.");
                                            }
                                            else{
                                                System.out.println("\nOrder status cannot be updated. Invalid order id.");
                                            }
                                            break;
                                    case 8: System.out.println("\n------------------------------------------------------------------------");
                                            System.out.println("USER LIST");
                                            System.out.println("------------------------------------------------------------------------");
                                            ad.displayUserDetails();
                                            break;
                                    default: System.out.println("\nInvalid menu option. Try again\n");
                                    }
                                    adminMenu();
                                    menuOpt = Integer.parseInt(reader.readLine());
                                }
                            }
                            else{
                                clientMenu();
                                menuOpt = Integer.parseInt(reader.readLine());
                                while(menuOpt != 3){
                                    switch(menuOpt)
                                    {
                                    case 1: System.out.println("\n------------------------------------------------------------------------");
                                            System.out.println("ADD NEW ORDER");
                                            System.out.println("------------------------------------------------------------------------");
                                            System.out.println("\nEnter date(dd/mm/yyyy): ");
                                            String orderDate = reader.readLine();
                                            System.out.println("\nEnter customer name: ");
                                            String custName = reader.readLine();
                                            System.out.println("\nEnter address: ");
                                            String address = reader.readLine();
                                            System.out.println("\nEnter mobile no: ");
                                            String mobile = reader.readLine();
                                            System.out.println("\nSelect books:");
                                            System.out.println("\n------------------------------------------------------------------------");
                                            Vector<Book>availBooks = bk.getBooksFromDatabase();
                                            for(Book b: availBooks)
                                            {
                                               System.out.println("\nBook ID: "+b.getBookID()+","+"Title: "+ b.getTitle() + "," +"No of copies available: " + b.getQuantity() + "," + "Price per book: " + b.getPrice());
                                            }
                                            System.out.println("\n------------------------------------------------------------------------");
                                            System.out.println("\nDo you want to select books? (1-Yes/0-No)");
                                            int bookOpt = Integer.parseInt(reader.readLine());
                                            double total= 0;
                                            Vector<Book>selectedBks = new Vector();
                                            while(bookOpt != 0)
                                            {
                                                if(bookOpt == 1)
                                                {
                                                    System.out.println("\nEnter bookid: ");
                                                    int bookID = Integer.parseInt(reader.readLine());
                                                    Book selectedBook =  bk.checkBookID(bookID);
                                                    if(selectedBook != null)
                                                    {
                                                        System.out.println("Enter quantity: ");
                                                        int qty = Integer.parseInt(reader.readLine());
                                                        total += bk.calculateTotal(bookID, qty);
                                                        selectedBook.setQuantity(qty);
                                                        selectedBks.add(selectedBook);
                                                    }
                                                    else{
                                                        System.out.println("\nInvalid book id. Book does not exist.");
                                                    }
                                                }
                                                else{
                                                    System.out.println("\nInvalid option. Please try again");
                                                }
                                                System.out.println("\nDo you want to select books? (1-Yes/0-No)");
                                                bookOpt = Integer.parseInt(reader.readLine());
                                            }
                                            Order newOrder = new Order(or.getMaxOrderID(),custName,orderDate,total,address,mobile,selectedBks,"Order Placed");
                                            boolean orderAdded = cl.addOrder(newOrder);
                                            if(orderAdded)
                                            {
                                                System.out.println("\nOrder added successfully.");
                                            }
                                            else{
                                                System.out.println("\nOrder cannot be added. Try again later.");
                                            }
                                            break;
                                    case 2:System.out.println("\n------------------------------------------------------------------------");
                                            System.out.println("SEARCH BOOK BY BOOK TITLE");
                                            System.out.println("------------------------------------------------------------------------");
                                           System.out.println("\nEnter book title: ");
                                           searchBookName = reader.readLine();
                                           Book searchedBook = cl.browseBooks(searchBookName);
                                           if(searchedBook != null){
                                                System.out.println("------------------------------------------------------------------------");
                                                bk.displayBooks(searchedBook);
                                                System.out.println("------------------------------------------------------------------------");
                                            }
                                            else{
                                                System.out.println("\nNo books found.");
                                            }
                                            break;
                                    default: System.out.println("\nInvalid menu option. Try again");
                                    }
                                    clientMenu();
                                    menuOpt = Integer.parseInt(reader.readLine());
                                }
                            }
                        }
                        else{
                            System.out.println("\nInvalid username ID.");
                        }
                    }
                }catch(NumberFormatException e){
                    System.out.println("Please enter an integer. Invalid input." + e.getMessage());
                }
            }   
            System.out.println("\nProgram ended.");
        
    }
}
    
