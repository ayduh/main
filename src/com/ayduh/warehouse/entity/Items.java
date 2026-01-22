package com.ayduh.warehouse.entity;

public class Items {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private int min_quantity;
    private String category;

    public Items(){}

    public Items(int id, String name, String description, int quantity,
                int min_quantity, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.min_quantity = min_quantity;
        this.category = category;
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

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}
}
