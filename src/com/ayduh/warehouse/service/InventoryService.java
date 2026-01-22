package com.ayduh.warehouse.service;

import com.ayduh.warehouse.entity.Items;
import com.ayduh.warehouse.repository.ItemsRepository;

import java.util.List;

public class InventoryService {

    private final ItemsRepository itemsRepository;

    public InventoryService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public Items getItemById(int id) {
        return itemsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: id=" + id));
    }

    public List<Items> getAllItems() {
        return itemsRepository.findAll();
    }

    // Приход товара: +quantity
    public String receiveItem(int itemId, int amount) {
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

    // Расход товара: -quantity
    public String issueItem(int itemId, int amount) {
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
}
