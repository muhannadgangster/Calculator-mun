package com.example.calculator.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.model.CalculatorMode
import com.example.calculator.model.UserSettings

@Composable
fun ModeSwitcher(
    currentMode: CalculatorMode,
    onModeChanged: (CalculatorMode) -> Unit,
    settings: UserSettings,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    val pillShape = RoundedCornerShape(24.dp)

    Box(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        GlassPanel(
            modifier = Modifier.wrapContentSize(),
            settings = settings,
            isDark = isDark,
            shape = pillShape
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .height(42.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ModePillItem(
                    title = "Normal",
                    icon = Icons.Default.Calculate,
                    isSelected = currentMode == CalculatorMode.NORMAL,
                    onClick = { onModeChanged(CalculatorMode.NORMAL) },
                    settings = settings,
                    isDark = isDark,
                    testTag = "mode_normal_button"
                )

                Spacer(modifier = Modifier.width(4.dp))

                ModePillItem(
                    title = "Scientific",
                    icon = Icons.Default.Science,
                    isSelected = currentMode == CalculatorMode.SCIENTIFIC,
                    onClick = { onModeChanged(CalculatorMode.SCIENTIFIC) },
                    settings = settings,
                    isDark = isDark,
                    testTag = "mode_scientific_button"
                )
            }
        }
    }
}

@Composable
private fun ModePillItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    settings: UserSettings,
    isDark: Boolean,
    testTag: String
) {
    val pillShape = RoundedCornerShape(20.dp)
    val neonActive = settings.neonLights && isSelected
    val neonAlpha = (settings.neonIntensity * 0.8f).coerceIn(0.2f, 1f)

    val activeBrush = Brush.linearGradient(
        listOf(
            Color(0xFF2563EB),
            Color(0xFF00E5FF)
        )
    )

    Box(
        modifier = Modifier
            .clip(pillShape)
            .then(
                if (isSelected) {
                    Modifier.background(activeBrush)
                } else {
                    Modifier.background(Color.Transparent)
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .testTag(testTag),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) Color.White else if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
            )
        }
    }
}
