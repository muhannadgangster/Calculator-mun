package com.example.calculator.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.engine.CalculatorEngine
import com.example.calculator.model.CalculatorMode
import com.example.calculator.model.UserSettings

@Composable
fun CalculatorDisplay(
    expression: String,
    result: String,
    isError: Boolean,
    angleMode: CalculatorEngine.AngleMode,
    calculatorMode: CalculatorMode,
    settings: UserSettings,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    val scrollStateExpr = rememberScrollState()

    // Auto-scroll to end of expression when it changes
    LaunchedEffect(expression) {
        scrollStateExpr.scrollTo(scrollStateExpr.maxValue)
    }

    GlassPanel(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        settings = settings,
        isDark = isDark
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.End
        ) {
            // Top row: Angle indicator badge (if in Scientific mode) + Expression
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (calculatorMode == CalculatorMode.SCIENTIFIC) {
                    Text(
                        text = angleMode.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) Color(0xFF38BDF8) else Color(0xFF0284C7),
                        modifier = Modifier.testTag("angle_mode_tag")
                    )
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                // Expression text with horizontal scroll
                Row(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .horizontalScroll(scrollStateExpr)
                ) {
                    Text(
                        text = if (expression.isEmpty()) " " else expression,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        modifier = Modifier.testTag("calc_expression_text")
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main result display
            val resultFontSize = when {
                result.length > 14 -> 28.sp
                result.length > 10 -> 36.sp
                result.length > 7 -> 44.sp
                else -> 52.sp
            }

            val resultColor = when {
                isError -> Color(0xFFEF4444)
                isDark -> Color.White
                else -> Color(0xFF0F172A)
            }

            Text(
                text = if (result.isEmpty()) "0" else result,
                fontSize = resultFontSize,
                fontWeight = FontWeight.Bold,
                color = resultColor,
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("calc_result_text")
            )
        }
    }
}
