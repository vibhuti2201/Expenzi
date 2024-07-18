package com.example.expensemanager.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensemanager.mock.mockExpense
import com.example.expensemanager.models.Expense
import com.example.expensemanager.models.groupedByDay
import com.example.expensemanager.ui.theme.ExpenseManagerTheme

@Composable
fun ExpensesList(expenses: List<Expense>, modifier: Modifier= Modifier)
{
   val groupedExpenses= expenses.groupedByDay()

   LazyColumn(modifier=modifier) {
      itemsIndexed(
         ArrayList(groupedExpenses.keys),
         key={_, date-> date}
      ){  index, date->
         if(groupedExpenses[date]!=null)
         {
            ExpensesDayGroup(date = date, dayExpenses = groupedExpenses[date]!!,modifier= Modifier.padding(top=24.dp))

         }

      }
   }
}

@Preview(uiMode= Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview(){
   ExpenseManagerTheme {
      ExpensesList(expenses = mockExpense)
   }
}