package com.example.expensemanager.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expensemanager.components.TableRow
import com.example.expensemanager.components.UnstyledTextField
import com.example.expensemanager.models.Recurrence
import com.example.expensemanager.ui.theme.BackgroundElevated
import com.example.expensemanager.ui.theme.DividerColor
import com.example.expensemanager.ui.theme.Shapes
import com.example.expensemanager.ui.theme.TopAppBarBackground
import com.example.expensemanager.ui.theme.Typography
import com.example.expensemanager.viewModels.AddViewModel
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Add(navController: NavController, vm: AddViewModel= viewModel()) {

    val state by vm.uiState.collectAsState()

    val recurrences = listOf(
        Recurrence.None,
        Recurrence.Daily,
        Recurrence.Weekly,
        Recurrence.Monthly,
        Recurrence.Yearly
    )

//    val categories = listOf("Groceries", "Bills", "Hobbies", "Take Out")

    val mContext = LocalContext.current


    // mDatePicker.maxDate=mCalendar.timeInMillis

    Scaffold(topBar = {
            MediumTopAppBar(
                title = { Text("Add") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = TopAppBarBackground
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp).padding(start=10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(BackgroundElevated)
                        .fillMaxWidth()
                ) {
                    TableRow(label = "Amount", detailContent = {
                        UnstyledTextField(
                            value = state.amount,
                            onValueChange = vm::setAmount,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("0") },
                            arrangement = Arrangement.End,
                            maxLines = 1,
                            textStyle = TextStyle(
                                textAlign = TextAlign.Right
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                    })
                    Divider(
                        modifier = Modifier
                            .padding(start = 16.dp),
                        thickness = 1.dp,
                        color = DividerColor
                    )

                    TableRow(label = "Recurrence", detailContent = {
                        var recurrenceMenuOpened by remember {
                            mutableStateOf(false)
                        }
                        TextButton(
                            onClick = { recurrenceMenuOpened = true },
                            shape = Shapes.large
                        ) {
                            Text(state.recurrence?.name ?: Recurrence.None.name)
                            DropdownMenu(
                                expanded = recurrenceMenuOpened,
                                onDismissRequest = { recurrenceMenuOpened = false }) {
                                recurrences.forEach { recurrences ->
                                    DropdownMenuItem(
                                        text = { Text(recurrences.name) },
                                        onClick = {
                                            vm.setRecurrence(recurrences)
                                            recurrenceMenuOpened = false
                                        })

                                }
                            }

                        }
                    })
                    Divider(
                        modifier = Modifier
                            .padding(start = 16.dp),
                        thickness = 1.dp,
                        color = DividerColor
                    )
                    var datePickerShowing by remember {
                        mutableStateOf(false)
                    }
                    TableRow(label = "Date", detailContent = {

                        TextButton(onClick = { datePickerShowing = true }) {
                            Text(state.date.toString())

                            if (datePickerShowing) {
                                DatePickerDialog(
                                    onDismissRequest = { datePickerShowing = false },
                                    onDateChange = { it ->
                                        vm.setDate(it)
                                        datePickerShowing = false
                                    },
                                    initialDate = state.date,
                                    title = {
                                        Text(
                                            "Select date",
                                            style = Typography.titleLarge
                                        )
                                    })
                            }
                        }
                    })
                    Divider(
                        modifier = Modifier
                            .padding(start = 16.dp),
                        thickness = 1.dp,
                        color = DividerColor
                    )

                    TableRow(label = "Note", detailContent = {
                        UnstyledTextField(
                            value = state.note,
                            placeholder = { Text("Leave some notes") },
                            arrangement = Arrangement.End,
                            onValueChange = vm::setNote,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                textAlign = TextAlign.End
                            ),

                            )
                    })
                    Divider(
                        modifier = Modifier
                            .padding(start = 16.dp),
                        thickness = 1.dp,
                        color = DividerColor
                    )

                    TableRow(label = "Category", detailContent = {
                        var categoriesMenuOpened by remember {
                            mutableStateOf(false)
                        }
                        TextButton(
                            onClick = { categoriesMenuOpened = true }, shape = Shapes.large
                        ) {
                            Text(
                                state.category?.name?: "Select a category first",
                                color = state.category?.color ?: Color.White
                            )
                            DropdownMenu(
                                expanded = categoriesMenuOpened,
                                onDismissRequest = { categoriesMenuOpened = false }) {
                                state.categories?.forEach { category ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Surface(
                                                    modifier = Modifier.size(10.dp),
                                                    shape = CircleShape,
                                                    color = category.color
                                                ) {}
                                                Text(
                                                    category.name, modifier = Modifier.padding(start = 8.dp)
                                                )
                                            }
                                        },
                                        onClick = {
                                            vm.setCategory(category)
                                            categoriesMenuOpened = false
                                        })

                                }
                            }

                        }
                    })

                }
                            Button(
                                onClick = vm::submitExpense,
                                modifier = Modifier.padding(16.dp),
                                shape = Shapes.large,
                                enabled = state.category!=null
                            ) {
                                Text("Submit expense")
                            }
                        }

        })
}
