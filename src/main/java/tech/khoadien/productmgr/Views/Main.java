package tech.khoadien.productmgr.Views;

/**
 * Main class with application's entry point and menus
 * @author Khoa Dien
 * @version 1.0
 */
public class Main {

    private static final String NEW_LINE = System.getProperty("line.separator");

    public static void main(String[] args) {
        System.out.println();
        System.out.println("=================================");
        System.out.println("== PRODUCT MANAGER APPLICATION ==");
        System.out.println("=================================");

        displayMainMenu();

        while (true) {
            int selection = ConsoleInput.getInt("Enter your selection: ");

            if (selection == 1) {
                manageCategories();
            } else if (selection == 2) {
                manageProducts();
            } else if (selection == 0) {
                break;
            } else {
                System.out.println("Invalid selection");
            }
        }

        System.out.println(NEW_LINE + "Goodbye!");
    }

    private static void displayMainMenu() {
        System.out.println();
        System.out.println("         -------------");
        System.out.println("         | MAIN MENU |");
        System.out.println("         -------------");
        System.out.println();
        System.out.println("Select one of the options below:");
        System.out.println("--------------------------------");
        System.out.println("1 - Manage Categories");
        System.out.println("2 - Manage Products");
        System.out.println("0 - Exit");
        System.out.println();
    }

    private static void displayCategoryMenu() {
        System.out.println("Select one of the options below:");
        System.out.println("--------------------------------");
        System.out.println("1 - List categories");
        System.out.println("2 - Add a category");
        System.out.println("3 - Update a category");
        System.out.println("4 - Delete a category");
        System.out.println("0 - Back to main menu");
        System.out.println();
    }

    private static void displayProductMenu() {
        System.out.println("Select one of the options below:");
        System.out.println("--------------------------------");
        System.out.println("1 - List products");
        System.out.println("2 - Add a product");
        System.out.println("3 - Update a product");
        System.out.println("4 - Delete a product");
        System.out.println("0 - Back to main menu");
        System.out.println();
    }

    private static void manageCategories() {
        System.out.println();
        System.out.println("        --------------");
        System.out.println("        | Categories |");
        System.out.println("        --------------");
        System.out.println();

        displayCategoryMenu();

        int selection = -1;

        while (selection != 0) {
            selection = ConsoleInput.getInt("Enter your selection: ");

            switch (selection) {
                case 1:
                    ManageCategories.display();
                    displayCategoryMenu();
                    break;
                case 2:
                    ManageCategories.add();
                    displayCategoryMenu();
                    break;
                case 3:
                    ManageCategories.update();
                    displayCategoryMenu();
                    break;
                case 4:
                    ManageCategories.delete();
                    displayCategoryMenu();
                    break;
                case 0:
                    displayMainMenu();
                    break;
                default:
                    System.out.println("Invalid selection");
            }
        }
    }

    private static void manageProducts() {
        System.out.println();
        System.out.println("         ------------");
        System.out.println("         | Products |");
        System.out.println("         ------------");
        System.out.println();

        displayProductMenu();

        int selection = -1;

        while (selection != 0) {
            selection = ConsoleInput.getInt("Enter your selection: ");

            switch (selection) {
                case 1:
                    ManageProducts.display();
                    displayProductMenu();
                    break;
                case 2:
                    ManageProducts.add();
                    displayProductMenu();
                    break;
                case 3:
                    ManageProducts.update();
                    displayProductMenu();
                    break;
                case 4:
                    ManageProducts.delete();
                    displayProductMenu();
                    break;
                case 0:
                    displayMainMenu();
                    break;
                default:
                    System.out.println("Invalid selection");
            }
        }
    }
}
