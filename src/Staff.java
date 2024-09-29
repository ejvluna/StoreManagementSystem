import java.util.Collection; // to use the Collection interface (e.g. to return search results)

/**
 * Represents a staff member with attributes such as username and password.
 * Each staff member has a unique username and password.
 * The class provides getter and setter methods for each attribute.
 * Staff members can view all computers in the inventory and search for computers by category and type.
 * The class serves as a superclass for other types of staff like Salesperson and Manager.
 */

public class Staff {
    // 1. declare the fields (attributes) specific to the Staff class
    private String username; // to store the username of the staff member
    private String password; // to store the password of the staff member
    private String type; // to store the type of staff member (e.g. Salesperson, Manager)

    // 2. declare a class constructor to create a new Staff object
    public Staff(String username, String password, String type) {
        setUsername(username);
        setPassword(password);
        setType(type);
    }

    // 3. declare the getter and setter methods for the fields specific to the Staff class

    // a public method to get the username of the staff member
    public String getUsername() {
        return username;
    }
    // a public method to set the username of the staff member
    public void setUsername(String username) {
        this.username = username;
    }
    // a public method to get the password of the staff member
    public String getPassword() {
        return password;
    }
    // a public method to set the password of the staff member
    public void setPassword(String password) {
        this.password = password;
    }
    // a public method to get the type of the staff member
    public String getType() {
        return type;
    }
    // a public method to set the type of the staff member
    public void setType(String type) {
        this.type = type;
    }



    //4 . other methods specific to the Staff class

/*
    // a public method to view all computers in the inventory
    public void viewComputers(ComputerInventory inventory) {
        // iterate over all the Computer objects in the inventory and print their details
        for (Computer computer : inventory.getAllComputers()) {
            System.out.println(computer);
        }
    }
*/

    @Override
    // a public overridden method to return a string representation of the Staff object
    public String toString() {
        return "Staff{" +
                "Username='" + getUsername() + '\'' +
                ", Type='" + getType() + '\'' +
                '}';
    }
    public void login() {
        // Implementation to be discussed later
    }
}