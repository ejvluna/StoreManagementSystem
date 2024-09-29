/**
 * Represents a Laptop which is a subclass of the 'Computer' class.
 * Each Laptop has attributes inherited from the 'Computer' class such as uniqueId, brand, cpuFamily, price, memorySizeGB, ssdCapacityGB, and type.
 * In addition, a Laptop has a specific attribute 'screenSizeInches' representing the screen size of the laptop in inches.
 * The class provides getter and setter methods for each attribute inherited from the 'Computer' class and the specific attribute of the 'Laptop' class.
 */

public class Laptop extends Computer {

    // 1. declare the fields specific to a laptop

    // 2. declare a constructor to create a new laptop object
    public Laptop(String category, String type, String uniqueId, String brand, String cpuFamily, int memorySizeGB, int ssdCapacityGB, double screenSize, double price) {
        super(category, type, uniqueId, brand, cpuFamily, memorySizeGB, ssdCapacityGB, screenSize, price);
    }

    // 3. define class override methods

    @Override
    // a public method to override the toString method to return the details specific to a laptop (e.g. general details + memory size + SSD capacity + screen size)
    public String toString() {
        // call the superclass overridden method to get the general details of the laptop and then add the Laptop specific details
        return super.toString() +
                ", memorySizeGB=" + getMemorySizeGB() +
                ", ssdCapacityGB=" + getSsdCapacityGB() +
                ", screenSize=" + getScreenSize() +
                '}';
    }
}