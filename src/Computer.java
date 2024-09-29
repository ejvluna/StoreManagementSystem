/**
 * Represents a computer with various attributes such as uniqueId, brand, cpuFamily, price, memorySizeGB, ssdCapacityGB, and type.
 * Each computer has a unique identifier, brand, CPU family, price, memory size in GB, SSD capacity in GB, and a type (e.g., Gaming, Business, etc.).
 * The class provides getter and setter methods for each attribute.
 */

public class Computer {
    // 1. declare the fields of the class
    private String category; // to store the category of the computer
    private String type; // to store the type of the computer (e.g. Gaming, Business, etc.)
    private String uniqueId; // to store a unique identifier for the computer
    private String brand; // to store the brand of the computer
    private String cpuFamily; // to store the CPU family of the computer
    private int memorySizeGB; // to store the memory size of the computer in GB
    private int ssdCapacityGB; // to store the SSD capacity of the computer in GB
    private double screenSize; // to store the screen size of certain computers (e.g. laptops, tablets)
    private double price; // to store the price of the computer

    // 2. declare a constructor to create a new computer object
    public Computer(String category, String type, String uniqueId, String brand, String cpuFamily, int memorySizeGB, int ssdCapacityGB, double screenSize, double price) {
        // call class methods to set the values of the fields from the parameters
        setCategory(category);
        setType(type);
        setUniqueId(uniqueId);
        setBrand(brand);
        setCpuFamily(cpuFamily);
        setMemorySizeGB(memorySizeGB);
        setSsdCapacityGB(ssdCapacityGB);
        setScreenSize(screenSize);
        setPrice(price);
    }

    // 3. declare the getter and setter methods for the fields

    // a public method to get the category of the computer
    public String getCategory() {
        return category;
    }
    // a public method to set the category of the computer
    public void setCategory(String category) {
        this.category = category;
    }
    // a public method to get the type of the computer
    public String getType() {
        return type;
    }
    // a public method to set the type of the computer
    public void setType(String type) {
        this.type = type;
    }
    // a public method to get the uniqueId of the computer
    public String getUniqueId() {
        return uniqueId;
    }
    // a public method to set the uniqueId of the computer
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    // a public method to get the brand of the computer
    public String getBrand() {
        return brand;
    }
    // a public method to set the brand of the computer
    public void setBrand(String brand) {
        this.brand = brand;
    }
    // a public method to get the cpuFamily of the computer
    public String getCpuFamily() {
        return cpuFamily;
    }
    // a public method to set the cpuFamily of the computer
    public void setCpuFamily(String cpuFamily) {
        this.cpuFamily = cpuFamily;
    }
    // a public method to get the memorySizeGB of the computer
    public int getMemorySizeGB() {
        return memorySizeGB;
    }
    // a public method to set the memorySizeGB of the computer
    public void setMemorySizeGB(int memorySizeGB) {
        this.memorySizeGB = memorySizeGB;
    }
    // a public method to get the ssdCapacityGB of the computer
    public int getSsdCapacityGB() {
        return ssdCapacityGB;
    }
    // a public method to set the ssdCapacityGB of the computer
    public void setSsdCapacityGB(int ssdCapacityGB) {
        this.ssdCapacityGB = ssdCapacityGB;
    }
    // a public method to get the price of the computer
    public double getScreenSize() {
        return screenSize;
    }
    // a public method to set the price of the computer
    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }
    // a public method to get the price of the computer
    public double getPrice() {
        return price;
    }
    // a public method to set the price of the computer
    public void setPrice(double price) {
        this.price = price;
    }

    // 4. class override methods

    @Override
    // on override the toString method to return a string representation of the computer object
    public String toString() {
        return "Computer{" +
                "Category='" + category + '\'' +
                ", Type='" + type + '\'' +
                ", Id='" + uniqueId + '\'' +
                ", Brand='" + brand + '\'' +
                ", CpuFamily='" + cpuFamily + '\'' +
                ", Price=" + price +
                '}';
    }

}