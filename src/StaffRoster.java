import java.util.ArrayList; // to use the ArrayList class to create dynamic arrays
import java.util.List; // to use the List interface to create lists

public class StaffRoster {
    // 1. declare the fields (attributes) specific to the StaffRoster class
    private List<Staff> staffList; // a List to store Staff objects

    // 2. declare a class constructor to create a new StaffRoster object
    public StaffRoster() {
        // initialize the staffList field as an empty ArrayList to store Staff objects
        this.staffList = new ArrayList<>();
    }
    // 3. declare the getter and setter methods for the fields specific to the StaffRoster class

    // a public method to add a new staff member to the staff roster
    public void addStaff(Staff staff) {
        this.staffList.add(staff);
    }

    // a public method to display all staff members in the system
    public void displayAllStaff() {
        for (Staff staff : this.staffList) {
            System.out.println(staff.toString());
        }
    }

    // a public method to return all staff members in the system
    public List<Staff> getAllStaff() {
        return this.staffList;
    }

    // a helper method to find a staff member by username and password
    private Staff findStaff(String username, String password) {
        for (Staff staff : staffList) {
            if (staff.getUsername().equals(username) && staff.getPassword().equals(password)) {
                return staff;
            }
        }
        return null; // return null if no matching staff member is found
    }


    // a public method to validate a user based on the username and password
    public Staff validateUser(String username, String password) {
        // Use the findStaff method to check if a staff member with the given username and password exists
        return findStaff(username, password);
    }

}