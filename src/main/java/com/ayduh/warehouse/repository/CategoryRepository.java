package com.ayduh.warehouse.repository;

import com.ayduh.warehouse.db.DatabaseConfig;
import com.ayduh.warehouse.entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepository {
    private static final CategoryRepository INSTANCE = new CategoryRepository();

    private CategoryRepository() {}

    public static CategoryRepository getInstance() {
        return INSTANCE;
    }

    public Optional<Category> findById(int id) {
        String sql = "SELECT id, name FROM categories WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Category(rs.getInt("id"), rs.getString("name")));
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> findAll() {
        String sql = "SELECT id, name FROM categories ORDER BY id";
        List<Category> categories = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }

            return categories;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
