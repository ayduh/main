package com.ayduh.warehouse.repository;

import com.ayduh.warehouse.db.DatabaseConfig;
import com.ayduh.warehouse.entity.Items;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresItemsRepository implements ItemsRepository {

    // Реализовал метод поиска по id2
    @Override
    public Optional<Items> findById(int id) {
        String sql = "SELECT * FROM items WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Items item = new Items(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getInt("min_quantity"),
                        rs.getString("category")
                );
                return Optional.of(item);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Чтение товаров (всех_
    @Override
    public List<Items> findAll() {
        List<Items> items = new ArrayList<>();
        String sql = "SELECT * FROM items";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                items.add(new Items(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getInt("min_quantity"),
                        rs.getString("category")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return items;
    }
    public void updateQuantity(int id, int newQuantity){

    }
}