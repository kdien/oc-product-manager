package tech.khoadien.productmgr.Models;

/**
 * Represents a category in the database
 * @author Khoa Dien
 * @version 1.0
 */
public class Category {

    private int id;
    private String name;

    public Category() {}

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
