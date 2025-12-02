import model.*;
import repository.ExpenseRepository;
import repository.InMemoryExpenseRepository;
import repository.MockExpenseRepository;
import service.BudgetManager;

import java.util.*;

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

    private final static Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    // OLD: private static BudgetManager manager = new BudgetManager();

    //NEW: Create repository, then inject into manager
    private  static ExpenseRepository repository = new InMemoryExpenseRepository();
    private  static BudgetManager manager = new BudgetManager(repository);

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
                   manager.displayCategoryStatistics();
                   break;

               case 11:
                   testOverloading();
                   break;

               case 12:
                   manager.addPresetExpenses();
                   System.out.println("Sample expenses added!");
                   break;

               case 13:
                   testMockRepository();
                   break;

               case 14:
                   switchRepository();
                   break;

               case 15:
                   testPolymorphism();
                   break;

               case 16:
                   manager.displayAdvancedStatistics();
                   break;

               case 17:
                   testHashMapPerformance();
                   break;

               case 18:
                   System.out.println("Exiting... Goodbye!");
                   isRunning = false;
                   break;
               default:
                   System.out.println("Invalid choice! Please enter 1-17.");
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

        System.out.println("Enter choice (1-17): ");
        System.out.println("1. 📝 Add Expense");
        System.out.println("2. 📋 Display All Expenses");
        System.out.println("3. 📊 Show Statistics");
        System.out.println("4. 🔍 Find by Category");
        System.out.println("5. 🗑️  Remove Expense");
        System.out.println("6. 💰 Show Most/Least Expensive");
        System.out.println("7. 📋 Display Expensive items");
        System.out.println("8. ⚠ Clear all expenses");
        System.out.println("9. ⚡ Display Expenses by priority");
        System.out.println("10. ⚡ Display category statistics");
        System.out.println("11. 🧪 Test Overloading");
        System.out.println("12. 🧪 Test: add and show preset expenses data");
        System.out.println("13. 🧪 Test Mock Repository");
        System.out.println("14. 🚪 Switch repository");
        System.out.println("15. 🧪 Test Polymorphism (Income + Expense)");
        System.out.println("16. 📊 Advanced Statistics (HashMap)");
        System.out.println("17. 🚪 Test HashMap Performance");
        System.out.println("18. 🚪 Exit");

    }

    /**
     * Add new expense
     */
    private static void addExpense(){
        System.out.println("=== Add New Expense ===");

        //Name
        String date;
        while (true){
            System.out.print("Enter date (YYYY-MM-DD): ");
        date = scanner.nextLine();

        if(date.trim().isEmpty()){
            System.out.println("Date cannot be empty! Try again.");
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

        //Description (optional)
        System.out.print("Enter description (optional, press Enter to skip): ");
        String description = scanner.nextLine();


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

        try{
            Expense expense = new Expense(date, amount, description, category, priority);
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
             List<Expense> found = manager.findByCategory(category);

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
        List<Expense> expenses = manager.findExpensesAbove(minAmount);

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
            List<Expense> found = manager.findByPriority(priority);

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

    /**
     * Demonstrate method overloading with various add methods.
     */
    private static void testOverloading(){
        System.out.println("\n=== Testing Method Overloading ===\n");

        //Method 1: Full version (all parameters)
        System.out.println("1️⃣ Adding with ALL parameters:");
        manager.addExpense("2025-01-20", 99.99, "Test expense with all fields",
                Category.OTHER, Priority.LOW);

        System.out.println();

        //Method 2: Without priority (defaults to MEDIUM)
        System.out.println("2️⃣ Adding WITHOUT priority:");
        manager.addExpense("2025-01-20", 50.00, "No priority specified",
                Category.FOOD);

        System.out.println();

        //Method 3: Without desctiption and priority
        System.out.println("3️⃣ Adding WITHOUT description and priority:");
        manager.addExpense("2025-01-20", 25.00, Category.TRANSPORT);

        System.out.println();

        //Method 4: Add multiple with varargs
        System.out.println("4️⃣ Adding MULTIPLE with varargs:");
        Expense bulk1 = new Expense("2025-01-20", 10, "Bulk 1", Category.FOOD);
        Expense bulk2 = new Expense("2025-01-20", 20, "Bulk 2", Category.FOOD);
        Expense bulk3 = new Expense("2025-01-20", 30, "Bulk 3", Category.FOOD);

        manager.addExpense(bulk1, bulk2, bulk3);

        System.out.println();
        System.out.println("✅ Overloading test complete!");
        System.out.println("Check 'Display All' to see added expenses.");
    }

    /**
     * Test Mock Repository
     */
  private static void testMockRepository(){
      System.out.println("\n=== Testing Mock Repository ===");

      ExpenseRepository mockRepo = new MockExpenseRepository();
      BudgetManager testManager = new BudgetManager(mockRepo);

      testManager.addExpense("2025-01-20", 50, Category.FOOD);
      testManager.displayAllExpenses();

      System.out.println("Count: " + testManager.getExpenseCount());  // 2 (fake)
      System.out.println("\n✅ Mock repository test complete!");
  }

    /**
     * Powerfull of Interfaces - switch repositories (swap implementation at runtime!)
     */
  private static void switchRepository(){

      System.out.println("\n=== Switch Repository ===");
      System.out.println("1. In-Memory Repository");
      System.out.println("2. Mock Repository (fake data)");
      System.out.print("Choose (1-2): ");

      int choice = scanner.nextInt();
      scanner.nextLine();

      if(choice == 1){
          repository = new InMemoryExpenseRepository();
      }else if(choice == 2){
          repository = new MockExpenseRepository();
      }else {
          System.out.println("Invalid choice!");
          return;
      }

      //Create NEW manager with new repository
      manager = new BudgetManager(repository);
      System.out.println("✅ Repository switched!");
  }

  private static void testPolymorphism(){
      System.out.println("\n=== Testing Polymorphism ===\n");

      //Parent reference -> Child objects (POLYMORPHISM!)
      Transaction t1 = new Expense("2025-01-20", 100, "Groceries", Category.FOOD);
      Transaction t2 = new Income("2025-06-19", 3000, "Monthly salary", "Job");

      //Store different types in ONE list!
      List<Transaction> transactions = new ArrayList<>();
      transactions.add(t1);
      transactions.add(t2);
      transactions.add(new Expense("2025-01-20", 50, "Gas", Category.TRANSPORT));
      transactions.add(new Income("2025-01-20", 500, "Freelance payment", "Client A"));
      transactions.add(new Budget("2025-01-01", 500, "Monthly food budget", "2025-01", Category.FOOD));

      System.out.println("All transactions:");
      System.out.println("─────────────────────────────────────");
      for (Transaction t : transactions) {
          t.displayInfo();  // Calls child version! (polymorphism)
      }
      System.out.println("─────────────────────────────────────");

      //Calculate totals
      double totalExpenses = 0;
      double totalIncome = 0;

      for(Transaction t : transactions){
          if(t.getType().equals("EXPENSE")){
              totalExpenses += t.getAmount();
          }else if(t.getType().equals("INCOME")){
              totalIncome += t.getAmount();
          }
      }

      System.out.printf("Total Expenses: %.2f PLN%n", totalExpenses);
      System.out.printf("Total Income: %.2f PLN%n", totalIncome);
      System.out.printf("Net Balance: %.2f PLN%n", totalIncome - totalExpenses);

      System.out.println("\n✅ Polymorphism test complete!");
  }

    /**
     * Demonstrate HashMap performance vs multiple ArrayList loops.
     */
  private static void testHashMapPerformance(){
      System.out.println("\n=== HashMap Performance Test ===\n");

      for(int i=0; i< 100; i++){
          Category randomCategory = Category.values()[i % Category.values().length];
          manager.addExpense("2025-01-20", Math.random() * 100,
                  "Total expense " + i, randomCategory);
      }

      System.out.println("Added 100 test expenses.\n");

      //Method 1: OLD WAY - Multiple lpps (one per category)
      long startOld = System.nanoTime();
      for(Category category : Category.values()){
          double total = manager.getTotalByCategory(category);
      }
      long endOld = System.nanoTime();
      long timeOld = endOld - startOld;

      //Method 2: NEW WAY - HashMap (single loop for all categories)
      long startNew = System.nanoTime();
      Map<Category, Double > totals = manager.calculateTotalsByCategory();
      long endNew = System.nanoTime();
      long timeNew = endNew - startNew;

      System.out.println("Performance Comparison:");
      System.out.println("───────────────────────────────────────");
      System.out.printf("OLD (multiple loops): %d ns%n", timeOld);
      System.out.printf("NEW (HashMap): %d ns%n", timeNew);
      System.out.printf("Speedup: %.1fx faster!%n", (double) timeOld / timeNew);
      System.out.println("───────────────────────────────────────");

      System.out.println("\n✅ HashMap is significantly faster!");
      System.out.println("With 1000s of expenses, difference would be HUGE!");

  }

}


