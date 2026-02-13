package com.ayduh.warehouse.entity;

public class Items {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private int min_quantity;
    private int categoryId;
    private String categoryName;

    public Items(){}

    public Items(int id, String name, String description, int quantity,
                int min_quantity, int categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.min_quantity = min_quantity;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public int getMin_quantity() {return min_quantity;}
    public void setMin_quantity(int min_quantity) {this.min_quantity = min_quantity;}

    public int getCategoryId() {return categoryId;}
    public void setCategoryId(int categoryId) {this.categoryId = categoryId;}

    public String getCategory() {return categoryName;}
    public void setCategory(String categoryName) {this.categoryName = categoryName;}
}
