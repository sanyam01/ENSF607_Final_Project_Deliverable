package PreProject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.*;

/**
 * 
 * Inventory Manager allows you to create and manage a store inventory database.
 * It creates a database and data table, then populates that table with tools
 * from items.txt.
 * 
 * @author Sanyam, Neha
 */
public class InventoryManager {

	/**
	 * jdbc_connection is of type Connection for making connection with mysql
	 */
	public Connection jdbc_connection;

	/**
	 * statement is of type Statement, which helps in querying the database
	 */
	public Statement statement;

	/**
	 * databaseName represents the database name tableName gives the name of the
	 * table in the databse dataFile contains the information of the database
	 */
	public String databaseName = "InventoryDB", tableName = "ToolTable", dataFile = "items.txt";

	/**
	 * connectionInfo has the login ID and password for connecting to mysql
	 */
	public String connectionInfo = "jdbc:mysql://localhost:3306/InventoryDB", login = "****", password = "****";

	/**
	 * InventoryManager() constructs the constructor of the class InventoryManager
	 */
	public InventoryManager() {
		try {
			
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
			System.out.println("Connected to: " + connectionInfo + "\n");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * createDB uses the jdbc connection to create a new database in MySQL
	 */
	public void createDB() {
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate("CREATE DATABASE " + databaseName);
			System.out.println("Created Database " + databaseName);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a data table inside of the database to hold tools
	 */
	public void createTable() {
		String sql = "CREATE TABLE " + tableName + "(" + "ID INT(4) NOT NULL, " + "TOOLNAME VARCHAR(20) NOT NULL, "
				+ "QUANTITY INT(4) NOT NULL, " + "PRICE DOUBLE(5,2) NOT NULL, " + "SUPPLIERID INT(4) NOT NULL, "
				+ "PRIMARY KEY ( ID ))";

		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Created Table " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes the data table from the database (and all the data held within it!)
	 */
	public void removeTable() {
		String sql = "DROP TABLE " + tableName;
		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
			System.out.println("Removed Table " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fills the data table with all the tools from the text file 'items.txt' if
	 * found
	 */

	public void fillTable() {
		try {
			Scanner sc = new Scanner(new FileReader(dataFile));
			while (sc.hasNext()) {
				String toolInfo[] = sc.nextLine().split(";");

				addItem(new Tool(Integer.parseInt(toolInfo[0]), toolInfo[1], Integer.parseInt(toolInfo[2]),
						Double.parseDouble(toolInfo[3]), Integer.parseInt(toolInfo[4])));

			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("File " + dataFile + " Not Found!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a tool to the database table
	 * 
	 * @param tool represents the tool
	 */
	public void addItem(Tool tool) {
		String sql = "INSERT INTO " + tableName + " VALUES ( " + tool.getID() + ", '" + tool.getName() + "', "
				+ tool.getQuantity() + ", " + tool.getPrice() + ", " + tool.getSupplierID() + ");";

		try {
			statement = jdbc_connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method should search the database table for a tool matching the toolID
	 * parameter and return that tool. It should return null if no tools matching
	 * that ID are found.
	 * 
	 * @param toolID represents the ID of the tool
	 * 
	 * @return the tool if found
	 */
	public Tool searchTool(int toolID) {
		String sql = "SELECT * FROM " + tableName + " WHERE ID=" + toolID;
		ResultSet tool;
		try {
			statement = jdbc_connection.createStatement();
			tool = statement.executeQuery(sql);
			if (tool.next()) {
				return new Tool(tool.getInt("ID"), tool.getString("TOOLNAME"), tool.getInt("QUANTITY"),
						tool.getDouble("PRICE"), tool.getInt("SUPPLIERID"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Prints all the items in the database to console
	 */
	public void printTable() {
		try {
			String sql = "SELECT * FROM " + tableName;
			statement = jdbc_connection.createStatement();
			ResultSet tools = statement.executeQuery(sql);
			System.out.println("Tools:");
			while (tools.next()) {
				System.out.println(tools.getInt("ID") + " " + tools.getString("TOOLNAME") + " "
						+ tools.getInt("QUANTITY") + " " + tools.getDouble("PRICE") + " " + tools.getInt("SUPPLIERID"));
			}
			tools.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		InventoryManager inventory = new InventoryManager();

		// You should comment this line out once the first database is created (either
		// here or in MySQL workbench)
		// inventory.createDB();

		inventory.createTable();

		System.out.println("\nFilling the table with tools");
		inventory.fillTable();

		System.out.println("Reading all tools from the table:");
		inventory.printTable();

		System.out.println("\nSearching table for tool 1002: should return 'Grommets'");
		int toolID = 1002;
		Tool searchResult = inventory.searchTool(toolID);
		if (searchResult == null)
			System.out.println("Search Failed to find a tool matching ID: " + toolID);
		else
			System.out.println("Search Result: " + searchResult.toString());

		System.out.println("\nSearching table for tool 9000: should fail to find a tool");
		toolID = 9000;
		searchResult = inventory.searchTool(toolID);
		if (searchResult == null)
			System.out.println("Search Failed to find a tool matching ID: " + toolID);
		else
			System.out.println("Search Result: " + searchResult.toString());

		System.out.println("\nTrying to remove the table");
		inventory.removeTable();

		try {
			inventory.statement.close();
			inventory.jdbc_connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("\nThe program is finished running");
		}
	}
}