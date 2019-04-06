package tech.khoadien.productmgr.Controllers;

import tech.khoadien.productmgr.AppData.DbConnection;
import tech.khoadien.productmgr.Models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a controller that connects to the database and returns requested data on the categories
 * @author Khoa Dien
 * @version 1.0
 */
public class CategoryController {

    private Connection connection;

    /**
     * Get a list of all categories from the database
     * @return a collection of Category objects representing all the categories
     */
    public List<Category> getList() {
        connection = DbConnection.getConnection();

        String sql = "SELECT CategoryID, CategoryName FROM categories ORDER BY CategoryID";

        List<Category> categories = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("CategoryID");
                String name = resultSet.getString("CategoryName");
                Category category = new Category(id, name);
                categories.add(category);
            }

            resultSet.close();
            connection.close();
            return categories;

        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * Get a category from the database
     * @param id the category ID requested
     * @return a Category object representing the category with a matching ID
     */
    public Category get(int id) {
        connection = DbConnection.getConnection();

        String sql = "SELECT CategoryID, CategoryName FROM categories WHERE CategoryID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("CategoryName");
                Category category = new Category(id, name);
                resultSet.close();
                connection.close();
                return category;
            } else {
                resultSet.close();
                connection.close();
                return null;
            }

        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * Add a new category to the database
     * @param category a Category object with properties to be added
     * @return true if successfully added, false if error occurred
     */
    public boolean add(Category category) {
        connection = DbConnection.getConnection();

        String sql = "INSERT INTO categories (CategoryName) VALUES (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * Delete a category from the database
     * @param category a Category object representing the record to be deleted
     * @return true if successfully deleted, false if error occurred
     */
    public boolean delete(Category category) {
        connection = DbConnection.getConnection();

        String sql = "DELETE FROM categories WHERE CategoryID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, category.getId());
            preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * Update/Edit a category in the database
     * @param category a Category object representing the record to be updated
     * @return true if successfully updated, false if error occurred
     */
    public boolean update(Category category) {
        connection = DbConnection.getConnection();

        String sql = "UPDATE categories SET CategoryName = ? WHERE CategoryID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setInt(2, category.getId());
            preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
