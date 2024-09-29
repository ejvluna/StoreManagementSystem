/**
 * Represents a Desktop PC which is a subclass of the 'Computer' class.
 * Each Desktop PC has attributes inherited from the 'Computer' class such as uniqueId, brand, cpuFamily, price, memorySizeGB, ssdCapacityGB, and type.
 * The class provides getter and setter methods for each attribute inherited from the 'Computer' class.
 * Additional attributes specific to a Desktop PC can be added in this class.
 */

public class DesktopPC extends Computer {
    // 1. declare the fields specific to a desktop PC

    // 2. declare a constructor to create a new desktop PC object
    public DesktopPC(String category, String type, String uniqueId, String brand, String cpuFamily,  int memorySizeGB, int ssdCapacityGB, double screenSize, double price) {
        super(category, type, uniqueId, brand, cpuFamily, memorySizeGB, ssdCapacityGB, 0, price);
    }

    // 3. declare the getter and setter methods for the fields specific to a desktop PC

    @Override
    // a public method to override the toString method to return the details of the desktop PC (e.g. general details + memory size + SSD capacity)
    public String toString() {
        return super.toString() +
                ", memorySizeGB=" + getMemorySizeGB() +
                ", ssdCapacityGB=" + getSsdCapacityGB() +
                '}';
    }

}