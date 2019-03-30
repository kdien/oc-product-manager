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

public class ProductController {

    private Connection connection;

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
