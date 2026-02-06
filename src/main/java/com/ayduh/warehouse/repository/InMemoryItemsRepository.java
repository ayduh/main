package com.ayduh.warehouse.repository;

import com.ayduh.warehouse.entity.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryItemsRepository implements ItemsRepository {

    private final Map<Integer, Items> storage = new HashMap<>();

    public InMemoryItemsRepository() {
        storage.put(1, new Items(1, "Hammer", "Steel hammer", 10, 3, 1, "Tools"));
        storage.put(2, new Items(2, "Screws pack", "100 pcs", 50, 20, 1, "Tools"));
        storage.put(3, new Items(3, "Paper A4", "500 sheets", 5, 10, 2, "Office"));
    }

    @Override
    public Optional<Items> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Items> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void updateQuantity(int id, int newQuantity) {
        Items item = storage.get(id);
        if (item != null) {
            item.setQuantity(newQuantity);
        }
    }
}
