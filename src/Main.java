import model.Expense;
import service.BudgetManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Budget Tracker CLI - Main Entry Point
 *
 * A command-line budget tracking application demonstrating
 * Core Java proficiency and OOP principles
 *
 * @author Konrad Wojdyna
 * @version 0.2.0
 */

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static BudgetManager manager = new BudgetManager();

    public static void main(String[] args){

        System.out.println("Java Budget Tracker CLI Interactive");

        runMenu();

        scanner.close();
        System.out.println("\nThank you for using Budget Tracker !");
    }

    /**
     * Runs the main interactive menu loop.
     */
    private static void runMenu(){
       boolean isRunning = true;


       while (isRunning){
           displayMenu();

           int choice = scanner.nextInt();
           scanner.nextLine();

           System.out.println();

           switch (choice){
               case 1:
                   addExpense();
                   break;

               case 2:
                   manager.displayAllExpenses();
                   break;

               case 3:
                   showStatistics();
                   break;

               case 4:
                   findByCategory();
                   break;

               case 5:
                   removeExpense();
                   break;

               case 6:
                   findMostAndCheapest();
                   break;

               case 7:
                   System.out.println("Exiting... Goodbye!");
                   isRunning = false;
                   break;
               default:
                   System.out.println("Invalid choice! Please enter 1-7.");
                   break;
           }

           System.out.println();
       }
    }

    private static void displayMenu(){
        System.out.println("MAIN MENU");
        System.out.println("Wybierz co chcesz zrobić: (1-7)");
        System.out.println("1. 📝 Add Expense");
        System.out.println("2. 📋 Display All Expenses");
        System.out.println("3. 📊 Show Statistics");
        System.out.println("4. 🔍 Find by Category");
        System.out.println("5. 🗑️  Remove Expense");
        System.out.println("6. 💰 Show Most/Least Expensive");
        System.out.println("7. 🚪 Exit");
    }

    private static void addExpense(){
        System.out.println("=== Add New Expense ===");

        //Name
        System.out.print("Enter expense name: ");
        String name = scanner.nextLine();

        //Amount
        System.out.println("Enter amount (PLN): ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        //Category
        System.out.println("Enter category (Food/Transport/Entertainment/etc.): ");
        String category = scanner.nextLine();

        //Date
        System.out.println("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        //Description (optional)
        System.out.println("Enter description (optional, press Enter to skip): ");
        String description = scanner.nextLine();

        try{
            Expense expense = new Expense(name, amount, category, date, description);
            manager.addExpense(expense);
        }catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Displays comprehensive statistics.
     */
    private static void showStatistics(){
        System.out.println("=== Budget Statistics ===");

        int count = manager.getExpenseCount();
        double total = manager.calculateTotal();

        System.out.println("Total expenses: " + count);
        System.out.println("Total amount: " + String.format("%.2f PLN", total));

        if(count > 0){
            double average = total / count;
            System.out.println("Average expense: " + String.format("%.2f PLN", average));
        }
    }

    /**
     * Finds and displays expenses by category.
     */
    private static void findByCategory(){
        System.out.println("Enter category to search: ");
        String category = scanner.nextLine();

        try{
             ArrayList<Expense> found = manager.findByCategory(category);

             if(found.isEmpty()){
                 System.out.println("No expenses found in category: " + category);
             }else {
                 System.out.println("\n=== Expenses in " + category + " ===");
                 for(int i=0; i<found.size(); i++){
                     System.out.println((i + 1) + ". ");
                     found.get(i).displayInfo();
                 }

                 double total = manager.getTotalByCategory(category);
                 System.out.println("\nCategory total: " + String.format("%.2f PLN", total));
             }
        }catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Removes an expenses by index.
     */
    private static void removeExpense(){
        if(manager.getExpenseCount() == 0){
            System.out.println("No expenses to remove");
            return;
        }

        //Show all expenses first
        manager.displayAllExpenses();

        System.out.println("\nEnter expense number to remove (1-" + manager.getExpenseCount() + "): ");
        int number = scanner.nextInt();
        scanner.nextLine();

        try{
            manager.removeExpense(number - 1);
        }catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Display the most and least expensive expenses.
     */
    private static void findMostAndCheapest(){
        Expense mostExpensive = manager.findMostExpensive();
        Expense cheapest = manager.findCheapest();

        if(mostExpensive != null){
            System.out.println("Most expensive:");
            System.out.print(" ");
            mostExpensive.displayInfo();
        }

        System.out.println();

        if(cheapest != null){
            System.out.println("Cheapest:");
            System.out.print(" ");
            cheapest.displayInfo();
        }
    }


}


