package com.example.expensemanager.pages

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensemanager.components.ExpensesList
import com.example.expensemanager.components.PickerTrigger
import com.example.expensemanager.mock.mockExpense
import com.example.expensemanager.models.Recurrence
import com.example.expensemanager.ui.theme.ExpenseManagerTheme
import com.example.expensemanager.ui.theme.LabelSecondary
import com.example.expensemanager.ui.theme.TopAppBarBackground
import com.example.expensemanager.ui.theme.Typography
import com.example.expensemanager.viewModels.ExpensesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Expenses(navController: NavController,vm: ExpensesViewModel= androidx.lifecycle.viewmodel.compose.viewModel()) {

    val recurrences = listOf( 
        Recurrence.Daily,
        Recurrence.Weekly,
        Recurrence.Monthly,
        Recurrence.Yearly
    )
val state by vm.uiState.collectAsState()
    var recurrenceMenuOpened by remember{
       mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Expenses") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = TopAppBarBackground
                )
            )
        },
        content={ innerPadding->
            Column (
                modifier= androidx.compose.ui.Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
               ){
                  Row(verticalAlignment = Alignment.CenterVertically){
                     Text("Total for:",
                        style = Typography.bodyMedium,
                         )
                      PickerTrigger(
                          state.recurrence.target?:Recurrence.None.target,
                          onClick = { recurrenceMenuOpened=!recurrenceMenuOpened },
                          modifier = Modifier.padding(start=16.dp)
                      )
                      DropdownMenu(
                          expanded = recurrenceMenuOpened,
                          onDismissRequest = { recurrenceMenuOpened = false }) {
                          recurrences.forEach { recurrences ->
                              DropdownMenuItem(
                                  text = { Text(recurrences.target) },
                                   onClick = {
                                      vm.setRecurrence(recurrences)
                                      recurrenceMenuOpened = false
                                  })

                          }
                      }
                  }
                Row(modifier=Modifier.padding(vertical=32.dp))
                {
                   Text(
                       "$",
                       style=Typography.bodyMedium,
                       color= LabelSecondary,
                       modifier=Modifier.padding(end=4.dp,top=3.dp))
                    Text("${state.sumTotal}",style=Typography.titleLarge)
                }
                ExpensesList(expenses = mockExpense, modifier = Modifier.weight(1f))
               }

        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExpensesPreview(){
    ExpenseManagerTheme {
        Expenses(navController = rememberNavController())
    }
}