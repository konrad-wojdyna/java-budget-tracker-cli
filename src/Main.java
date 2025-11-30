import model.Category;
import model.Expense;
import model.Priority;
import service.BudgetManager;

import java.util.ArrayList;
import java.util.Locale;
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

    private static Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
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
                   findExpensiveItems();
                   break;

               case 8:
                   clearAllExpenses();
                   break;

               case 9:
                   findByPriority();
                   break;

               case 10:
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

    /**
     * Show user menu
     */
    private static void displayMenu(){
        System.out.println("MAIN MENU");

        //Show current budget status
        int count = manager.getExpenseCount();
        double total = manager.calculateTotal();
        System.out.println("📊 Current: " + count + " expenses | " + String.format("%.2f PLN", total));

        System.out.println("Wybierz co chcesz zrobić: (1-10)");
        System.out.println("1. 📝 Add Expense");
        System.out.println("2. 📋 Display All Expenses");
        System.out.println("3. 📊 Show Statistics");
        System.out.println("4. 🔍 Find by Category");
        System.out.println("5. 🗑️  Remove Expense");
        System.out.println("6. 💰 Show Most/Least Expensive");
        System.out.println("7. 📋 Display Expensive items");
        System.out.println("8. ⚠ Clear all expenses");
        System.out.println("9. ⚡ Display Expenses by priority");
        System.out.println("10. 🚪 Exit");

    }

    /**
     * Add new expense
     */
    private static void addExpense(){
        System.out.println("=== Add New Expense ===");

        //Name
        String name;
        while (true){
        System.out.print("Enter expense name: ");
        name = scanner.nextLine();

        if(name.trim().isEmpty()){
            System.out.println("Name cannot be empty! Try again.");
        }else {
            break;
         }
        }

        //Amount
        double amount;
        while (true){
        System.out.println("Enter amount (PLN): ");
        amount = scanner.nextDouble();
        scanner.nextLine();

        if(amount <= 0){
            System.out.println("Amount must be positive! Try again.");
        }else {
            break;
        }
        }


        //Category
        Category category;
        while (true) {
            System.out.println("\n📂 Select category:");
            Category[] categories = Category.values();

            for (int i = 0; i < categories.length; i++) {
                System.out.println(" " + (i + 1) + ". " + categories[i].getLabel());
            }

            System.out.println("Enter choice (1-" + categories.length + "): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= categories.length) {
                category = categories[choice - 1];
                break;
            } else {
                System.out.println("Invalid choice! Try again.");
            }
        }

        //Date
        String date;
        while (true){
        System.out.println("Enter date (YYYY-MM-DD): ");
        date = scanner.nextLine();

        if(date.trim().isEmpty()){
            System.out.println("Date cannot be empty! Try again");
        }else {
            break;
         }
        }

        //Priority - Select from enum
        Priority priority;
        while (true){
            System.out.println("\n Select priority:");
            Priority[] priorities = Priority.values();

            for(int i=0; i<priorities.length; i++){
                System.out.println(" " + (i + 1) + ". " + priorities[i]);
            }

            System.out.println("Enter choice (1-" + priorities.length + "): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice >= 1 && choice <= priorities.length){
                priority = priorities[choice - 1];
                break;
            }else {
                System.out.println("Invalid choice! Try again.");
            }
        }


        //Description (optional)
        System.out.println("Enter description (optional, press Enter to skip): ");
        String description = scanner.nextLine();

        try{
            Expense expense = new Expense(name, amount, category, date, description, priority);
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
        System.out.println("\n📂 Select category to search:");
        Category[] categories = Category.values();

        for(int i=0; i<categories.length; i++){
            System.out.println(" " + (i + 1) + ". " + categories[i].getLabel());
        }

        System.out.println("Enter choice (1-" + categories.length + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice < 1 || choice > categories.length){
            System.out.println("Invalid choice!");
            return;
        }

        Category category = categories[choice - 1];

        try{
             ArrayList<Expense> found = manager.findByCategory(category);

             if(found.isEmpty()){
                 System.out.println("No expenses found in category: " + category.getDisplayName());
             }else {
                 System.out.println("\n=== Expenses in " + category.getDisplayName() + " ===");
                 for(int i=0; i<found.size(); i++){
                     System.out.print((i + 1) + ". ");
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

    /**
     * Display all expensive items above user-specified amount
     */
    private static void findExpensiveItems(){

        double minAmount;

        while (true) {
            System.out.print("Enter minimum amount: ");
            minAmount = scanner.nextDouble();
            scanner.nextLine();

            if (minAmount <= 0) {
                System.out.println("Amount must be positive number greater than 0!");
            } else {
                break;
            }
        }

        try{
        ArrayList<Expense> expenses = manager.findExpensesAbove(minAmount);

        if(expenses.isEmpty()){
            System.out.println("No expenses found above " + String.format("%.2f PLN", minAmount));
            return;
        }

        System.out.println("Expenses above " + String.format("%.2f PLN:", minAmount));
        System.out.println(" ");
        for(Expense expense : expenses){
            expense.displayInfo();
        }
            System.out.println("─────────────────────────────");
            System.out.println("Found: " + expenses.size() + " expenses");

        }catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Clear all expenses - cannot be undone
     */
    private static void clearAllExpenses(){
        if(manager.getExpenseCount() == 0){
            System.out.println("No expenses to clear");
            return;
        }

        String confirm;
        while (true){
          System.out.print("⚠️  Are you sure? This cannot be undone! (yes/no): ");
          confirm = scanner.nextLine();

          if(!confirm.trim().equals("yes") && !confirm.trim().equals("no")){
              System.out.println("Select: yes or no");
          }else {
              break;
          }
        }

        if(confirm.equalsIgnoreCase("yes")){
            manager.clearAllExpenses();
        }else {
            System.out.println("Cancelled.");
        }
    }

    /**
     * Find all expenses by priority level
     */
    private static void findByPriority(){
        System.out.println("Select priority to search:");
        Priority[] priorities = Priority.values();

        for(int i=0; i < priorities.length; i++){
            System.out.println(" " + (i + 1) + ". " + priorities[i]);
        }

        System.out.println("Enter choice (1-" + priorities.length + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice < 1 || choice > priorities.length){
            System.out.println("Invalid choice!");
            return;
        }

        Priority priority = priorities[choice - 1];

        try{
            ArrayList<Expense> found = manager.findByPriority(priority);

            if(found.isEmpty()){
                System.out.println("No expenses with priority: " + priority);
            }else {
                System.out.println("\n=== Expenses with priority: " + priority + " ===");
                for(int i=0; i<found.size(); i++){
                    System.out.print((i + 1) + ". ");
                    found.get(i).displayInfo();
                }
                System.out.println("\nFound: " + found.size() + " expenses");
            }
        }catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }


}


