package com.example.calculator.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.model.ThemeMode
import com.example.calculator.model.UserSettings
import com.example.calculator.ui.components.ButtonType
import com.example.calculator.ui.components.GlassCalculatorButton
import com.example.calculator.ui.components.GlassPanel

@Composable
fun ThemeSettingsScreen(
    settings: UserSettings,
    isDark: Boolean,
    onBack: () -> Unit,
    onThemeSelect: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("theme_settings_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = if (isDark) Color.White else Color(0xFF0F172A)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Theme",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDark) Color.White else Color(0xFF0F172A)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ThemeCard(
                    title = "Light",
                    theme = ThemeMode.LIGHT,
                    isSelected = settings.themeMode == ThemeMode.LIGHT,
                    onClick = { onThemeSelect(ThemeMode.LIGHT) },
                    modifier = Modifier.weight(1f)
                )
                ThemeCard(
                    title = "Dark",
                    theme = ThemeMode.DARK,
                    isSelected = settings.themeMode == ThemeMode.DARK,
                    onClick = { onThemeSelect(ThemeMode.DARK) },
                    modifier = Modifier.weight(1f)
                )
                ThemeCard(
                    title = "System",
                    theme = ThemeMode.SYSTEM,
                    isSelected = settings.themeMode == ThemeMode.SYSTEM,
                    onClick = { onThemeSelect(ThemeMode.SYSTEM) },
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "Preview",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
            )

            // Live Preview Card
            GlassPanel(
                modifier = Modifier.fillMaxWidth(),
                settings = settings,
                isDark = isDark,
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Mini display
                    GlassPanel(
                        modifier = Modifier.fillMaxWidth(),
                        settings = settings,
                        isDark = isDark,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "12 × 8",
                                fontSize = 16.sp,
                                color = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "96",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDark) Color.White else Color(0xFF0F172A)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Mini keypad preview
                    val previewSpacing = 8.dp
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(previewSpacing)
                    ) {
                        GlassCalculatorButton("AC", {}, Modifier.weight(1f).height(48.dp), ButtonType.FUNCTION, settings, isDark, fontSize = 16.sp)
                        GlassCalculatorButton("⌫", {}, Modifier.weight(1f).height(48.dp), ButtonType.FUNCTION, settings, isDark, fontSize = 16.sp)
                        GlassCalculatorButton("%", {}, Modifier.weight(1f).height(48.dp), ButtonType.FUNCTION, settings, isDark, fontSize = 16.sp)
                        GlassCalculatorButton("÷", {}, Modifier.weight(1f).height(48.dp), ButtonType.OPERATOR, settings, isDark, fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.height(previewSpacing))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(previewSpacing)
                    ) {
                        GlassCalculatorButton("7", {}, Modifier.weight(1f).height(48.dp), ButtonType.NUMBER, settings, isDark, fontSize = 18.sp)
                        GlassCalculatorButton("8", {}, Modifier.weight(1f).height(48.dp), ButtonType.NUMBER, settings, isDark, fontSize = 18.sp)
                        GlassCalculatorButton("9", {}, Modifier.weight(1f).height(48.dp), ButtonType.NUMBER, settings, isDark, fontSize = 18.sp)
                        GlassCalculatorButton("×", {}, Modifier.weight(1f).height(48.dp), ButtonType.OPERATOR, settings, isDark, fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.height(previewSpacing))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(previewSpacing)
                    ) {
                        GlassCalculatorButton("4", {}, Modifier.weight(1f).height(48.dp), ButtonType.NUMBER, settings, isDark, fontSize = 18.sp)
                        GlassCalculatorButton("5", {}, Modifier.weight(1f).height(48.dp), ButtonType.NUMBER, settings, isDark, fontSize = 18.sp)
                        GlassCalculatorButton("6", {}, Modifier.weight(1f).height(48.dp), ButtonType.NUMBER, settings, isDark, fontSize = 18.sp)
                        GlassCalculatorButton("−", {}, Modifier.weight(1f).height(48.dp), ButtonType.OPERATOR, settings, isDark, fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.height(previewSpacing))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(previewSpacing)
                    ) {
                        GlassCalculatorButton("1", {}, Modifier.weight(1f).height(48.dp), ButtonType.NUMBER, settings, isDark, fontSize = 18.sp)
                        GlassCalculatorButton("2", {}, Modifier.weight(1f).height(48.dp), ButtonType.NUMBER, settings, isDark, fontSize = 18.sp)
                        GlassCalculatorButton("3", {}, Modifier.weight(1f).height(48.dp), ButtonType.NUMBER, settings, isDark, fontSize = 18.sp)
                        GlassCalculatorButton("+", {}, Modifier.weight(1f).height(48.dp), ButtonType.OPERATOR, settings, isDark, fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.height(previewSpacing))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(previewSpacing)
                    ) {
                        GlassCalculatorButton("±", {}, Modifier.weight(1f).height(48.dp), ButtonType.FUNCTION, settings, isDark, fontSize = 16.sp)
                        GlassCalculatorButton("0", {}, Modifier.weight(1f).height(48.dp), ButtonType.NUMBER, settings, isDark, fontSize = 18.sp)
                        GlassCalculatorButton(".", {}, Modifier.weight(1f).height(48.dp), ButtonType.NUMBER, settings, isDark, fontSize = 18.sp)
                        GlassCalculatorButton("=", {}, Modifier.weight(1f).height(48.dp), ButtonType.ACCENT, settings, isDark, fontSize = 20.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
