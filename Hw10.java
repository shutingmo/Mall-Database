/*
 * Name			Shuting Mo
 * Section		7776
 * Assignment	10
 * Due Date		April 11th, 2018
 */

package hw10;

/***********
 * 
 *  MariaDB [(none)]> GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY 'root' WITH GRANT OPTION;
 * 
 * download and place on your java classpath:
 * www.cise.ufl.edu/~pjd/courses/4301/notes/mariadb-java-client-2.2.3.jar
 * 
 * Statements:
 * (1) Parse SQL Query
 * (2) Compile SQL Query
 * (3) Plan/optimize data acquisition path
 * (4) Execute query and return result set
 * 
 * Prepared Statements:
 * (1) - (3) combined
 * (4) follows
 * 
************/

import java.sql.*;
import java.util.Scanner; 

class Hw10 {

  private static final String JDBC_DRIVER = "org.mysql.jdbc.Driver";
  private static final String LOCAL_HOST = "jdbc:mariadb://localhost:3306/";

  private String db;
  private String username;
  private String password;
  // private Statement statement;
  private PreparedStatement preparedStatement;
  private Connection connection;

  public Hw10() {
    this.db = "mall";
    this.username = "root";
    this.password = "root";
  }

  public Hw10( String db, String username, String password ) {
    this.db = db;
	this.username = username;
	this.password = password;
  }

  public boolean openConnection( ) {
	  boolean result = false;

	try {
      connection = DriverManager.getConnection( LOCAL_HOST + db, username, password );
      System.out.println( db + " connected." );
      System.out.println();
      
      // statement = connection.createStatement();
      
      result = true;
    }
    catch ( SQLException sqle ) {
      sqle.printStackTrace();
    }
    
    return result;
  }
  public ResultSet query( String sql, String args[] ) {
    ResultSet rs = null;
    int i;

    try {
      // rs = statement.executeQuery( sql );
      
      preparedStatement = connection.prepareStatement( sql );
      
      if ( args != null ) {
        for ( i = 0; i < args.length; i++ ) {
          preparedStatement.setString( ( i + 1 ), args[i] );
	    }
	  }
      
      rs = preparedStatement.executeQuery();
    }
    catch ( SQLException sqle ) {
      sqle.printStackTrace();
    }

    return rs;
  }
 
  public int update( String sql ) {
    int result = 0;

    try {
      // result = statement.executeUpdate( sql );
      
      preparedStatement = connection.prepareStatement( sql );
      result = preparedStatement.executeUpdate();
    }
    catch ( SQLException sqle ) {
      sqle.printStackTrace();
    }

    return result;
  }
  public void closeConnection() {
    try {
      if ( connection != null ) {
        connection.close();
      }
    }
    catch ( SQLException sqle ) {
      sqle.printStackTrace();
    }
  }
}

class TestJDBC {
  public static void main(String[] args) throws SQLException {
    Hw10 dbc = new Hw10();
    String sql, list[];
    ResultSet rs;
    Connection connect = null;


    dbc.openConnection();
    
    Scanner input = new Scanner(System.in);
    String exp; 
    int exit;
    do
    {
    	System.out.println("Are you an experienced SQL user? Yes or No");
    	exp = input.next();
    	
    	if (exp.matches("Yes"))
    	{
    		System.out.println("Enter a complete SQL command");
    		input.nextLine();
    		String sqlCom = input.nextLine();
    		
    		rs = dbc.query(sqlCom, null);


    	    ResultSetMetaData rsMetaData = rs.getMetaData();

    	    int numberOfColumns = rsMetaData.getColumnCount();

    	    
    		try {
        		
//	    	    for (int i = 1; i <= numberOfColumns; i++) 
//	    	    {
//	    	      System.out.println(rsMetaData.getColumnName(i));
//	    	    }
	    	    
	    	    int count = 1;
//	    	    while(rs.next())
//	    	    {
//	    	    	String columnName = rsMetaData.getColumnName(count);
//	    	    	System.out.println(columnName);
//	    	    	count++;
//	    	    	
//	    	    }
	    	    while (rs.next()) {
	 		       for (int i = 1; i <= numberOfColumns; i++) {
	 		           if (i > 1) System.out.print(",  ");
	 		           String columnValue = rs.getString(i);
	 		           System.out.print(columnValue + " ");
	 		       }
	 		       System.out.println("");
	 		   }
			} catch (SQLException e) {}
    		
//		   System.out.println("querying SELECT * FROM XXX");
//		   rsMetaData = rs.getMetaData();
//		   int columnsNumber = rsMetaData.getColumnCount();
//		   System.out.println(columnsNumber);
//		   while (rs.next()) {
//		       for (int i = 1; i <= columnsNumber; i++) {
//		           if (i > 1) System.out.print(",  ");
//		           String columnValue = rs.getString(i);
//		           System.out.print(columnValue + " ");
//		       }
//		       System.out.println("");
//		   }
    		
    	}
    	
    	else if (exp.matches("No"))
    	{
    		System.out.println("Select a table by typing in its corresponding number");
    		
//    		rs = dbc.query("SHOW TABLES;", null);
//    		try 
//    		{
//    			while ( rs.next() ) 
//    			{
//    				System.out.println(rs.getString("Tables_in_mall"));
//    			}
//    	      System.out.println();
//    		}
//    	    catch ( SQLException sqle ) {}
    		
    		System.out.println("1. Accounts");
    		System.out.println("2. Customers");
    		System.out.println("3. Employees");
    		System.out.println("4. Employs");
    		System.out.println("5. Inventory");
    		System.out.println("6. Items");
    		System.out.println("7. Stores");
    		System.out.println("8. Transactions");
    		
    		int table;
    		table = input.nextInt();
    		
    		if (table < 1 || table > 8)
    		{
    			boolean typo = true; 
    			
    			do 
    			{
    				System.out.println("Error: Could not find table. Please select again");
    				System.out.println("Accounts");
    	    		System.out.println("Customers");
    	    		System.out.println("Employees");
    	    		System.out.println("Employs");
    	    		System.out.println("Inventory");
    	    		System.out.println("Items");
    	    		System.out.println("Stores");
    	    		System.out.println("Transactions");
    	    		
    	    		table = input.nextInt();
    	    		
    	    		if (table < 1 || table > 8)
	    				{
	    					typo = false;
	    				}
    	    		
    				
    			}while (typo);
    		}
    		    	    
    		String tableStr = " ";
    		switch(table)
    		{
    		case 1: tableStr = "Accounts";
    		break;
    		
    		case 2: tableStr = "Customers";
    		break;
    		
    		case 3: tableStr = "Employees";
    		break;
    		
    		case 4: tableStr = "Employs";
    		break;

    		case 5: tableStr = "Inventory";
    		break;
    		
    		case 6: tableStr = "Items";
    		break;
    		
    		case 7: tableStr = "Stores";
    		break;
    		
    		case 8: tableStr = "Transactions";
    		break;
    		
    		}
    		
    		System.out.println("You have selected " + table + ": " + tableStr);

    	
			try {
	    	    rs = dbc.query("SELECT * FROM "+ tableStr, null);

	    	    ResultSetMetaData rsMetaData = rs.getMetaData();

	    	    int numberOfColumns = rsMetaData.getColumnCount();

	    	    for (int i = 1; i <= numberOfColumns; i++) 
	    	    {
	    	      System.out.println(rsMetaData.getColumnName(i));
	    	    }
			} catch (SQLException e) {}
    		
			System.out.println("How many columns do you want data from?");
			int numCol = input.nextInt();
			
			System.out.println("Please pick which columns whose tuples you want");
			
			String tuple1;
			String tuple2;
			String tuple3;
			String tuple4;
			String tuple5;
			
			if (numCol == 1)
			{
				tuple1 = input.next();
				rs = dbc.query("SELECT " + tuple1 + " FROM " + tableStr + ";", null);
	    		try 
	    		{
	    			while ( rs.next() ) 
	    			{
	    				System.out.println(rs.getString(tuple1));
	    				System.out.println(" ");
	    			}
	    	      System.out.println();
	    		}
	    	    catch ( SQLException sqle ) {}
				
			}
			
			else if (numCol == 2)
			{
				tuple1 = input.next();
				tuple2 = input.next();
				
				rs = dbc.query("SELECT " + tuple1 + ", " + tuple2 + " FROM " + tableStr + ";", null);
	    		try 
	    		{
	    			while ( rs.next() ) 
	    			{
	    				System.out.println(rs.getString(tuple1));
	    				System.out.println(rs.getString(tuple2));
	    				System.out.println(" ");
	    			}
	    	      System.out.println();
	    		}
	    	    catch ( SQLException sqle ) {}
			}

			else if (numCol == 3)
			{
				tuple1 = input.next();
				tuple2 = input.next();
				tuple3 = input.next();
				
				rs = dbc.query("SELECT " + tuple1 + ", " + tuple2 + ", " + tuple3 + " FROM " + tableStr + ";", null);
	    		try 
	    		{
	    			while ( rs.next() ) 
	    			{
	    				System.out.println(rs.getString(tuple1));
	    				System.out.println(rs.getString(tuple2));
	    				System.out.println(rs.getString(tuple3));
	    				System.out.println(" ");
	    			}
	    	      System.out.println();
	    		}
	    	    catch ( SQLException sqle ) {}
			}
			
			else if (numCol == 4)
			{
				tuple1 = input.next();
				tuple2 = input.next();
				tuple3 = input.next();
				tuple4 = input.next();

				rs = dbc.query("SELECT " + tuple1 + ", " + tuple2 + ", " + tuple3 + ", " + tuple4 + " FROM " + tableStr + ";", null);
	    		try 
	    		{
	    			while ( rs.next() ) 
	    			{
	    				System.out.println(rs.getString(tuple1));
	    				System.out.println(rs.getString(tuple2));
	    				System.out.println(rs.getString(tuple3));
	    				System.out.println(rs.getString(tuple4));
	    				System.out.println(" ");
	    			}
	    	      System.out.println();
	    		}
	    	    catch ( SQLException sqle ) {}
			}
		
			else if (numCol == 5)
			{
				tuple1 = input.next();
				tuple2 = input.next();
				tuple3 = input.next();
				tuple4 = input.next();
				tuple5 = input.next();

				rs = dbc.query("SELECT " + tuple1 + ", " + tuple2 + ", " + tuple3 + ", " + tuple4 + ", " + tuple5 + " FROM " + tableStr + ";", null);
	    		try 
	    		{
	    			while ( rs.next() ) 
	    			{
	    				System.out.println(rs.getString(tuple1));
	    				System.out.println(rs.getString(tuple2));
	    				System.out.println(rs.getString(tuple3));
	    				System.out.println(rs.getString(tuple4));
	    				System.out.println(rs.getString(tuple5));
	    				System.out.println(" ");
	    			}
	    	      System.out.println();
	    		}
	    	    catch ( SQLException sqle ) {}
			}
			
			
			
    	}
    	
    	System.out.println("Press 0 to exit. Press any other numbers to continue");
    	exit = input.nextInt();
    	
    }while(exit != 0);
  
    dbc.closeConnection();
  }
}
