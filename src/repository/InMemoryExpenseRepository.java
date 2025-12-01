package repository;

import model.Category;
import model.Expense;
import model.Priority;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of ExpenseRepository using ArrayList.
 *
 * This implementation stores expenses in memory and will lose
 * data when application stops. Useful for development and testing.
 *
 * @author Konrad Wojdyna
 * @version 0.1.0
 */

public class InMemoryExpenseRepository implements ExpenseRepository{

    private final ArrayList<Expense> expenses;

    /**
     * Creates empty in-memory repository.
     */
    public InMemoryExpenseRepository(){
        this.expenses = new ArrayList<>();
    }

    @Override
    public void save(Expense expense) {
       if(expense == null){
           throw new IllegalArgumentException("Expense cannot be null");
       }
       expenses.add(expense);
    }

    @Override
    public void saveAll(Expense... expenses) {
       if(expenses == null || expenses.length == 0){
           return;
       }

       for(Expense expense : expenses){
           if(expense != null){
               save(expense);
           }
       }
    }

    @Override
    public List<Expense> findAll() {
        return new ArrayList<>(expenses);
    }

    @Override
    public Expense findById(int index) {
        if(index < 0 || index >= expenses.size()){
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        return expenses.get(index);
    }

    @Override
    public List<Expense> findByCategory(Category category) {
        if(category == null){
            throw new IllegalArgumentException("Category cannot be null");
        }

        List<Expense> result = new ArrayList<>();
        for(Expense expense : expenses){
            if(expense.getCategory() == category){
                result.add(expense);
            }
        }

        return result;
    }

    @Override
    public List<Expense> findByPriority(Priority priority) {
        if(priority == null){
            throw new IllegalArgumentException("Priority cannot be null");
        }

        List<Expense> result = new ArrayList<>();
        for(Expense expense: expenses){
            if(expense.getPriority() == priority){
                result.add(expense);
            }
        }

        return result;
    }

    @Override
    public List<Expense> findExpensesAbove(double amount) {

        if(amount < 0){
            throw new IllegalArgumentException("Amount must be positive");
        }

        List<Expense> result = new ArrayList<>();
        for(Expense expense : expenses){
            if(expense.getAmount() >= amount){
                result.add(expense);
            }
        }

        return result;
    }

    @Override
    public void delete(int index) {
       if(index < 0 || index >= expenses.size()){
           throw new IndexOutOfBoundsException("Invalid index: " + index);
       }

       expenses.remove(index);
    }

    @Override
    public void deleteAll() {
        expenses.clear();
    }

    @Override
    public int count() {
        return expenses.size();
    }

    @Override
    public boolean isEmpty() {
        return expenses.isEmpty();
    }
}
