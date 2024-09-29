import javax.swing.*; // to use JFrame, JPanel, JTable, DefaultTableModel, JComboBox, JLabel, JScrollPane, JOptionPane
import java.awt.event.*; // to use ActionListener, MouseEvent (e.g. to handle events)
import javax.swing.table.DefaultTableModel; // to use DefaultTableModel (e.g. to store the data of the computers)
import java.awt.*; // to use the border layout (e.g. to position components in the panel)
import java.awt.event.ActionEvent; // to use the ActionEvent class (e.g. to handle filter events)
import java.util.List; // to use List interface (e.g. to store the search results)
import java.util.ArrayList; // to use the ArrayList class (e.g. to store search results)
import javax.swing.table.DefaultTableCellRenderer; // to use DefaultTableCellRenderer (e.g. to customize the appearance of the table cells)
import java.util.Map; // to use the Map interface
import java.util.HashMap; // to use the HashMap class
import java.util.Arrays; // to use the Arrays class
import javax.swing.table.TableCellEditor; // to use TableCellEditor (e.g. to customize the cell editor)

public class PCStoreGUI {

    // === Section 1: Class Variables (Fields) ===
    private final Map<String, List<String>> typesByCategory = new HashMap<>(); // to store the types of computers by category as a map
    private JFrame frame; // to hold the main window
    private JTable table; // to display the data of the computers
    private JTabbedPane tabbedPane; // to hold multiple tabs (e.g. for browsing and updating products)
    private JDialog loginDialog; // to hold the login panel throughout the session

    private DefaultTableModel browseTableModel; // to store the data of the computers
    private DefaultTableModel detailsTableModel;
    private JComboBox<String> categoryFilter; // to filter the computers by category
    private JComboBox<String> typeFilter; // to filter the computers by type
    private ComputerInventory inventory; // to store the inventory of computers
    private Staff currentUser; // to store the current user
    private boolean isEditable = false;
    private boolean isManager = false; // to check if the current user is a manager
    private boolean isAdding = false; // to allow editing of the 'Item ID' row

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    private static final Color PRIMARY_COLOR = new Color(220, 240, 255); // to set the primary color (light blue)
    private static final Color SECONDARY_COLOR = Color.WHITE; // to set the secondary color (white)
    private static final Color TEXT_COLOR = new Color(50, 50, 50); // to set the text color (dark gray)

    // === Section 2: Constructors ===

    // a constructor to create a new PCStoreGUI object
    public PCStoreGUI(ComputerInventory inventory) {
        // assign the inventory from the parameter to the inventory field for the current PCStoreGUI object
        this.inventory = inventory;
        // initialize the typesByCategory map
        typesByCategory.put("Desktop PC", Arrays.asList("Gaming", "Home & Study", "Business", "Compact"));
        typesByCategory.put("Laptop", Arrays.asList("Gaming", "Home & Study", "Business", "Thin & Light"));
        typesByCategory.put("Tablet", Arrays.asList("Android", "Apple", "Windows"));
    }

    // === Section 3: Methods ===

    // a public method to create and show the Graphical User Interface (GUI)
    public void createAndShowGUI() {
        // create a new frame to hold the main window using helper method #1
        frame = createFrame();
        // create a new tabbed pane to hold multiple tabs (e.g. for browsing and updating products)
        tabbedPane = new JTabbedPane();

        // --- Panel 1: Browse Products ---
        // create a new panel for browsing products using a helper method #2
        JPanel browsePanel = createBrowsePanel(); // also creates a table with the inventory data using helper method #3
        // populate the table model with the data of the computers in the inventory using helper method #4
        populateBrowseTable();
        // create and add filters to the panel (e.g. category and type filters)
        createAndAddFilters(browsePanel);
        // create a 'Switch User' button
        JButton switchUserButton = new JButton("Switch User");
        // add an action listener to the 'Switch User' button
        switchUserButton.addActionListener(e -> {
            loginDialog.setVisible(true); // show the login dialog
        });
        JPanel buttonPanel = new JPanel(); // create a new panel for the button
        buttonPanel.add(switchUserButton); // add the 'Switch User' button
        browsePanel.add(buttonPanel, BorderLayout.SOUTH); // add the button panel to the bottom of the browse panel
        // add the browse panel to the tabbed pane
        tabbedPane.addTab("Browse Products", browsePanel);

        // --- Panel 2: create a new panel for updating products
        // create a new panel for updating products using helper method #8
        JPanel detailsPanel = createDetailsPanel();
        // set the title for the details panel and add it to the tabbed pane
        tabbedPane.addTab("View/Update Product", detailsPanel);

        frame.add(tabbedPane); // add the tabbed pane to the frame
        frame.pack(); // size the frame to fit its content
        frame.setVisible(false); // hide the frame until the user logs in
    }

    // --- Helper Methods ---

    // a public method to create and display the login panel to validate the user
    public void createLoginPanel(StaffRoster staffRoster) {
        // create a new JDialog for the login panel
        loginDialog = new JDialog(frame, "Login", true);
        loginDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE); // hide the dialog when closed
        // create a new JPanel for the login panel
        JPanel loginPanel = new JPanel();
        // create a new JTextField for the username and password with initial width of 20 characters
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        // create a new JButton for the login button
        JButton loginButton = new JButton("Login");
        // create an action listener for the login button to handle when the button is clicked
        loginButton.addActionListener(e -> {
            String username = usernameField.getText(); // get the username from the username field and store it
            String password = new String(passwordField.getPassword()); // get the password from the password field and store it as a string
            Staff user = staffRoster.validateUser(username, password); // validate the user
            // if the user is valid (e.g. the username and password are correct)
            if (user != null) {
                // display a welcome message to the user
                JOptionPane.showMessageDialog(loginDialog, "Your username has been accepted, welcome.", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                currentUser = user; // store the user object for the current session
                frame.setVisible(true); // display the main frame to the user
                loginDialog.setVisible(false); // hide the login dialog
                isManager = "Manager".equals(currentUser.getType()); // check if the user is a manager to determine permissions
                addButton.setEnabled(isManager); // enable the add button if the user is a manager
                updateButton.setEnabled(isManager); // enable the update button if the user is a manager
                deleteButton.setEnabled(isManager); // enable the delete button if the user is a manager
                clearButton.setEnabled(true); // the clear button is available for all users
            } else {
                // display a message dialog when the username or password is not found
                JOptionPane.showMessageDialog(loginDialog, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                // reset the username and password fields so that the user can try again
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        // add the components to the login panel
        loginPanel.add(new JLabel("Username:")); // add a label for the username field
        loginPanel.add(usernameField); // add the username field
        loginPanel.add(new JLabel("Password:")); // add a label for the password field
        loginPanel.add(passwordField); // add the password field
        loginPanel.add(loginButton); // add the login button
        // add the login panel to the content pane of the login dialog
        loginDialog.getContentPane().add(loginPanel); // add the panel to the dialog
        loginDialog.pack(); // size the dialog to fit its content
        loginDialog.setLocationRelativeTo(frame); // center the dialog on the frame
        loginDialog.setVisible(true); // show the dialog
    }

    // a private helper method to create the main frame
    private JFrame createFrame() {
        JFrame frame = new JFrame("PC Store Management System"); // create a new JFrame to hold the main window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set the default close operation of the frame to exit on close
        frame.setSize(1000, 600); // set the width and height of the frame
        return frame; // return the new frame
    }

    // a private helper method to create the browse panel
    private JPanel createBrowsePanel() {
        // create a new panel for browsing products with a border layout
        JPanel panel = new JPanel(new BorderLayout());
        // create a new table model using the createTableModel() method
        browseTableModel = createBrowseTableModel();
        // create a new JTable that uses the table model
        table = new JTable(browseTableModel);
        // set the selection mode of the table to single selection (e.g. to select the whole row when a cell is clicked)
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // use the default cell renderer to customize the appearance of the table cells
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            // an override method to customize the appearance of the table cells
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? PRIMARY_COLOR : SECONDARY_COLOR);
                c.setForeground(TEXT_COLOR); // Use dark gray text
                return c;
            }
        });
        // set the table to auto-resize the columns to fill the available width
        table.setRowHeight(30); // Increase row height for better readability
        // create a mouse listener to handle double-click events on the table
        table.addMouseListener(new MouseAdapter() {
            @Override
            // an override method to handle mouse clicks
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // if the user double-clicks on a row
                    //populateBrowseTable(); // Repopulate the browseTable
                    handleDoubleClick(table); // call a helper method to handle a double click event
                }
            }
        });

        // create a new scroll pane with the table
        JScrollPane scrollPane = new JScrollPane(table);
        // set the border of the scroll pane to an empty border
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // add the scroll pane to the panel
        panel.add(scrollPane, BorderLayout.CENTER);
        // create and add filters to the panel with a helper method
        createAndAddFilters(panel);
        // return the new panel
        return panel;
    }

    // a method to create a new table model for the browse panel
    private DefaultTableModel createBrowseTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        // add 6 columns
        model.addColumn("Category");
        model.addColumn("Type");
        model.addColumn("ID");
        model.addColumn("Brand");
        model.addColumn("CPU Family");
        model.addColumn("Price");
        // return the new table model
        return model;
    }

    // a private helper method to populate the table model with the data of the computers in the inventory
    private void populateBrowseTable() {
        browseTableModel.setRowCount(0);
        List<Computer> computers = new ArrayList<>(inventory.getAllComputers());
        // iterate over the list of computers and for each:
        for (Computer computer : computers) {
            // create a new row with the following data of the computer object
            Object[] row = new Object[]{
                    computer.getCategory(),
                    computer.getType(),
                    computer.getUniqueId(),
                    computer.getBrand(),
                    computer.getCpuFamily(),
                    computer.getPrice()
            };
            // then add the row to the table model
            browseTableModel.addRow(row);
        }
    }

    // a private helper method to create the details table model (e.g. a table with 2 columns and 9 rows)
    private DefaultTableModel createDetailsTableModel() {
        // create a new table model object
        DefaultTableModel model = new DefaultTableModel(
                // create a 2D array with 9 rows and 2 columns
                new Object[][]{
                    {"Item ID", ""},
                    {"Category", ""},
                    {"Type", ""},
                    {"Brand", ""},
                    {"CPU Family", ""},
                    {"Memory Size (GB)", ""},
                    {"SSD Capacity (GB)", ""},
                    {"Screen Size", ""},
                    {"Price", ""}
                },
                // create an array with the column names
                new String[]{"Attribute", "Value"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isEditable && column == 1 && (row != 0 || isAdding);
            }
        };
        return model;
    }

    // a private helper method to create and add filters to the panel
    private void createAndAddFilters(JPanel panel) {
        // create a new panel for the filters (e.g. to visually separate them from the table)
        JPanel filterPanel = new JPanel();

        // 1. Create a category filter
        categoryFilter = createCategoryFilter();
        filterPanel.add(new JLabel("Category:"));
        filterPanel.add(categoryFilter);

        // --- 2. Create a type filter ---
        typeFilter = createTypeFilter("All"); // Initial type filter for "All" categories
        filterPanel.add(new JLabel("Type:"));
        filterPanel.add(typeFilter);

        // --- 3. Add Action Listeners ---
        // add an action listener for when the category filter is selected
        categoryFilter.addActionListener(e -> {
            // get the selected category from the category filter and store it
            String selectedCategory = (String) categoryFilter.getSelectedItem();
            // update the type filter based on selected category (using existing typesByCategory hashmap)
            updateTypeFilter(selectedCategory);
            // filter the displayed computers based on the selected category using helper method #6
            filterByType(e);
        });
        // add an action listener for when the type filter is selected
        typeFilter.addActionListener(this::filterByType);  // filter the displayed computers based on the selected type using helper method #7

        panel.add(filterPanel, BorderLayout.NORTH);

    }

    // a private helper method to create a new filter by type
    private void filterByType(ActionEvent e) {
        // store the current selected category from the category filter
        String selectedCategory = (String) categoryFilter.getSelectedItem();
        // store the current selected type from the type filter
        String selectedType = (String) typeFilter.getSelectedItem();
        // clear the table at the beginning of the search (e.g. to remove the previous search results)
        browseTableModel.setRowCount(0);
        // create a new list to store the search results
        List<Computer> computers;
        // check if the selected type is "All"
        if ("All".equals(selectedType)) {
            // if so, then also check if the selected category is "All"
            if ("All".equals(selectedCategory)) {
                // if both are "All", get all computers from the inventory
                computers = new ArrayList<>(inventory.getAllComputers());
                // otherwise (only the type is "All")
            } else {
                // call a class method to search for computers by category and store the results
                computers = new ArrayList<>(inventory.searchByCategory(selectedCategory));
            }
            // otherwise (the type is not "All")
        } else {
            // check if the selected category is "All"
            if ("All".equals(selectedCategory)) {
                // if so, call a class method to search for computers by type and store the results
                computers = new ArrayList<>(inventory.searchByType(selectedType));
                // otherwise (neither the category nor the type is "All")
            } else {
                // call a class method to search for computers by category and type and store the results
                computers = new ArrayList<>(inventory.searchByCategoryAndType(selectedCategory, selectedType));
            }
        }
        // iterate over the results and for each computer object:
        for (Computer computer : computers) {
            // create a new row with the following data of the computer object
            Object[] row = new Object[]{
                    computer.getCategory(),
                    computer.getType(),
                    computer.getUniqueId(),
                    computer.getBrand(),
                    computer.getCpuFamily(),
                    computer.getPrice()
            };
            // then add the row to the table model
            browseTableModel.addRow(row);
        }
    }

    // a private helper method to create the details panel
    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel(new GridBagLayout()); // create a new panel for the details with a grid bag layout
        GridBagConstraints gbc = new GridBagConstraints(); // create a new object for the grid bag constraints
        // create a new table model for the details panel
        detailsTableModel = new DefaultTableModel(new Object[][]{}, new String[]{"", ""}) {
            @Override
            public boolean isCellEditable(int row, int column) { // an override method to make cells editable
                // cells are editable only when isEditable is true (from the Update button) making the second column. The 'Item ID' row is not editable
                return isEditable && column == 1 && (row != 0 || isAdding);
            }
        };
        detailsTableModel = createDetailsTableModel(); // create a new table model for the details panel
        JTable detailsTable = new JTable(detailsTableModel); // create a new JTable with the table model
        JScrollPane scrollPane = new JScrollPane(detailsTable); // create a new scroll pane with the table
        detailsTable.setRowHeight(40); // Increase row height for better readability
        // create a custom cell renderer to customize the appearance of the table cells
        detailsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? PRIMARY_COLOR : SECONDARY_COLOR);
                c.setForeground(TEXT_COLOR);
                return c;
            }
        });
        // add JScrollPane with detailsTable to detailsPanel
        detailsPanel.add(scrollPane);
        JPanel buttonPanel = createButtonPanel(detailsTableModel, detailsTable); // create a new panel with buttons
        // configure GridBagConstraints for buttonPanel
        gbc.gridy = 2;      // Place in the third row
        gbc.weighty = 0;    // No additional vertical space for the buttonPanel
        detailsPanel.add(buttonPanel, gbc); // add the button panel to the details panel
        return detailsPanel; // return the new details panel
    }

    // a private helper method to create a category filter based on the typesByCategory map
    private JComboBox<String> createCategoryFilter() {
        // create a new combo box for the category filter
        JComboBox<String> categoryFilter = new JComboBox<>();
        // add an "All" option to the category filter as the first item
        categoryFilter.addItem("All");
        // iterate over the categories in the typesByCategory map and for each:
        for (String category : typesByCategory.keySet()) {
            // add the category to the category filter
            categoryFilter.addItem(category);
        }
        // return the new category filter
        return categoryFilter;
    }

    // a private helper method to create a type filter based on the selected category using the typesByCategory map
    private JComboBox<String> createTypeFilter(String category) {
        // create a new combo box for the type filter
        JComboBox<String> typeFilter = new JComboBox<>();
        // add an "All" option to the type filter as the first item
        typeFilter.addItem("All");
        // check if the selected category is "All"
        if (!"All".equals(category)) {
            // if not, then get the types for the selected category using the typesByCategory map and store them in a list
            List<String> typesForCategory = typesByCategory.get(category);
            // check if the list is not empty (e.g. there are types for the selected category)
            if (typesForCategory != null) {
                // if so, iterate over the types and for each:
                for (String type : typesForCategory) {
                    // add the type to the type filter
                    typeFilter.addItem(type);
                }
            }
        }
        // return the new type filter
        return typeFilter;
    }

    // a private helper method to update the type filter based on the selected category
    private void updateTypeFilter(String selectedCategory) {
        typeFilter.removeAllItems();  // first clear existing types
        typeFilter.addItem("All");   // add the "All" option
        // check if the selected category is "All"
        if (!"All".equals(selectedCategory)) {
            // when it is not "All", get the types for the selected category using the typesByCategory map and store it
            List<String> types = typesByCategory.get(selectedCategory);
            // iterate over the types and for each:
            for (String type : types) {
                // add the type to the type filter
                typeFilter.addItem(type);
            }
        }
    }

    // a private helper method to get the details for computer at the given row
    private Computer getComputerAtRow(int row) {
        String uniqueId = (String) table.getValueAt(row, 2); // column 2 is the unique ID
        return inventory.searchByUniqueId(uniqueId);
    }

    // a private helper method to handle a double click event
    private void handleDoubleClick(JTable table) {
        int selectedRow = table.getSelectedRow(); // get the selected row
        // check if the selected row is a valid index (e.g. within the bounds of the table)
        if (selectedRow >= 0 && selectedRow < table.getRowCount()) {
            Computer selectedComputer = getComputerAtRow(selectedRow); // get the computer object at the selected row
            populateDetailsTable(detailsTableModel, selectedComputer); // populate the details table with the selected computer
            tabbedPane.setSelectedIndex(1); // switch to the details panel
            isEditable = false; // to make cells non-editable
            isAdding = false; // to make the 'Item ID' row non-editable
            detailsTableModel.fireTableStructureChanged(); // notify the details table that the structure has changed
        }
    }

    // a private helper method to create a new panel with buttons for adding, updating, deleting, and clearing items
    private JPanel createButtonPanel(DefaultTableModel detailsTableModel, JTable detailsTable) {
        // create a new panel to hold all the action buttons
        JPanel buttonPanel = new JPanel();

        // Button 1: create a new button for adding a new computer
        addButton = new JButton("Add");
        addButton.setEnabled(false); // disable the button by default
        // add an action listener to the 'Add' button to handle when the button is clicked
        addButton.addActionListener(e -> {
            addNewItem(); // call a method to create a new dialog for adding a new item
            // isEditable = true; // to make cells in the second column editable
            populateBrowseTable(); // repopulate the browse table with the updated inventory
            categoryFilter.setSelectedItem("All"); // reset the category filter
            updateTypeFilter("All"); // update the type filter after resetting the category filter
            browseTableModel.fireTableDataChanged(); // notify the JTable that the data has changed
            detailsTableModel.setRowCount(0); // clear the table in the details panel
            detailsTableModel.fireTableDataChanged(); // notify the JTable that the structure has changed
        });

        // Button 2: create a new button for updating the selected computer
        updateButton = new JButton("Update");
        updateButton.setEnabled(false); // disable the button by default
        // add an action listener to the 'Update' button to handle when the button is clicked
        updateButton.addActionListener(e -> {
            String uniqueId = (String) detailsTableModel.getValueAt(0, 1); // get the unique ID from the details table
            // check if the unique ID is not null or empty (e.g. an existing computer is selected)
            if (uniqueId != null && !uniqueId.isEmpty()) {
                Computer computerToUpdate = inventory.searchByUniqueId(uniqueId); // get the computer object to update
                // check if the computer object is not null (e.g. the computer exists in the inventory)
                if (computerToUpdate != null) {
                    updateExistingItem(computerToUpdate, uniqueId); // update the existing item in the inventory
                    detailsTableModel.setRowCount(0); // clear the table in the details panel
                    detailsTableModel.fireTableDataChanged(); // notify the JTable that the data has changed
                    populateBrowseTable(); // repopulate the browse table with the updated inventory
                    browseTableModel.fireTableDataChanged(); // notify the JTable that the data has changed
                } else {
                    // if the computer object is null, display an error message
                    JOptionPane.showMessageDialog(frame, "Computer with ID " + uniqueId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // if the unique ID is null or empty, display an error message
                JOptionPane.showMessageDialog(frame, "Please select a computer first.", "No Computer Selected", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Button 3: create a new button for deleting the selected computer
        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false); // disable the button by default
        // add an action listener to the 'Delete' button to handle when the button is clicked
        deleteButton.addActionListener(e -> {
            String uniqueId = (String) detailsTableModel.getValueAt(0, 1); // get the unique ID from the details table
            inventory.deleteComputer(uniqueId); // delete the computer from the inventory
            // display a confirmation message to the user
            JOptionPane.showMessageDialog(frame, "Computer with ID " + uniqueId + " has been successfully deleted.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            populateBrowseTable(); // repopulate the browse table with the updated inventory
            categoryFilter.setSelectedItem("All"); // reset the category filter
            updateTypeFilter("All"); // update the type filter after resetting the category filter
            browseTableModel.fireTableDataChanged(); // notify the JTable that the data has changed
            detailsTableModel.setRowCount(0); // clear the table in the details panel
            detailsTableModel.fireTableDataChanged(); // notify the JTable that the structure has changed
        });

        // Button 4: create a new button for clearing the details table
        clearButton = new JButton("Clear");
        clearButton.setEnabled(true); // enable the button by default (e.g. available for all users)
        // add an action listener to the 'Clear' button to handle when the button is clicked
        clearButton.addActionListener(e -> {
            // iterate over the rows in the details table and for each:
            for (int i = 0; i < detailsTableModel.getRowCount(); i++) {
                // set the value of the second column to an empty string
                detailsTableModel.setValueAt("", i, 1);
            }
            isEditable = false; // make cells non-editable
            detailsTableModel.fireTableStructureChanged(); // notify the details table that the structure has changed
        });

        // add the buttons to the button panel
        buttonPanel.add(addButton); // add the 'Add' button
        buttonPanel.add(updateButton); // add the 'Update' button
        buttonPanel.add(deleteButton); // add the 'Delete' button
        buttonPanel.add(clearButton); // add the 'Clear' button
        // return the new button panel
        return buttonPanel;
    }

    // a public method to add a new item to the inventory
    public void addNewItem() {
        // create a new JDialog for adding a new item
        JDialog addDialog = new JDialog(frame, "Add New Computer", true);
        isEditable = true; // to make cells in the second column editable
        isAdding = true; // to make the 'Item ID' row editable
        // create the model for the table
        DefaultTableModel newDetailsTableModel = createDetailsTableModel();
        // set the layout manager of the content pane to GridBagLayout
        addDialog.getContentPane().setLayout(new GridBagLayout());
        // create a new object for the grid bag constraints and configure it
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // fill both horizontally and vertically
        gbc.weightx = 1.0; // allow components to expand horizontally
        gbc.weighty = 1.0; // allow components to expand vertically

        // create a new table with the new table model
        JTable newItemTable = new JTable(newDetailsTableModel) {
            // create a local categoryComboBox to store the selected category
            JComboBox<String> categoryComboBox = createCategoryFilter();
            @Override // an override method to edit cells in the table
            public TableCellEditor getCellEditor(int row, int column) {
                // get the model column index and store it (e.g. for the category and type rows)
                int modelColumn = convertColumnIndexToModel(column);
                // if the model column is 1 and the row is 1 then this is the category row
                if (modelColumn == 1 && row == 1) {
                    // set the initial selected item to "All"
                    categoryComboBox.setSelectedItem("All");
                    // add an item listener to the categoryComboBox to handle when an item is selected
                    categoryComboBox.addItemListener(e -> {
                        // when an item is selected
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            String selectedCategory = (String) categoryComboBox.getSelectedItem(); // get the selected category and store it
                            updateTypeFilter(selectedCategory); // update the class type filter based on the selected category
                        }
                    });
                    // return a new cell editor with the categoryComboBox
                    return new DefaultCellEditor(categoryComboBox);
                // if the model column is 1 and the row is 2 then this is the type row
                } else if (modelColumn == 1 && row == 2) {
                    // create a local type filter based on the selected category
                    JComboBox<String> localTypeFilter = createTypeFilter((String) categoryComboBox.getSelectedItem()); // local variable to not affect the global typeFilter
                    // return a new cell editor with the local type filter
                    return new DefaultCellEditor(localTypeFilter);
                } else {
                    // otherwise, return the default cell editor
                    return super.getCellEditor(row, column);
                }
            }
        };

        // add the table to the dialog using GridBagConstraints
        newItemTable.setRowHeight(30);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // make the table span both columns
        gbc.insets = new Insets(10, 10, 10, 10); // add some padding around the table
        addDialog.getContentPane().add(new JScrollPane(newItemTable), gbc); // add the table to the dialog
        JButton saveButton = new JButton("Save"); // create a Save button to save the new item
        // add an action listener to the 'Save' button
        saveButton.addActionListener(event -> {
            // check if all fields are filled, if not show an error message
            for (int i = 0; i < newDetailsTableModel.getRowCount(); i++) {
                if (newDetailsTableModel.getValueAt(i, 1) == null || newDetailsTableModel.getValueAt(i, 1).toString().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            String uniqueId = (String) newDetailsTableModel.getValueAt(0, 1); // get the unique ID from the table
            // check if the Unique ID already exists in the inventory, if so show an error message
            if (inventory.searchByUniqueId(uniqueId) != null) {
                JOptionPane.showMessageDialog(null, "A computer with this Unique ID already exists.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // call a helper method to validate the new item
            if (validate(newDetailsTableModel)) {
                Computer newComputer = getComputerFromTable(newDetailsTableModel); // create a new computer object from the table
                inventory.addComputer(newComputer); // add the new computer to the inventory
                JOptionPane.showMessageDialog(frame, "New computer added to the inventory.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                addDialog.dispose(); // close the dialog
            } else {
                // If validation fails, show an error message
                JOptionPane.showMessageDialog(frame, "There were issues with the computer data. Please correct them and try again.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // add the 'Save' button to the dialog using the GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // make the button span both columns
        gbc.anchor = GridBagConstraints.CENTER; // center the button
        addDialog.getContentPane().add(saveButton, gbc); // add the button to the dialog
        addDialog.pack(); // size the dialog to fit its content
        addDialog.setVisible(true); // show the dialog
    }

    // a public method to update an existing item in the inventory
    public void updateExistingItem(Computer computerToUpdate, String uniqueId) {
        isEditable = true; // to make cells in the second column editable
        isAdding = false; // to keep the 'Item ID' row of an existing item non-editable
        // create a new JDialog for the update
        JDialog updateDialog = new JDialog(frame, "Update Computer", true);
        updateDialog.getContentPane().setLayout(new GridBagLayout()); // set the layout manager of the content pane to GridBagLayout
        // create a new object for the grid bag constraints (e.g. to set the position and size of the components) and configure it
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        DefaultTableModel updateTableModel = createDetailsTableModel(); // create a new table model for the update dialog
        populateDetailsTable(updateTableModel, computerToUpdate); // create a new table with the update table model
        JTable updateItemTable = new JTable(updateTableModel) { // create a new table with the update table model and custom cell editor
            JComboBox<String> categoryComboBox = createCategoryFilter(); // create a local categoryComboBox to store the selected category
            @Override
            // an override method to edit cells in the table
            public TableCellEditor getCellEditor(int row, int column) {
                int modelColumn = convertColumnIndexToModel(column); // to store the model column index
                // if the model column is 1 and the row is 1 then this is the category row
                if (modelColumn == 1 && row == 1) { // Category row
                    categoryComboBox.setSelectedItem(updateTableModel.getValueAt(1,1)); // get the selected category
                    // add an item listener to the categoryComboBox to handle when an item is selected
                    categoryComboBox.addItemListener(e -> {
                        // when an item is selected
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            String selectedCategory = (String) e.getItem(); // get the selected category and store it
                            updateTypeFilter(selectedCategory); // update the class type filter based on the selected category
                        }
                    });
                    return new DefaultCellEditor(categoryComboBox); // return a new cell editor with the categoryComboBox
                // if the model column is 1 and the row is 2 then this is the type row
                } else if (modelColumn == 1 && row == 2) {
                    // create a local type filter based on the selected category (local to not conflict with the global typeFilter)
                    JComboBox<String> localTypeFilter = createTypeFilter((String) categoryComboBox.getSelectedItem());
                    return new DefaultCellEditor(localTypeFilter); // return a new cell editor with the local type filter
                } else { // if the model column is not 1 or the row is not 1 or 2 then return the default cell editor
                    return super.getCellEditor(row, column);
                }
            }
        };
        // add the new table to the dialog using GridBagConstraints
        updateItemTable.setRowHeight(30); // increase row height for better readability
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        updateDialog.getContentPane().add(new JScrollPane(updateItemTable), gbc); // add the table to the dialog
        JButton saveButton = new JButton("Save"); // create a Save button to save the updated item
        // add an action listener to the 'Save' button to handle when the button is clicked
        saveButton.addActionListener(event -> {
            // first check if all fields are filled, if not display an error message
            for (int i = 0; i < updateTableModel.getRowCount(); i++) {
                if (updateTableModel.getValueAt(i, 1) == null || updateTableModel.getValueAt(i, 1).toString().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            // then check if the input is valid using the validate method before updating the computer
            if (validate(updateTableModel)) {
                Computer updatedComputer = getComputerFromTable(updateTableModel); // create a new computer object with the updated data
                inventory.updateComputer(uniqueId, updatedComputer); // update the computer in the inventory
                populateDetailsTable(updateTableModel, updatedComputer); // repopulate the details table with the updated computer
                detailsTableModel.fireTableDataChanged(); // notify the JTable that the data has changed
                // display a confirmation message to the user
                JOptionPane.showMessageDialog(frame, "Computer updated in the inventory.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                updateDialog.dispose(); // close the dialog
            } else {
                // If validation fails, show an error message
                JOptionPane.showMessageDialog(frame, "There were issues with the computer data. Please correct them and try again.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // add the 'Save' button to the dialog with the GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // make the button span both columns
        gbc.anchor = GridBagConstraints.CENTER; // center the button
        updateDialog.getContentPane().add(saveButton, gbc); // add the button to the dialog
        updateDialog.pack(); // size the dialog to fit its content
        updateDialog.setVisible(true); // show the dialog
    }

    // a private helper method to get the computer object from a table model
    private Computer getComputerFromTable(DefaultTableModel detailsTableModel) {
        String category = (String) detailsTableModel.getValueAt(1, 1); // get the category from the table model
        String type = (String) detailsTableModel.getValueAt(2, 1); // get the type from the table model
        String uniqueId = (String) detailsTableModel.getValueAt(0, 1); // get the unique ID from the table model
        String brand = (String) detailsTableModel.getValueAt(3, 1); // get the brand from the table model
        String cpuFamily = (String) detailsTableModel.getValueAt(4, 1); // get the CPU family from the table model
        // use typecasting to convert the memory size, SSD capacity, screen size, and price to the data types expected by the Computer constructor
        int memorySizeGB = Integer.parseInt(detailsTableModel.getValueAt(5, 1).toString()); // get the memory size from the table model
        int ssdCapacityGB = Integer.parseInt(detailsTableModel.getValueAt(6, 1).toString()); // get the SSD capacity from the table model
        double screenSize = Double.parseDouble(detailsTableModel.getValueAt(7, 1).toString()); // get the screen size from the table model
        double price = Double.parseDouble(detailsTableModel.getValueAt(8, 1).toString()); // get the price from the table model
        // return the new computer object
        return new Computer(category, type, uniqueId, brand, cpuFamily, memorySizeGB, ssdCapacityGB, screenSize, price);
    }

    // a private helper method to populate the details table with the details of the selected computer
    private void populateDetailsTable(DefaultTableModel tableModel, Computer computer) {
        // check if the computer object is not null
        if (computer != null) {
            // clear the table before populating it
            tableModel.setRowCount(0);
            // then add rows with attribute-value pairs for the computer object
            tableModel.addRow(new Object[]{"Item ID", computer.getUniqueId()});
            tableModel.addRow(new Object[]{"Category", computer.getCategory()});
            tableModel.addRow(new Object[]{"Type", computer.getType()});
            tableModel.addRow(new Object[]{"Brand", computer.getBrand()});
            tableModel.addRow(new Object[]{"CPU Family", computer.getCpuFamily()});
            tableModel.addRow(new Object[]{"Memory Size (GB)", computer.getMemorySizeGB()});
            tableModel.addRow(new Object[]{"SSD Capacity (GB)", computer.getSsdCapacityGB()});
            tableModel.addRow(new Object[]{"Screen Size", computer.getScreenSize()});
            tableModel.addRow(new Object[]{"Price", computer.getPrice()});
        } else {
            // if the computer object is null, clear the table
            tableModel.setRowCount(0);
        }
    }

    // a private helper method to validate the input in the details table
    private boolean validate(DefaultTableModel  tableModel) {
        String category = (String) tableModel.getValueAt(1, 1); // get the category from the table model
        String type = (String) tableModel.getValueAt(2, 1); // get the type from the table model
        // check if the Category is "All", and display an error message if it is
        if ("All".equals(category)) {
            JOptionPane.showMessageDialog(null, "Category cannot be 'All'", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // check if the Type is "All", and display an error message if it is
        if ("All".equals(type)) {
            JOptionPane.showMessageDialog(null, "Type cannot be 'All'", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // a try-catch block to handle NumberFormatExceptions
        try {
            // check if the memory size input is a valid numeric value if not display an error message
            int memorySize = Integer.parseInt(tableModel.getValueAt(5, 1).toString()); // convert to string and then to int for validation
            if (memorySize <= 0) {
                throw new NumberFormatException("Memory size must be a positive whole number.");
            }
            // check if the SSD capacity input is a valid numeric value if not display an error message
            int ssdCapacity = Integer.parseInt(tableModel.getValueAt(6, 1).toString());
            if (ssdCapacity <= 0) {
                throw new NumberFormatException("SSD capacity must be a positive whole number.");
            }
            // check if the screen size input is a valid numeric value if not display an error message
            double screenSize = Double.parseDouble(tableModel.getValueAt(7, 1).toString());
            if (screenSize <= 0) {
                throw new NumberFormatException("Screen size must be a positive number.");
            }
            // check if the price input is a valid numeric value if not display an error message
            double price = Double.parseDouble(tableModel.getValueAt(8, 1).toString());
            if (price <= 0) {
                throw new NumberFormatException("Price must be a positive number.");
            }
        // catch the NumberFormatException and display an error message accordingly
        } catch (NumberFormatException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Memory size")) {
                errorMessage = "Invalid memory size. It must be a positive whole number.";
            } else if (errorMessage.contains("SSD capacity")) {
                errorMessage = "Invalid SSD capacity. It must be a positive whole number.";
            } else if (errorMessage.contains("Screen size")) {
                errorMessage = "Invalid screen size. It must be a positive number.";
            } else if (errorMessage.contains("Price")) {
                errorMessage = "Invalid price. It must be a positive number.";
            } else {
                errorMessage = "Invalid input for memory size, SSD capacity, screen size, or price. Input must be numeric.";
            }
            JOptionPane.showMessageDialog(null, errorMessage, "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true; // All validations passed
    }

}


