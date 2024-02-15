package com.edu;
//TODO Auto-generated method stub
		import java.sql.Connection;
		import java.sql.Date;
		import java.sql.DriverManager;
		import java.sql.PreparedStatement;
		import java.sql.ResultSet;
		import java.sql.SQLException;
		import java.util.Scanner;

public class MedicalManagement {

			    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/batch28db";
		    private static final String USERNAME = "root";
		    private static final String PASSWORD = "root";

		    public static void main(String[] args) {
		        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
		            System.out.println("Connected to the database.");

		            Scanner scanner = new Scanner(System.in);

		            while (true) {
		                System.out.println("\nMedical Management System");
		                System.out.println("1. Insert New Medicine");
		                System.out.println("2. Update Medicine Price");
		                System.out.println("3. Check Medicine Availability");
		                System.out.println("4. Check Medicine Price");
		                System.out.println("5. Delete Medicine");
		                System.out.println("6.view all medicine ");
		                System.out.println("7. Exit");
		                System.out.print("Enter your choice: ");
		                int choice = scanner.nextInt();

		                switch (choice) {
		                    case 1:
		                        insertNewMedicine(conn, scanner);
		                        break;
		                    case 2:
		                        updateMedicinePrice(conn, scanner);
		                        break;
		                    case 3:
		                        checkMedicineAvailability(conn, scanner);
		                        break;
		                    case 4:
		                        checkMedicinePrice(conn, scanner);
		                        break;
		                    case 5:
		                        deleteMedicine(conn, scanner);
		                        break;
		                    case 6:
		                        viewAllMedicine(conn);
		                        break;
		                    case 7:
		                    	System.out.println("Existing..........");;
		                    	break;
		                    default:
		                        System.out.println("Invalid choice. Please try again.");
		                        
		                        return;
		                }
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    private static void viewAllMedicine(Connection conn) throws SQLException {
				// TODO Auto-generated method stub
			  
		    	
		    	    // SQL query to retrieve medicines
		    	    String sql = "SELECT * FROM medicines";
		    	    PreparedStatement pst = conn.prepareStatement(sql);
		    	    ResultSet rs = pst.executeQuery();

		    	    // Display table header
		    	    System.out.format("%-10s %-30s %-20s %-10s %n",
		    	            "ID", "Name", "Price", "Quantity");
		    	    System.out.println("------------------------------------------------------------");

		    	    // Display medicine information in tabular format
		    	    while (rs.next()) {
		    	        System.out.format("%-10d %-30s $%-9.2f %-10d%n",
		    	                rs.getInt("id"),
		    	                rs.getString("name"),
		    	                rs.getFloat("price"),
		    	                rs.getInt("quantity"));
		    	    }
		    	}

			private static void insertNewMedicine(Connection conn, Scanner scanner) throws SQLException {
		        System.out.print("Enter the medicine name: ");
		        String name = scanner.next();
		        System.out.print("Enter the medicine price: ");
		        double price = scanner.nextDouble();
		        System.out.print("Enter the quantity available: ");
		        int quantity = scanner.nextInt();

		        String sql = "INSERT INTO medicines (name, price, quantity) VALUES (?, ?, ?)";
		        try (PreparedStatement statement = conn.prepareStatement(sql)) {
		            statement.setString(1, name);
		            statement.setDouble(2, price);
		            statement.setInt(3, quantity);
		            int rowsInserted = statement.executeUpdate();
		            if (rowsInserted > 0) {
		                System.out.println("Medicine inserted successfully.");
		            } else {
		                System.out.println("Failed to insert medicine.");
		            }
		        }
		    }

		    private static void updateMedicinePrice(Connection conn, Scanner scanner) throws SQLException {
		        System.out.print("Enter the medicine name: ");
		        String name = scanner.next();
		        System.out.print("Enter the new price: ");
		        double newPrice = scanner.nextDouble();

		        String sql = "UPDATE medicines SET price = ? WHERE name = ?";
		        try (PreparedStatement statement = conn.prepareStatement(sql)) {
		            statement.setDouble(1, newPrice);
		            statement.setString(2, name);
		            int rowsUpdated = statement.executeUpdate();
		            if (rowsUpdated > 0) {
		                System.out.println("Medicine price updated successfully.");
		            } else {
		                System.out.println("Failed to update medicine price. Medicine not found.");
		            }
		        }
		    }

		    private static void checkMedicineAvailability(Connection conn, Scanner scanner) throws SQLException {
		        System.out.print("Enter the medicine name: ");
		        String name = scanner.next();

		        String sql = "SELECT quantity FROM medicines WHERE name = ?";
		        try (PreparedStatement statement = conn.prepareStatement(sql)) {
		            statement.setString(1, name);
		            ResultSet resultSet = statement.executeQuery();
		            if (resultSet.next()) {
		                int quantity = resultSet.getInt("quantity");
		                System.out.println("Available quantity of " + name + ": " + quantity);
		            } else {
		                System.out.println("Medicine not found.");
		            }
		        }
		    }

		    private static void checkMedicinePrice(Connection conn, Scanner scanner) throws SQLException {
		        System.out.print("Enter the medicine name: ");
		        String name = scanner.next();

		        String sql = "SELECT price FROM medicines WHERE name = ?";
		        try (PreparedStatement statement = conn.prepareStatement(sql)) {
		            statement.setString(1, name);
		            ResultSet resultSet = statement.executeQuery();
		            if (resultSet.next()) {
		                double price = resultSet.getDouble("price");
		                System.out.println("Price of " + name + ": " + price);
		            } else {
		                System.out.println("Medicine not found.");
		            }
		        }
		    }

		    private static void deleteMedicine(Connection conn, Scanner scanner) throws SQLException {
		        System.out.print("Enter the medicine name to delete: ");
		        String name = scanner.next();

		        String sql = "DELETE FROM medicines WHERE name = ?";
		        try (PreparedStatement statement = conn.prepareStatement(sql)) {
		            statement.setString(1, name);
		            int rowsDeleted = statement.executeUpdate();
		            if (rowsDeleted > 0) {
		                System.out.println("Medicine deleted successfully.");
		            } else {
		                System.out.println("Failed to delete medicine. Medicine not found.");
		            }
		        }
		    }

	}


