package PreProject;

/**
 * Class Tool represents an item in the inventory. It has an item name, ID,
 * quantity, price, and the supplier info.
 * 
 * @author Sanyam
 *
 */

public class Tool {

	/**
	 * itemID represents the ID of the item. Each item has unique ID
	 */
	private int ID;

	/**
	 * itemName represents the name of the item
	 */
	private String name;

	/**
	 * itemQuantity tells the quantity of the item in the inventory
	 */
	private int quantity;

	/**
	 * itemPrice tells the price of the item
	 */
	private double price;

	/**
	 * supplierID represents the ID of the supplier
	 */
	private int supplierID;

	/**
	 * Constructs a constructor of Items and assigns all the values to its member
	 * variables
	 * 
	 * @param itemID       iD of an item
	 * @param itemName     name of an item
	 * @param itemQuantity quantity left in the stock
	 * @param itemPrice    price of an item
	 * @param supplierID   iD of the supplier that sells that item
	 * @param sup          object of Supplier corresponding to that item
	 */
	public Tool(int itemID, String itemName, int itemQuantity, double itemPrice, int supplierID) {

		this.setID(itemID);
		this.setName(itemName);
		this.setPrice(itemPrice);
		this.setQuantity(itemQuantity);
		this.setSupplierID(supplierID);

	}

	@Override
	public String toString() {

		return this.getID() + " " + this.getName() + " " + this.getQuantity() + " " + this.getPrice() + " "
				+ this.getSupplierID();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

}
