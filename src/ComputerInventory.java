import java.util.HashMap; // to create HashMap data structures (e.g. to store Computer objects)
import java.util.ArrayList; // to create ArrayList data structures (e.g. to store search results)
import java.util.Collection; // to use the Collection interface (e.g. to return search results)

/**
 * Represents an inventory system for computers with various operations such as adding, deleting, updating, and retrieving computers.
 * The inventory is stored as a HashMap where the key is the unique ID of the computer and the value is the Computer object itself.
 * The class provides methods to add a new computer to the inventory, delete a computer from the inventory, update the details of a computer in the inventory, and get a computer from the inventory based on its unique ID.
 * It also provides methods to get all computers in the inventory and search for computers by category and type.
 */

public class ComputerInventory {

    // 1. declare the fields (attributes) specific to the ComputerInventory class
    private HashMap<String, Computer> computers; // a HashMap to store Computer objects with unique IDs

    // 2. declare a class constructor to create a new ComputerInventory object
    public ComputerInventory() {
        // initialize the computers field as an empty HashMap to store Computer objects
        this.computers = new HashMap<>();
        // a hashmap is a collection that stores key-value pairs
    }

    // 3. declare the getter and setter methods for the fields specific to the ComputerInventory class

    // a public method to add a new computer to the inventory
    public void addComputer(Computer computer) {
        this.computers.put(computer.getUniqueId(), computer);
    }
    // a public method to remove a computer from the inventory
    public void deleteComputer(String uniqueId) {
        this.computers.remove(uniqueId);
    }
    // a public method to update the details of a computer in the inventory
    public void updateComputer(String uniqueId, Computer updatedComputer) {
        if (this.computers.containsKey(uniqueId)) {
            this.computers.put(uniqueId, updatedComputer);
        } else {
            throw new IllegalArgumentException("Computer with unique ID " + uniqueId + " does not exist in the inventory.");
        }
    }


    // a public method to get all computers in the inventory
    public Collection<Computer> getAllComputers() {
        return this.computers.values();
    }

    // a public method to search for computers in the inventory based on a specific category (e.g. Desktop PC, Laptop, Tablet)
    public Collection<Computer> searchByCategory(String category) {
        // create an empty ArrayList to store the search results
        Collection<Computer> result = new ArrayList<>();
        // iterate over all the values (Computer objects) in the HashMap (inventory). For each object (computer) do the following:
        for (Computer computer : this.computers.values()) {
            // compare the category parameter with the computer's category
            if (computer.getCategory().equals(category)) {
                // if a match is found, add the computer to the result ArrayList
                result.add(computer);
            }
        }
        // return the search results
        return result;
    }

    // a public method to search for computers in the inventory based on a specific type (e.g. Gaming, Business, etc.)
    public Collection<Computer> searchByType(String type) {
        // create an empty ArrayList to store the search results
        Collection<Computer> result = new ArrayList<>();
        // iterate over all the values (Computer objects) in the HashMap (inventory). For each object (computer) do the following:
        for (Computer computer : this.computers.values()) {
            // compare the type parameter with the computer's type, if a match is found, add the computer to the result ArrayList
            if (computer.getType().equals(type)) {
                result.add(computer);
            }
        }
        // return the search results
        return result;
    }

    // a public method to search for computers in the inventory based on both a specific category and type
    public Collection<Computer> searchByCategoryAndType(String category, String type) {
        // create an empty ArrayList to store the search results
        Collection<Computer> result = new ArrayList<>();
        // iterate over all the values (Computer objects) in the HashMap (inventory). For each object (computer) do the following:
        for (Computer computer : this.computers.values()) {
            // check if the category and type parameters match the computer's category and type
            if (computer.getCategory().equals(category) && computer.getType().equals(type)) {
                // if so, add the computer to the result ArrayList
                result.add(computer);
            }
        }
        // return the search results
        return result;
    }

    // a public method to search for computers in the inventory based on the unique ID
    public Computer searchByUniqueId(String uniqueId) {
        return this.computers.get(uniqueId);
    }

    // a public method to get a computer from the inventory based on its unique ID
    public Computer getComputer(String uniqueId) {
        return this.computers.get(uniqueId);
    }
}