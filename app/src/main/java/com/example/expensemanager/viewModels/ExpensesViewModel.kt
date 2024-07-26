package com.example.expensemanager.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensemanager.db
import com.example.expensemanager.models.Expense
import com.example.expensemanager.models.Recurrence
import com.example.expensemanager.utils.calculateDateRange
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ExpensesState(
    val recurrence: Recurrence= Recurrence.Daily,
    val sumTotal: Double=12850.98,
    val expenses: List<Expense> = listOf()
)
class ExpensesViewModel :ViewModel()
{
    private val _uiState= MutableStateFlow(ExpensesState())
    val uiState: StateFlow<ExpensesState> = _uiState.asStateFlow()

    init{
        _uiState.update { currentState->
            currentState.copy(
                expenses = db.query<Expense>().find()
            )
        }
        viewModelScope.launch (Dispatchers.IO){
            setRecurrence(Recurrence.Daily)
        }
    }
    fun setRecurrence(recurrence: Recurrence){
        val (start, end) = calculateDateRange(recurrence, 0)

        val filteredExpenses= uiState.value.expenses.filter { expense->
            (expense.date.toLocalDate().isAfter(start) && expense.date.toLocalDate()
                .isBefore(end)) || expense.date.toLocalDate()
                .isEqual(start) || expense.date.toLocalDate().isEqual(end)

        }

        val sumTotal = filteredExpenses.sumOf { it.amount }

        _uiState.update { currentState->
            currentState.copy(
                recurrence=recurrence,
                expenses = filteredExpenses,
                sumTotal = sumTotal
            )
        }
    }
}