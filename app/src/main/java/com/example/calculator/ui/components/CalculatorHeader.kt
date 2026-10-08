package com.example.calculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.model.UserSettings

@Composable
fun CalculatorHeader(
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit,
    settings: UserSettings,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: History button
        IconButton(
            onClick = onHistoryClick,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(
                    if (isDark) Color(0x22FFFFFF) else Color(0x33000000)
                )
                .testTag("header_history_button")
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = "Calculation History",
                tint = if (isDark) Color.White else Color(0xFF0F172A),
                modifier = Modifier.size(22.dp)
            )
        }

        // Center: Crown + MUN Calculator title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Stylized futuristic crown / diamond icon with electric neon cyan gradient
            Icon(
                imageVector = Icons.Outlined.Diamond,
                contentDescription = "MUN Logo",
                tint = Color(0xFF00E5FF),
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 4.dp)
            )
            Text(
                text = "MUN Calculator",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDark) Color.White else Color(0xFF0F172A),
                letterSpacing = 0.5.sp
            )
        }

        // Right: Settings button
        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(
                    if (isDark) Color(0x22FFFFFF) else Color(0x33000000)
                )
                .testTag("header_settings_button")
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = if (isDark) Color.White else Color(0xFF0F172A),
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
