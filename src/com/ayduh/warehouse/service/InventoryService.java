package com.ayduh.warehouse.service;

import com.ayduh.warehouse.entity.Items;
import com.ayduh.warehouse.model.Role;
import com.ayduh.warehouse.model.User;
import com.ayduh.warehouse.repository.ItemsRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryService {

    private final ItemsRepository itemsRepository;
    private final AuthService authService;

    public InventoryService(ItemsRepository itemsRepository, AuthService authService) {
        this.itemsRepository = itemsRepository;
        this.authService = authService;
    }

    public Items getItemById(int id) {
        requireLogin();
        if (id <= 0) {
            throw new IllegalArgumentException("Item id must be > 0");
        }

        return itemsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: id=" + id));
    }

    public List<Items> getAllItems() {
        requireLogin();

        return itemsRepository.findAll().stream()
                .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
                .collect(Collectors.toList());
    }

    public List<Items> getLowStockItems() {
        requireLogin();

        return itemsRepository.findAll().stream()
                .filter(i -> i.getQuantity() < i.getMin_quantity())
                .collect(Collectors.toList());
    }

    public String receiveItem(int itemId, int amount) {
        requireManagerOrAdmin();
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }

        Items item = getItemById(itemId);

        int oldQty = item.getQuantity();
        int newQty = oldQty + amount;

        itemsRepository.updateQuantity(itemId, newQty);

        return "RECEIVED: " + amount + " units. Item=" + item.getName()
                + ", oldQty=" + oldQty + ", newQty=" + newQty;
    }

    public String issueItem(int itemId, int amount) {
        requireManagerOrAdmin();
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }

        Items item = getItemById(itemId);

        int oldQty = item.getQuantity();
        if (oldQty < amount) {
            throw new IllegalStateException("Not enough stock. Available=" + oldQty + ", requested=" + amount);
        }

        int newQty = oldQty - amount;
        itemsRepository.updateQuantity(itemId, newQty);

        String result = "ISSUED: " + amount + " units. Item=" + item.getName()
                + ", oldQty=" + oldQty + ", newQty=" + newQty;

        if (newQty < item.getMin_quantity()) {
            result += " | WARNING: stock below min (" + item.getMin_quantity() + ")";
        }

        return result;
    }

    private void requireLogin() {
        if (!authService.isLoggedIn()) {
            throw new IllegalStateException("Please login first.");
        }
    }

    private void requireManagerOrAdmin() {
        requireLogin();
        Role role = authService.getCurrentUser().getRole();
        if (role == Role.MANAGER || role == Role.ADMIN) return;
        throw new IllegalStateException("Access denied. Only MANAGER or ADMIN.");
    }

}

