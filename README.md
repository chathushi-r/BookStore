The source codes for the application are inside the folder src/main/java and then click on classes.
The classes folder consists of all the classes for the book store console application where the BookStore.java is the main class

The database itself is not included in the repository. Instead, you'll need to set it up inside the Netbeans project:

Apache Derby Client dependency is already added in the project's pom.xml file. This will enable derby client in your application allowing it to connect to a derby database.

You can direct to services window in netbeans and create a database:
- Right click Java DB and create a database.
- Create the necessary tables.
- Connect to the database.

At last, modify the database connection code lines by adding the new database name, username and password. Modify the sql queries according to your table and column names.
