/**
 * Represents a Salesperson which is a subclass of the 'Staff' class.
 * Each Salesperson has attributes inherited from the 'Staff' class such as username and password.
 * The class provides getter and setter methods for each attribute inherited from the 'Staff' class.
 * Additional attributes and methods specific to a Salesperson can be added in this class.
 */

public class Salesperson extends Staff {

    // 1. declare the fields (attributes) specific to the Salesperson class

    // 2. declare a class constructor to create a new Salesperson object
    public Salesperson(String username, String password) {
        // call the constructor of the superclass (Staff) to initialize inherited fields (username and password) from parameters
        super(username, password, "Salesperson");
    }

    // 3. declare method specific to the Salesperson class



}