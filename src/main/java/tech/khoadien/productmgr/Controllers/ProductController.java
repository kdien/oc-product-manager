package tech.khoadien.productmgr.Controllers;

import tech.khoadien.productmgr.AppData.DbConnection;
import tech.khoadien.productmgr.Models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a controller that connects to the database and returns requested data on the products
 * @author Khoa Dien
 * @version 1.0
 */
public class ProductController {

    private Connection connection;

    /**
     * Get a list of products from the database, optionally filtered by a category if specified
     * @param categoryId optional ID of a category to filter the products returned
     * @return a collection of Product objects representing the products requested
     */
    public List<Product> getList(Optional<Integer> categoryId) {
        connection = DbConnection.getConnection();

        String sql = "SELECT ProductID, ProductCode, Description, Price, CategoryName " +
            "FROM products P JOIN categories C ON P.CategoryID = C.CategoryID " +
            ((categoryId.isPresent()) ? "WHERE P.CategoryID = ? " : "") +
            "ORDER BY ProductID";

        List<Product> products = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (categoryId.isPresent())
                preparedStatement.setInt(1, categoryId.orElse(0));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ProductID");
                String code = resultSet.getString("ProductCode");
                String description = resultSet.getString("Description");
                double price = resultSet.getDouble("Price");
                String category = resultSet.getString("CategoryName");

                Product product = new Product(id, code, description, price, category);
                products.add(product);
            }

            resultSet.close();
            connection.close();
            return products;

        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * Get a product from the database
     * @param id the product ID requested
     * @return a Product object representing the product with a matching ID
     */
    public Product get(int id) {
        connection = DbConnection.getConnection();

        String sql = "SELECT * FROM products WHERE ProductID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String code = resultSet.getString("ProductCode");
                String description = resultSet.getString("Description");
                double price = resultSet.getDouble("Price");
                int categoryId = resultSet.getInt("CategoryID");

                Product product = new Product(id, code, description, price, categoryId);

                resultSet.close();
                connection.close();
                return product;

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
     * Add a new product to the database
     * @param product a Product object with properties to be added
     * @return true if successfully added, false if error occurred
     */
    public boolean add(Product product) {
        connection = DbConnection.getConnection();

        String sql = "INSERT INTO products (ProductCode, Description, Price, CategoryID) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getCategoryId());
            preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * Delete a product from the database
     * @param product a Product object representing the record to be deleted
     * @return true if successfully deleted, false if error occurred
     */
    public boolean delete(Product product) {
        connection = DbConnection.getConnection();

        String sql = "DELETE FROM products WHERE ProductID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, product.getId());
            preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * Update/Edit a product in the database
     * @param product a Product object representing the record to be updated
     * @return true if successfully updated, false if error occurred
     */
    public boolean update(Product product) {
        connection = DbConnection.getConnection();

        String sql = "UPDATE products SET ProductCode = ?, Description = ?, Price = ?, CategoryID = ? WHERE ProductID = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getCategoryId());
            preparedStatement.setInt(5, product.getId());
            preparedStatement.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
