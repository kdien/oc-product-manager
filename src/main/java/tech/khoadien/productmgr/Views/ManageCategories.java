package tech.khoadien.productmgr.Views;

import tech.khoadien.productmgr.Controllers.CategoryController;
import tech.khoadien.productmgr.Models.Category;

import java.util.List;
import java.util.Optional;

public class ManageCategories {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static CategoryController categoryController = new CategoryController();

    public static void display() {
        System.out.println();

        List<Category> categories = categoryController.getList();

        if (categories == null) {
            System.out.println("Error! Unable to get categories." + NEW_LINE);
        } else {
            CommandLineTable table = new CommandLineTable();
            table.setHeaders("Category ID", "Category Name");

            for (Category category : categories) {
                table.addRow(Integer.toString(category.getId()), category.getName());
            }

            table.print();
            System.out.println();
        }
    }

    public static void add() {
        String name = getNewName(Optional.empty());

        if (name.equals("-1")) {
            System.out.println("Operation cancelled." + NEW_LINE);
            return;
        }

        Category category = new Category();
        category.setName(name);

        if (categoryController.add(category))
            System.out.println(NEW_LINE + "New category '" + name + "' added." + NEW_LINE);
        else
            System.out.println(NEW_LINE + "Error! Unable to add category." + NEW_LINE);
    }

    public static void update() {
        int id;
        while (true) {
            id = Console.getInt("Enter category ID to update (enter -1 to cancel this operation): ");

            if (id == -1) {
                System.out.println("Operation cancelled." + NEW_LINE);
                return;
            }

            if (isValidId(id))
                break;
            else
                System.out.println("Invalid category ID");
        }

        Category category = categoryController.get(id);

        System.out.println(NEW_LINE + "Updating the following category:");
        CommandLineTable table = new CommandLineTable();
        table.setHeaders("Category ID", "Category Name");
        table.addRow(Integer.toString(category.getId()), category.getName());
        table.print();
        System.out.println();

        String newName = getNewName(Optional.of(category));
        if (newName.equals("-1")) {
            System.out.println("Operation cancelled." + NEW_LINE);
            return;
        }

        category.setName(newName);

        if (categoryController.update(category)) {
            System.out.println(NEW_LINE + "Category updated as follows:");
            table = new CommandLineTable();
            table.setHeaders("Category ID", "Category Name");
            table.addRow(Integer.toString(category.getId()), category.getName());
            table.print();
            System.out.println();
        } else {
            System.out.println(NEW_LINE + "Error! Unable to update category." + NEW_LINE);
        }
    }

    public static void delete() {
        int id;
        while (true) {
            id = Console.getInt("Enter category ID to delete (enter -1 to cancel this operation): ");

            if (id == -1) {
                System.out.println("Operation cancelled." + NEW_LINE);
                return;
            }

            if (isValidId(id))
                break;
            else
                System.out.println("Invalid category ID");
        }

        Category category = categoryController.get(id);

        if (categoryController.delete(category))
            System.out.println(NEW_LINE + "Category '" + category.getName() + "' deleted." + NEW_LINE);
        else
            System.out.println(NEW_LINE + "Error! Unable to delete category." + NEW_LINE);
    }

    private static String getNewName(Optional<Category> category) {
        String newName;

        while (true) {
            newName = Console.getLine("Enter new category name (enter -1 to cancel this operation): ").trim();

            if (isUniqueName(newName))
                break;
            else if (category.isPresent() && category.get().getName().equalsIgnoreCase(newName))
                break;
            else
                System.out.println("This category name already exists.");
        }

        return newName;
    }

    private static boolean isValidId(int id) {
        List<Category> categories = categoryController.getList();
        for (Category category : categories) {
            if (category.getId() == id)
                return true;
        }
        return false;
    }

    private static boolean isUniqueName(String name) {
        List<Category> categories = categoryController.getList();
        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase(name))
                return false;
        }
        return true;
    }
}
