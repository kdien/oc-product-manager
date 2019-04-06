package tech.khoadien.productmgr.Models;

import java.text.NumberFormat;

/**
 * Represents a product in the database
 * @author Khoa Dien
 * @version 1.0
 */
public class Product {

    private int id;
    private String code;
    private String description;
    private double price;
    private int categoryId;
    private String categoryName;

    public Product() {}

    public Product(int id, String code, String description, double price, int categoryId) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Product(int id, String code, String description, double price, String categoryName) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Get currency-formatted price
     * @return a String representing the product price in currency format
     */
    public String getPriceFormatted() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}