package com.anisulnz.vwallet.controller;

import com.anisulnz.vwallet.domain.Expense;
import com.anisulnz.vwallet.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.anisulnz.vwallet.repository.ExpenseRepository;
import com.anisulnz.vwallet.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ExpenseController {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users/{userId}/expenses")
    public List<Expense> getExpensesByUserId(@PathVariable Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    @PostMapping("/users/{userId}/expenses")
    public Expense addExpense(@PathVariable Long userId,
                              @Valid @RequestBody Expense expense) {
        return userRepository.findById(userId)
                .map(user -> {
                    expense.setUser(user);
                    return expenseRepository.save(expense);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @PutMapping("/users/{userId}/expenses/{expenseId}")
    public Expense updateExpense(@PathVariable Long userId,
                                 @PathVariable Long expenseId,
                                 @Valid @RequestBody Expense expenseRequest) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return expenseRepository.findById(expenseId)
                .map(expense -> {
                    expense.setAmount(expenseRequest.getAmount());
                    expense.setDetails(expenseRequest.getDetails());
                    return expenseRepository.save(expense);
                }).orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));
    }

    @DeleteMapping("/users/{userId}/expenses/{expenseId}")
    public ResponseEntity deleteExpense(@PathVariable Long userId,
                                        @PathVariable Long expenseId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return expenseRepository.findById(expenseId)
                .map(expense -> {
                    expenseRepository.delete(expense);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));
    }


}
