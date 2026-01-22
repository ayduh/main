package com.ayduh.warehouse.controller;

import com.ayduh.warehouse.entity.Items;
import com.ayduh.warehouse.repository.InMemoryItemsRepository;
import com.ayduh.warehouse.repository.ItemsRepository;
import com.ayduh.warehouse.repository.PostgresItemsRepository;
import com.ayduh.warehouse.service.InventoryService;

import java.util.List;
import java.util.Scanner;

public class ConsoleController {

    private final InventoryService inventoryService;
    private final Scanner scanner;

    public ConsoleController() {
        ItemsRepository repo = new PostgresItemsRepository();
        this.inventoryService = new InventoryService(repo);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            if ("0".equals(choice)) {
                System.out.println("Bye!");
                break;
            }

            try {
                if ("1".equals(choice)) {
                    listItems();
                } else if ("2".equals(choice)) {
                    receiveItem();
                } else if ("3".equals(choice)) {
                    issueItem();
                } else if ("4".equals(choice)) {
                    showOneItem();
                } else {
                    System.out.println("Unknown option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
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
