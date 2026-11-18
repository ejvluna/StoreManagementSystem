A Java Swing desktop application for managing a PC store’s computer inventory and staff accounts. It supports browsing and filtering products, role-based access, and manager-controlled inventory updates.

## Features

- View desktop PCs, laptops, and tablets
- Filter products by category and type
- Log in as a salesperson or manager
- Add, update, and delete inventory items as a manager
- Load initial inventory data from `computers.txt`
- Search inventory by ID, category, type, or category and type

## What It Is

The application models a store catalog using Java classes for computers, staff, and inventory. `Main` loads the catalog and predefined staff accounts, then starts a Swing interface. Salespeople can browse and search products, while managers receive controls for modifying the inventory.

### Stack

- **Language:** Java
- **Interface:** Java Swing and AWT
- **Data storage:** In-memory collections
- **Initial inventory:** Comma-separated values in `computers.txt`
- **IDE support:** IntelliJ IDEA

## How It Is Organized

```text
src/
  Main.java             Application entry point and inventory importer
  PCStoreGUI.java       Swing login window and main interface
  Computer.java         Base computer model
  DesktopPC.java        Desktop PC specialization
  Laptop.java           Laptop specialization
  Tablet.java           Tablet specialization
  ComputerInventory.java In-memory inventory and search operations
  Staff.java            Base staff model
  StaffRoster.java      Staff storage and login validation
  Salesperson.java      Salesperson role
  Manager.java          Manager role
  PCStoreGUI.form       IntelliJ GUI designer form

computers.txt           Initial inventory records
test.txt                Sample invalid records for parsing tests
```

`Main` creates a `ComputerInventory` and `StaffRoster`, imports `computers.txt`, and launches `PCStoreGUI` on the Swing event-dispatch thread. `PCStoreGUI` validates the selected staff account and enables add, update, and delete operations only for users with the `Manager` role.

## How to Run

### Prerequisites

- A Java Development Kit
- IntelliJ IDEA or another Java IDE, for the simplest setup

### IntelliJ IDEA

1. Clone the repository.
2. Open the repository directory or `PC Store Management System.iml` in IntelliJ IDEA.
3. Ensure `src` is marked as the source folder.
4. Run `src/Main.java`.

### Command Line

From the repository root:

```bash
javac src/*.java
java -cp src Main
```

Run the command from the repository root because `Main` reads the inventory using the relative path `computers.txt`.

## Login Accounts

The application creates these accounts in `Main`:

| Username | Password | Role |
|---|---|---|
| `s1` | `s1` | Salesperson |
| `s2` | `s2` | Salesperson |
| `s3` | `s3` | Salesperson |
| `m1` | `m1` | Manager |
| `m2` | `m2` | Manager |

Manager accounts can add, update, and delete products. Salesperson accounts can browse and filter products.
