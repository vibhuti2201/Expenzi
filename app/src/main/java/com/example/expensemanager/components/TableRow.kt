package com.example.expensemanager.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.expensemanager.R
import com.example.expensemanager.ui.theme.BackgroundElevated
import com.example.expensemanager.ui.theme.Destructive
import com.example.expensemanager.ui.theme.TextPrimary
import com.example.expensemanager.ui.theme.Typography


@Composable
fun TableRow(
    modifier: Modifier = Modifier,
    label: String?=null,
    hasArrow: Boolean = false,
    onClick:(String)-> Unit= {},
    isDestructive: Boolean = false,
    detailContent: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable() (RowScope.() -> Unit)? = null
) {

    val textColor = if (isDestructive) Destructive else TextPrimary

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundElevated)
                .clickable {
                    if (label != null) {
                        onClick(label)
                    }
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            if(label!=null) {
                Text(
                    text = label,
                    style = Typography.bodyMedium,
                    color = textColor,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            if (content != null) {
                content()
            }
            if (hasArrow) {
                Icon(
                    painterResource(id = R.drawable.chevron_right),
                    contentDescription = "Right arrow",
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            if(detailContent!=null)
            {
                detailContent()
            }
        }
    }


