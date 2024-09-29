import java.io.BufferedReader; // to use a buffered reader to read text from a character-input stream
import java.io.FileReader; // to use a file reader to read files
import java.io.IOException; // to use the IOException class to handle input/output exceptions

/**
 * This is the main class for a PC Store Management System.
 * The system manages an inventory of computers and a roster of staff members.
 * The inventory includes different types of computers such as Desktop PCs, Laptops, and Tablets.
 * Each computer has attributes like category, type, id, brand, cpuFamily, memorySizeGB, ssdCapacityGB, screenSize, and price.
 * The staff roster includes different types of staff members such as Salespersons and Managers.
 * Each staff member has attributes like username, password, and type.
 * The system provides functionality to import inventory data from a file, add staff to the system, view all computers in the inventory, and search for computers by category and type.
 * The system also provides functionality for a Manager to add, delete, and update computers in the inventory.
 */

public class Main {
    // the main method to run the program
    public static void main(String[] args) {

        // === Phase 1: Data Structures and Data Import ===
        // create a new ComputerInventory object to store the computers
        ComputerInventory inventory = new ComputerInventory();
        // create a new StaffRoster object to store the staff
        StaffRoster staffRoster = new StaffRoster();
        // import the inventory data from the file computers.txt
        importData(inventory);
        // add staff to the system per the system description test cases using the StaffRoster class
        staffRoster.addStaff(new Salesperson("s1", "s1"));
        staffRoster.addStaff(new Salesperson("s2", "s2"));
        staffRoster.addStaff(new Salesperson("s3", "s3"));
        staffRoster.addStaff(new Manager("m1", "m1"));
        staffRoster.addStaff(new Manager("m2", "m2"));

        // === Section 2: Run Graphical User Interface (GUI) ===

        // create and display the Graphical User Interface (GUI)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // create a new PCStoreGUI object and pass the inventory object to the constructor
                PCStoreGUI gui = new PCStoreGUI(inventory);
                // create and show the Graphical User Interface (GUI)
                gui.createAndShowGUI();
                // create and show the login panel to validate the user
                gui.createLoginPanel(staffRoster); // pass the staffRoster object to the createLoginPanel method
            }
        }); // end of Event Dispatch Thread
    }

    // a public method to import data from a file
    public static void importData(ComputerInventory inventory) {
        // use a try-catching block to handle IOExceptions when reading the file
        try (BufferedReader br = new BufferedReader(new FileReader("computers.txt"))) {
            String line; // to store each line read from the file
            // start a loop to run until there are no more lines to read from the file (e.g. end of file)
            while ((line = br.readLine()) != null) {
                // use a try-catch block to handle IllegalArgumentExceptions when parsing the line
                try {
                    // get the data from each line using a helper method and store it in a new Computer object
                    Computer computer = parseLine(line);
                    // add the new Computer object to the inventory
                    inventory.addComputer(computer);
                // if an IllegalArgumentException is caught, print an error message with the line and the exception message
                } catch (IllegalArgumentException e) {
                    System.out.println("Error parsing line: " + line);
                    System.out.println("Error message: " + e.getMessage());
                }
            }
        // if an IOException is caught, print an error message with the exception message
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // a public method to parse a line from the file computers.txt
    public static Computer parseLine(String line) throws IllegalArgumentException {
        // create an array of strings to store a full input line
        String[] parts = line.split(","); // set a comma as the delimiter to split the line into parts
        String category = parts[0]; // set the category of the computer from the first element in the array
        String type = parts[1]; // set the type of the computer from the second element in the array
        String id = parts[2]; // set the uniqueId of the computer from the third element in the array
        String brand = parts[3]; // set the brand of the computer from the fourth element in the array
        String cpuFamily = parts[4]; // set the cpuFamily of the computer from the fifth element in the array
        // declare variables to store certain attributes as numeric values (e.g. to facilitate future calculations, if desired)
        int memorySizeGB = 0; // to store the memory size
        int ssdCapacityGB = 0; // to store the SSD capacity
        double screenSize = 0; // to store the screen size
        double price = 0; // to store the price
        // use a try-catch block to handle NumberFormatExceptions when parsing numeric values for all categories
        try {
            // set the price of the computer from the last element in the array as this applies to all categories
            price = Double.parseDouble(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            // if a number format exception occurs when parsing the price, throw an IllegalArgumentException and print an error message
            throw new IllegalArgumentException("Invalid price: " + parts[parts.length - 1]);
        }
        // check the category of the computer and create a new computer object based on the category
        switch (category) {
            // Case 1: if the category is "Desktop PC"
            case "Desktop PC":
                // check if the input line has at least 8 elements to match the required format for a Desktop PC
                if (parts.length < 8) {
                    // if the input line does not have enough elements, throw an IllegalArgumentException
                    throw new IllegalArgumentException("Invalid input line for Desktop PC: " + line);
                }
                // use a try-catch block to handle NumberFormatExceptions when parsing numeric values
                try {
                    memorySizeGB = Integer.parseInt(parts[5]); // set the memorySizeGB of the DesktopPC from the sixth element in the array
                    ssdCapacityGB = Integer.parseInt(parts[6]); // set the ssdCapacityGB of the DesktopPC from the seventh element in the array
                } catch (NumberFormatException e) {
                    // if the memorySizeGB or ssdCapacityGB cannot be parsed due to an invalid format, throw an IllegalArgumentException
                    throw new IllegalArgumentException("Invalid memory size or SSD capacity: " + parts[5] + ", " + parts[6]);
                }
                // create and return a new DesktopPC object with the parsed values
                return new DesktopPC(category, type, id, brand, cpuFamily, memorySizeGB, ssdCapacityGB, 0, price);

            // Case 2: if the category is "Laptop"
            case "Laptop":
                // check if the input line has at least 9 elements to match the required format for a Laptop
                if (parts.length < 9) {
                    // if the input line does not have enough elements, throw an IllegalArgumentException
                    throw new IllegalArgumentException("Invalid input line for Laptop: " + line);
                }
                // use a try-catch block to handle NumberFormatExceptions when parsing numeric values
                try {
                    memorySizeGB = Integer.parseInt(parts[5]);  // set the memorySizeGB of the Laptop from the sixth element in the array
                    ssdCapacityGB = Integer.parseInt(parts[6]); // set the ssdCapacityGB of the Laptop from the seventh element in the array
                    screenSize = Double.parseDouble(parts[7]); // set the screenSize of the Laptop from the eighth element in the array
                } catch (NumberFormatException e) {
                    // if the memorySizeGB, ssdCapacityGB, or screenSize cannot be parsed due to an invalid format, throw an IllegalArgumentException
                    throw new IllegalArgumentException("Invalid memory size, SSD capacity, or screen size: " + parts[5] + ", " + parts[6] + ", " + parts[7]);
                }
                // create and return a new Laptop object with the parsed values
                return new Laptop(category, type, id, brand, cpuFamily, memorySizeGB, ssdCapacityGB, screenSize, price);

            // Case 3: if the category is "Tablet"
            case "Tablet":
                // check if the input line has at least 7 elements to match the required format for a Tablet
                if (parts.length < 7) {
                    // if the input line does not have enough elements, throw an IllegalArgumentException
                    throw new IllegalArgumentException("Invalid input line for Tablet: " + line);
                }
                // use a try-catch block to handle NumberFormatExceptions when parsing numeric values
                try {
                    screenSize = Double.parseDouble(parts[5]); // set the screenSize of the Tablet from the sixth element in the array
                } catch (NumberFormatException e) {
                    // if the screenSize cannot be parsed due to an invalid format, throw an IllegalArgumentException
                    throw new IllegalArgumentException("Invalid screen size: " + parts[5]);
                }
                // create and return a new Tablet object with the parsed values
                return new Tablet(category, type, id, brand, cpuFamily, 0, 0, screenSize, price);

            // Default Case: if none of the valid categories are matched
            default:
                // throw an IllegalArgumentException with an error message indicating an invalid category
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }
}