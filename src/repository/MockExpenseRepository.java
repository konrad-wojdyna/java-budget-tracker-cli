package repository;

import model.Category;
import model.Expense;
import model.Priority;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock repository that returns fake data for testing.
 * Useful for testing BudgetManager without real data.
 */
public class MockExpenseRepository implements ExpenseRepository{

    private final List<Expense> fakeData;

    public MockExpenseRepository(){
        this.fakeData = new ArrayList<>();
        fakeData.add(new Expense("2025-01-01", 10.00, "Fake Coffee", Category.FOOD));
        fakeData.add(new Expense("2024-04-08", 15.90, "Fake Bus", Category.TRANSPORT));
        fakeData.add(new Expense("2025-10-11", 40.50, "Netflix", Category.ENTERTAINMENT));
        fakeData.add(new Expense("2025-12-30", 132.75, "Medicines", Category.HEALTHCARE));
        fakeData.add(new Expense("2025-06-20", 221.15, "TV", Category.HOUSING));
    }

    @Override
    public void save(Expense expense) {
        if(expense == null){
            throw new IllegalArgumentException("Expense cannot be null!");
        }
        fakeData.add(expense);
        System.out.println("[MOCK] Pretending to save: " + expense.getDescription());
    }

    @Override
    public void saveAll(Expense... expenses) {

        if(expenses == null || expenses.length == 0){
            throw new IllegalArgumentException("Expenses cannot be null");
        }

        for(Expense expense : expenses){
            if(expense != null){
                save(expense);
            }
        }

        System.out.println("[MOCK] Pretending to save " + expenses.length + " expenses");
    }

    @Override
    public List<Expense> findAll() {
        //Return fake data
        return new ArrayList<>(fakeData);
    }

    @Override
    public Expense findById(int index) {

        if(index < 0 || index >= fakeData.size()){
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        return fakeData.get(index);
    }

    @Override
    public List<Expense> findByCategory(Category category) {

        if(category == null){
            throw new IllegalArgumentException("Provide category name!");
        }

        List<Expense> expensesByCategory = new ArrayList<>();
        for(Expense expense : fakeData){
            if(expense.getCategory() == category){
                expensesByCategory.add(expense);
            }
        }

        return expensesByCategory;
    }

    @Override
    public List<Expense> findByPriority(Priority priority) {

        if(priority == null){
            throw new IllegalArgumentException("Provide priority!");
        }

        List<Expense> expensesByPriority = new ArrayList<>();
        for(Expense expense : fakeData){
            if(expense.getPriority() == priority){
                expensesByPriority.add(expense);
            }
        }

        return expensesByPriority;
    }

    @Override
    public List<Expense> findExpensesAbove(double amount) {

        if(amount < 0){
            throw new IllegalArgumentException("Amount must be positive");
        }

        List<Expense> expensesAboveAmount = new ArrayList<>();
        for(Expense expense : fakeData){
            if(expense.getAmount() >= amount){
                expensesAboveAmount.add(expense);
            }
        }

        return  expensesAboveAmount;
    }

    @Override
    public void delete(int index) {
       if(index < 0 || index >= fakeData.size()){
           throw new IndexOutOfBoundsException("Invalid index: " + index);
       }

       Expense removed = fakeData.remove(index);
        System.out.println("[MOCK] Deleted: " + removed.getDescription());
    }

    @Override
    public void deleteAll() {
        int count = fakeData.size();
        fakeData.clear();
        System.out.println("[MOCK] Cleared " + count + " expenses");
    }

    @Override
    public int count() {
        return fakeData.size();
    }

    @Override
    public boolean isEmpty() {
        return fakeData.isEmpty();
    }
}
