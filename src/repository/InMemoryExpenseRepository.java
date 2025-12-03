package repository;

import exception.DuplicateExpenseException;
import exception.ExpenseNotFoundException;
import exception.InvalidExpenseDataException;
import exception.RepositoryException;
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
 * @version 0.2.0
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
           throw new InvalidExpenseDataException("Expense cannot be null", "expense", null);
       }

       List<Expense> allExpenses = findAll();

       for(Expense exp : allExpenses){
           if(exp.getDate().equals(expense.getDate()) && exp.getDescription().equals(expense.getDescription())){
               throw new DuplicateExpenseException("Duplicate found:", exp.getDate(), exp.getDescription());
           }
       }

       try{
         expenses.add(expense);
       }catch (Exception e){
           throw new RepositoryException("Failed to save expense: " + expense.getDescription(), e);
       }
    }

    @Override
    public void saveAll(Expense... expenses) {
       if(expenses == null){
           return;
       }

       int savedCount = 0;
       List<String> errors = new ArrayList<>();

       for(Expense expense : expenses){
           try{
           if(expense != null){
               save(expense);
               savedCount++;
           }
           }catch (Exception e){
               errors.add("Failed to save expense: " + e.getMessage());
           }
       }

       if(!errors.isEmpty()){
           System.out.println("⚠️  Warnings during bulk save:");
           errors.forEach(System.out::println);
       }

        System.out.println("✓ Saved " + savedCount + " of " +
                expenses.length + " expenses");
    }

    @Override
    public List<Expense> findAll() {
        try{
        return new ArrayList<>(expenses);
        }catch (Exception e){
            throw new RepositoryException("Failed to restrieve expenses", e);
        }
    }

    @Override
    public Expense findById(int index) throws ExpenseNotFoundException {
        if(index < 0 ){
            throw new InvalidExpenseDataException("Index cannot be negative", "index", index);
        }

        if(index >= expenses.size()){
            throw new ExpenseNotFoundException("No expense found at index: " + index +
                    " (size: " + expenses.size() + ")", index);
        }

        return expenses.get(index);
    }

    @Override
    public List<Expense> findByCategory(Category category) {
        if(category == null){
            throw new InvalidExpenseDataException("Category cannot be null", "category", null);
        }

        List<Expense> result = new ArrayList<>();

        try{
        for(Expense expense : expenses){
            if(expense.getCategory() == category){
                result.add(expense);
            }
        }
        }catch (Exception e){
          throw new RepositoryException("Failed to find expenses by category: " + category, e);
        }

        return result;
    }

    @Override
    public List<Expense> findByPriority(Priority priority) {
        if(priority == null){
            throw new InvalidExpenseDataException("Priority cannot be null", "priority", null);
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
            throw new InvalidExpenseDataException("Amount must be positive", "amount", amount);
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
    public void delete(int index) throws ExpenseNotFoundException {

       if(index < 0){
           throw new InvalidExpenseDataException("Index cannot be negative", "index", index);
       }

       if(index >= expenses.size()){
           throw new ExpenseNotFoundException("Cannot delete - no expense at index: " + index, index);
       }

       try{
           expenses.remove(index);
       }catch (Exception e){
           throw new RepositoryException("Failed to delete expense at index: " + index, e);
       }

       expenses.remove(index);
    }

    @Override
    public void deleteAll() {
        try{
        expenses.clear();
        }catch (Exception e){
           throw new RepositoryException("Failed to clear all expenses", e);
        }
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
