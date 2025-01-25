package com.example.expensemanager.components.charts

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import com.example.expensemanager.models.Recurrence
import com.example.expensemanager.ui.theme.LabelSecondary
import com.github.tehras.charts.piechart.utils.toLegacyInt

class LabelDrawer(val recurrence: Recurrence, private val lastDay:Int?=-1):
    com.github.tehras.charts.bar.renderer.label.LabelDrawer {
    private val leftOffset = when (recurrence) {
        Recurrence.Weekly -> 50f
        Recurrence.Monthly -> 13f
        Recurrence.Yearly -> 32f
        else -> 0f
    }
    private val paint= android.graphics.Paint().apply{
        this.textAlign=android.graphics.Paint.Align.CENTER
        this.color = LabelSecondary.toLegacyInt()
        this.textSize = 42f
    }

//    drawScope: The scope in which drawing happens.
//canvas: The canvas on which to draw the label.
//label: The actual text label to be drawn.
//barArea: A rectangle representing the area of the bar.
//xAxisArea: A rectangle representing the area of the X-axis.
    override fun drawLabel(
        drawScope: DrawScope,
        canvas: Canvas,
        label: String,
        barArea: Rect,
        xAxisArea: Rect
    ) {
        val monthlyCondition=
            recurrence== Recurrence.Monthly && (
                    Integer.parseInt(label)%5==0 ||
                            Integer.parseInt(label) == 1 ||
                            Integer.parseInt(label) == lastDay
                    )

//    By adding leftOffset to barArea.left, you get the exact horizontal position where the label will be placed, ensuring it doesn’t overlap with the bar itself and looks visually appealing.
//    The label will be vertically positioned above the bar by a fixed distance (65 pixels).

        if(monthlyCondition|| recurrence!=Recurrence.Monthly)
            canvas.nativeCanvas.drawText(
                label,
                barArea.left+leftOffset,
                barArea.bottom+65f,
                paint
            )
    }
}