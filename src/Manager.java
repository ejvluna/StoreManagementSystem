/**
 * Represents a Manager which is a subclass of the 'Staff' class.
 * Each Manager has attributes inherited from the 'Staff' class such as username and password.
 * The class provides getter and setter methods for each attribute inherited from the 'Staff' class.
 * In addition, a Manager has specific methods to add, delete, and update computers in the inventory.
 */

public class Manager extends Staff {

    // 1. declare the fields (attributes) specific to the Manager class

    // 2. declare a class constructor to create a new Manager object
    public Manager(String username, String password) {
        super(username, password, "Manager");
    }

    // 3. declare the getter and setter methods for the fields specific to the Manager class


}