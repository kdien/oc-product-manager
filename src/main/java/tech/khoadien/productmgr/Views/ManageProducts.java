package tech.khoadien.productmgr.Views;

import tech.khoadien.productmgr.Controllers.CategoryController;
import tech.khoadien.productmgr.Controllers.ProductController;
import tech.khoadien.productmgr.Models.Category;
import tech.khoadien.productmgr.Models.Product;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ManageProducts {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static ProductController productController = new ProductController();
    private static CategoryController categoryController = new CategoryController();

    public static void display() {
        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Filter products by category? (Y/N): ");
            input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")) {
                break;
            } else {
                System.out.println("Invalid answer");
            }
        }

        int categoryId = -1;

        if (input.equalsIgnoreCase("y")) {
            while (true) {
                categoryId = ConsoleInput.getInt("Enter category ID to filter products displayed (enter 0 to show list of valid categories): ");

                if (isValidCategoryId(categoryId))
                    break;
                else if (categoryId == 0)
                    ManageCategories.display();
                else
                    System.out.println("Invalid category ID");
            }
        }

        List<Product> products;
        if (categoryId == -1)
            products = productController.getList(Optional.empty());
        else
            products = productController.getList(Optional.of(categoryId));

        if (products == null) {
            System.out.println(NEW_LINE + "Error! Unable to get products." + NEW_LINE);
        } else {
            CommandLineTable table = new CommandLineTable();
            table.setHeaders("Product ID", "Code", "Description", "Price", "Category");

            for (Product product : products) {
                table.addRow(Integer.toString(product.getId()), product.getCode(), product.getDescription(), product.getPriceFormatted(), product.getCategoryName());
            }

            System.out.println();
            table.print();
            System.out.println();
        }
    }

    public static void add() {
        System.out.println(NEW_LINE + "Add a new product.");
        System.out.println("At any prompt, enter -1 to cancel this operation." + NEW_LINE);

        String code = getNewCode(Optional.empty());
        if (code.equals("-1")) {
            System.out.println("Operation cancelled." + NEW_LINE);
            return;
        }

        String description = ConsoleInput.getLine("Enter description: ").trim();
        if (description.equals("-1")) {
            System.out.println("Operation cancelled." + NEW_LINE);
            return;
        }

        double price = ConsoleInput.getDouble("Enter price: ");
        if (price == -1) {
            System.out.println("Operation cancelled." + NEW_LINE);
            return;
        }

        int categoryId = getNewCategoryId();
        if (categoryId == -1) {
            System.out.println("Operation cancelled." + NEW_LINE);
            return;
        }

        Product product = new Product();
        product.setCode(code);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategoryId(categoryId);

        if (productController.add(product))
            System.out.println(NEW_LINE + "New product with code '" + code + "' added." + NEW_LINE);
        else
            System.out.println(NEW_LINE + "Error! Unable to add product." + NEW_LINE);
    }

    public static void update() {
        System.out.println(NEW_LINE + "Update a product.");
        System.out.println("At any prompt, enter -1 to cancel this operation." + NEW_LINE);

        int id;
        while (true) {
            id = ConsoleInput.getInt("Enter product ID to update: ");

            if (id == -1) {
                System.out.println("Operation cancelled." + NEW_LINE);
                return;
            }

            if (isValidProductId(id))
                break;
            else
                System.out.println("Invalid product ID");
        }

        Product product = productController.get(id);
        String categoryName = categoryController.get(product.getCategoryId()).getName();

        System.out.println(NEW_LINE + "Updating the following product:");
        CommandLineTable table = new CommandLineTable();
        table.setHeaders("Product ID", "Code", "Description", "Price", "Category");
        table.addRow(Integer.toString(product.getId()), product.getCode(), product.getDescription(), product.getPriceFormatted(), categoryName);
        table.print();
        System.out.println();

        String newCode = getNewCode(Optional.of(product));
        if (newCode.equals("-1")) {
            System.out.println("Operation cancelled." + NEW_LINE);
            return;
        }

        String description = ConsoleInput.getLine("Enter description: ").trim();
        if (description.equals("-1")) {
            System.out.println("Operation cancelled." + NEW_LINE);
            return;
        }

        double price = ConsoleInput.getDouble("Enter price: ");
        if (price == -1) {
            System.out.println("Operation cancelled." + NEW_LINE);
            return;
        }

        int categoryId = getNewCategoryId();
        if (categoryId == -1) {
            System.out.println("Operation cancelled." + NEW_LINE);
            return;
        }

        product.setCode(newCode);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategoryId(categoryId);

        if (productController.update(product)) {
            categoryName = categoryController.get(product.getCategoryId()).getName();
            System.out.println(NEW_LINE + "Product updated as follows:");
            table = new CommandLineTable();
            table.setHeaders("Product ID", "Code", "Description", "Price", "Category");
            table.addRow(Integer.toString(product.getId()), product.getCode(), product.getDescription(), product.getPriceFormatted(), categoryName);
            table.print();
            System.out.println();
        } else {
            System.out.println(NEW_LINE + "Error! Unable to update product." + NEW_LINE);
        }
    }

    public static void delete() {
        int id;
        while (true) {
            id = ConsoleInput.getInt("Enter product ID to delete (enter -1 to cancel this operation): ");

            if (id == -1) {
                System.out.println("Operation cancelled." + NEW_LINE);
                return;
            }

            if (isValidProductId(id))
                break;
            else
                System.out.println("Invalid product ID");
        }

        Product product = productController.get(id);

        if (productController.delete(product))
            System.out.println(NEW_LINE + "Product with code '" + product.getCode() + "' deleted." + NEW_LINE);
        else
            System.out.println(NEW_LINE + "Error! Unable to delete product." + NEW_LINE);
    }

    private static String getNewCode(Optional<Product> product) {
        String newCode;

        while (true) {
            newCode = ConsoleInput.getString("Enter new product code: ").trim();

            if (isUniqueProductCode(newCode))
                break;
            else if (product.isPresent() && product.get().getCode().equalsIgnoreCase(newCode))
                break;
            else
                System.out.println("This product code already exists.");
        }

        return newCode;
    }

    private static int getNewCategoryId() {
        int newCategoryId;

        while (true) {
            newCategoryId = ConsoleInput.getInt("Enter category ID (enter 0 to show list of valid categories): ");

            if (newCategoryId == -1)
                return -1;

            if (isValidCategoryId(newCategoryId))
                break;
            else if (newCategoryId == 0)
                ManageCategories.display();
            else
                System.out.println("Invalid category ID");
        }

        return newCategoryId;
    }

    private static boolean isValidProductId(int id) {
        List<Product> products = productController.getList(Optional.empty());
        for (Product product : products) {
            if (product.getId() == id)
                return true;
        }
        return false;
    }

    private static boolean isUniqueProductCode(String code) {
        List<Product> products = productController.getList(Optional.empty());
        for (Product product : products) {
            if (product.getCode().equalsIgnoreCase(code))
                return false;
        }
        return true;
    }

    private static boolean isValidCategoryId(int id) {
        List<Category> categories = categoryController.getList();
        for (Category category : categories) {
            if (category.getId() == id)
                return true;
        }
        return false;
    }
}
