package com.example.expensemanager.mock

import androidx.compose.ui.graphics.Color
import com.example.expensemanager.models.Category
import com.example.expensemanager.models.Recurrence
import io.github.serpro69.kfaker.Faker
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

val faker= Faker()

val mockCategories= listOf(
    Category(
        "Bills",
        Color(
            faker.random.nextInt(0,255),
            faker.random.nextInt(0,255),
            faker.random.nextInt(0,255)
        )
    ),
    Category("Subscriptions", Color(
        faker.random.nextInt(0,255),
        faker.random.nextInt(0,255),
        faker.random.nextInt(0,255)
    )
    ),
    Category("Take", Color(
        faker.random.nextInt(0,255),
        faker.random.nextInt(0,255),
        faker.random.nextInt(0,255)
    )
    ),
    Category("Hobbies", Color(
        faker.random.nextInt(0,255),
        faker.random.nextInt(0,255),
        faker.random.nextInt(0,255)
    )
    )
)

val mockExpense: List<Unit> = List(30){ index->
    Expense(
        id=index,
        amount = faker.random.nextInt(min = 1, max = 999)
            .toDouble() + faker.random.nextDouble(),
        date = LocalDateTime.now().minus(
            faker.random.nextInt(min = 300, max = 345600).toLong(),
            ChronoUnit.SECONDS
        ),
        recurrence = faker.random.randomValue(
            listOf(
                Recurrence.None,
                Recurrence.Daily,
                Recurrence.Monthly,
                Recurrence.Weekly,
                Recurrence.Yearly
            )
        ),
        note = faker.australia.animals(),
        category = faker.random.randomValue(mockCategories)
    )
}

fun Expense(id: Int, amount: Double, date: LocalDateTime?, recurrence: Recurrence, note: String, category: Category) {

}
