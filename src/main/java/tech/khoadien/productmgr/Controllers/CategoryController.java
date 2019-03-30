package tech.khoadien.productmgr.Controllers;

import tech.khoadien.productmgr.AppData.DbConnection;
import tech.khoadien.productmgr.Models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryController {

    private Connection connection;

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
