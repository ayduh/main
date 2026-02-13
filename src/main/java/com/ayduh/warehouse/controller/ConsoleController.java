package com.ayduh.warehouse.controller;

import com.ayduh.warehouse.entity.Items;
import com.ayduh.warehouse.repository.ItemRepository;
import com.ayduh.warehouse.repository.RepositoryFactory;
import com.ayduh.warehouse.service.InventoryService;

import java.util.List;
import java.util.Scanner;

public class ConsoleController {

    private final InventoryService inventoryService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleController() {
        ItemRepository repo = RepositoryFactory.createItemRepository(false);
        this.inventoryService = new InventoryService(repo);
    }

    public void start() {
        while (true) {
            printMenu();
            int choice;
            while (true) {
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Please enter a number.");
                    scanner.nextLine();
                }
            }


            if (choice == 0) {
                System.out.println("Bye!");
                break;
            }

            else {
                try {
                    switch (choice) {
                        case 1 -> listItems();
                        case 2 -> receiveItem();
                        case 3 -> issueItem();
                        case 4 -> showOneItem();
                        default -> System.out.println("Unknown option. Try again.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
                catch (IllegalStateException e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
            }

        }
    }

    private void printMenu() {
        System.out.println("\n=== WAREHOUSE MENU ===");
        System.out.println("1) List items");
        System.out.println("2) Receive item (+qty)");
        System.out.println("3) Issue item (-qty)");
        System.out.println("4) Show item by id");
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

    private void receiveItem() {
        int id = readInt("Enter item id: ");
        int amount = readInt("Enter amount to receive: ");
        String result = inventoryService.receiveItem(id, amount);
        System.out.println(result);
    }

    private void issueItem() {
        int id = readInt("Enter item id: ");
        int amount = readInt("Enter amount to issue: ");
        String result = inventoryService.issueItem(id, amount);
        System.out.println(result);
    }

    private void showOneItem() {
        int id = readInt("Enter item id: ");
        Items item = inventoryService.getItemById(id);
        System.out.println("Item: " + item.getId() + " | " + item.getName()
                + " | qty=" + item.getQuantity()
                + " | min=" + item.getMin_quantity()
                + " | category=" + item.getCategory());
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
