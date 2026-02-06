package com.ayduh.warehouse.controller;

import com.ayduh.warehouse.entity.Items;
import com.ayduh.warehouse.repository.InMemoryItemsRepository;
import com.ayduh.warehouse.repository.ItemsRepository;
import com.ayduh.warehouse.service.AuthService;
import com.ayduh.warehouse.service.InventoryService;

import java.util.List;
import java.util.Scanner;

public class ConsoleController {

    private final InventoryService inventoryService;
    private final AuthService authService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleController() {
        ItemsRepository repo = new InMemoryItemsRepository();
        this.authService = new AuthService();
        this.inventoryService = new InventoryService(repo, authService);
    }

    public void start() {
        while (true) {
            printMenu();
            int choice = readInt("");

            if (choice == 0) {
                System.out.println("Bye!");
                break;
            }

            try {
                switch (choice) {
                    case 1 -> listItems();
                    case 2 -> receiveItem();
                    case 3 -> issueItem();
                    case 4 -> showOneItem();
                    case 5 -> lowStock();
                    case 6 -> login();
                    case 7 -> logout();
                    default -> System.out.println("Unknown option. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            } catch (IllegalStateException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== WAREHOUSE MENU ===");
        if (authService.isLoggedIn()) {
            System.out.println("User: " + authService.getCurrentUser().getUsername()
                    + " | role=" + authService.getCurrentUser().getRole());
        } else {
            System.out.println("User: NOT LOGGED IN");
        }

        System.out.println("1) List items");
        System.out.println("2) Receive item (+qty)  [MANAGER/ADMIN]");
        System.out.println("3) Issue item (-qty)    [MANAGER/ADMIN]");
        System.out.println("4) Show item by id");
        System.out.println("5) List low stock items");
        System.out.println("6) Login");
        System.out.println("7) Logout");
        System.out.println("0) Exit");
        System.out.print("Choose: ");
    }

    private void listItems() {
        List<Items> items = inventoryService.getAllItems();
        System.out.println("\n--- ITEMS ---");
        for (Items item : items) {
            System.out.println(item.getId() + ") " + item.getName()
                    + " | qty=" + item.getQuantity()
                    + " | min=" + item.getMin_quantity()
                    + " | category=" + item.getCategory());
        }
    }

    private void lowStock() {
        List<Items> items = inventoryService.getLowStockItems();
        System.out.println("\n--- LOW STOCK ---");
        if (items.isEmpty()) {
            System.out.println("All good. No low stock items.");
            return;
        }
        for (Items item : items) {
            System.out.println(item.getId() + ") " + item.getName()
                    + " | qty=" + item.getQuantity()
                    + " | min=" + item.getMin_quantity());
        }
    }

    private void receiveItem() {
        int id = readInt("Enter item id: ");
        int amount = readInt("Enter amount to receive: ");
        System.out.println(inventoryService.receiveItem(id, amount));
    }

    private void issueItem() {
        int id = readInt("Enter item id: ");
        int amount = readInt("Enter amount to issue: ");
        System.out.println(inventoryService.issueItem(id, amount));
    }

    private void showOneItem() {
        int id = readInt("Enter item id: ");
        Items item = inventoryService.getItemById(id);
        System.out.println("Item: " + item.getId() + " | " + item.getName()
                + " | qty=" + item.getQuantity()
                + " | min=" + item.getMin_quantity()
                + " | category=" + item.getCategory());
    }

    private void login() {
        String username = readString("Username: ");
        String password = readString("Password: ");
        authService.login(username, password);
        System.out.println("OK. Logged in.");
    }

    private void logout() {
        authService.logout();
        System.out.println("Logged out.");
    }

    private int readInt(String prompt) {
        while (true) {
            if (!prompt.isEmpty()) System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Must not be empty.");
        }
    }
}


