package com.example.expensemanager.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.expensemanager.R
import com.example.expensemanager.ui.theme.FillTertiary
import com.example.expensemanager.ui.theme.Shapes
import com.example.expensemanager.ui.theme.Typography

@Composable
fun PickerTrigger(
    label:String="",
    onClick:()-> Unit,
    modifier: Modifier=Modifier
) {
    Surface(
        shape=Shapes.medium,
        color= FillTertiary,
        modifier= modifier,
        onClick = onClick
    ) {
        Row(
             verticalAlignment = Alignment.CenterVertically
        ){
            Text(label, style=Typography.titleSmall)
            Image(
                painterResource(R.drawable.unfold_more),
                contentDescription="Open picker",
                modifier=Modifier.padding(start=10.dp)
            )
        }
    }

}
