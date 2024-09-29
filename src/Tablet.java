/**
 * Represents a Tablet which is a subclass of the 'Computer' class.
 * Each Tablet has attributes inherited from the 'Computer' class such as uniqueId, brand, cpuFamily, price, memorySizeGB, ssdCapacityGB, and type.
 * In addition, a Tablet has a specific attribute 'screenSizeInches' representing the screen size of the tablet in inches.
 * The class provides getter and setter methods for each attribute inherited from the 'Computer' class and the specific attribute of the 'Tablet' class.
 */

public class Tablet extends Computer {

    // 1 declare the fields specific to a tablet

    // 2. declare a constructor to create a new tablet object
    public Tablet(String category, String type, String uniqueId, String brand, String cpuFamily, int memorySizeGB, int ssdCapacityGB, double screenSize, double price) {
        super(category, type, uniqueId, brand, cpuFamily, memorySizeGB, ssdCapacityGB, screenSize, price);
    }

    // 3. define class override methods

    @Override
    // a public method to override the toString method to return the details specific to a tablet (e.g. general details + screen size)
    public String toString() {
        // call the superclass overridden method to get the general details of the tablet and then add the Tablet specific details
        return super.toString() +
                ", screenSize=" + getScreenSize() +
                '}';
    }
}