package repository;

import model.Category;
import model.Expense;
import model.Priority;

import java.util.List;

/**
 * Repository interface for expense data access.
 * Defines the contract for expense storage operations.
 * Implementations can use different storage mechanisms
 * (in-memory, file, database) while maintaining the same API.
 *
 * @author Konrad Wojdyna
 * @version 0.1.0
 */

public interface ExpenseRepository {

    /**
     * Saves an expense to the repository.
     *
     * @param expense the expense to save.
     * @throws  IllegalArgumentException if expense is null
     */
    void save(Expense expense);

    /**
     * Save an expense to the repository.
     *
     * @param expenses the expense to save
     * @throws IllegalArgumentException if expense is null
     */
    void saveAll(Expense ...expenses);

    /**
     * Retrieves all expenses from the repository.
     *
     * @return list of all expenses (empty if none)
     */
    List<Expense> findAll();

    /**
     * Finds expese by index position.
     *
     * @param index the index (0-based)
     * @return the expense at that index
     */
    Expense findById(int index);

    /**
     * Finds all expenses in specified category.
     *
     * @param category the category to search
     * @return list of matching expenses (empty if none)
     */
    List<Expense> findByCategory(Category category);

    /**
     * Finds all expenses with specified priority.
     *
     * @param priority the priority level
     * @return list of matching expenses (empty if none)
     */
    List<Expense> findByPriority(Priority priority);


    /**
     * Finds all expenses above specified amount.
     *
     * @param amount minimum amount threshold
     * @return list of matching expenses (empty if none)
     */
    List<Expense> findExpensesAbove(double amount);

    /**
     * Deletes expense at specified index.
     *
     * @param index the index (0-based)
     * @throws IndexOutOfBoundsException if index invalid
     */
    void delete(int index);

    /**
     * Deletes all expenses from repository.
     */
    void deleteAll();

    /**
     * Returns total count of expenses.
     *
     * @return number of expenses
     */
    int count();

    /**
     * Checks if repository is empty.
     *
     * @return true if no expenses exist.
     */
    boolean isEmpty();

}
