package com.ayduh.warehouse.repository;

import com.ayduh.warehouse.entity.Items;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Optional<Items> findById(int id);
    Optional<Items> findByIdWithCategory(int id);
    List<Items> findAll();
    void updateQuantity(int id, int newQuantity);
}
