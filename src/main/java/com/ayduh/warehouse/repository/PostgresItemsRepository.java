package com.ayduh.warehouse.repository;

import com.ayduh.warehouse.db.DatabaseConfig;
import com.ayduh.warehouse.entity.Items;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PostgresItemsRepository implements ItemsRepository {

    // Реализовал метод поиска по id2
    @Override
    public Optional<Items> findById(int id) {
        String sql = "SELECT i.id, i.name, i.description, i.quantity, i.min_quantity, " +
                "i.category_id, c.name AS category_name " +
                "FROM items i JOIN categories c ON i.category_id = c.id WHERE i.id = ?";
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
                        rs.getInt("category_id"),
                        rs.getString("category_name")
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
        String sql = "SELECT i.id, i.name, i.description, i.quantity, i.min_quantity, " +
                "i.category_id, c.name AS category_name " +
                "FROM items i JOIN categories c ON i.category_id = c.id";

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
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return items;
    }
    @Override
    public void updateQuantity(int id, int newQuantity) {
        String sql = "UPDATE items SET quantity = ? WHERE id = ?";

        try (
                Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
