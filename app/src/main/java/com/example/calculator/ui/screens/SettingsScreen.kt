package com.example.calculator.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.model.AppScreen
import com.example.calculator.model.CalculatorMode
import com.example.calculator.model.ThemeMode
import com.example.calculator.model.UserSettings
import com.example.calculator.ui.components.GlassPanel

@Composable
fun SettingsScreen(
    settings: UserSettings,
    isDark: Boolean,
    onBack: () -> Unit,
    onNavigate: (AppScreen) -> Unit,
    onUpdateSettings: ((UserSettings) -> UserSettings) -> Unit,
    onResetSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }
    val context = LocalContext.current
    var showResetDialog by remember { mutableStateOf(false) }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset Customization", fontWeight = FontWeight.Bold) },
            text = { Text("Reset all glass effects, theme, and wallpaper to default?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onResetSettings()
                        showResetDialog = false
                        Toast.makeText(context, "Settings reset to default", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Reset", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

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
                modifier = Modifier.testTag("settings_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = if (isDark) Color.White else Color(0xFF0F172A)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Settings",
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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Theme Section
            Text(
                text = "Theme",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ThemeCard(
                    title = "Light",
                    theme = ThemeMode.LIGHT,
                    isSelected = settings.themeMode == ThemeMode.LIGHT,
                    onClick = { onUpdateSettings { it.copy(themeMode = ThemeMode.LIGHT) } },
                    modifier = Modifier.weight(1f)
                )
                ThemeCard(
                    title = "Dark",
                    theme = ThemeMode.DARK,
                    isSelected = settings.themeMode == ThemeMode.DARK,
                    onClick = { onUpdateSettings { it.copy(themeMode = ThemeMode.DARK) } },
                    modifier = Modifier.weight(1f)
                )
                ThemeCard(
                    title = "System",
                    theme = ThemeMode.SYSTEM,
                    isSelected = settings.themeMode == ThemeMode.SYSTEM,
                    onClick = { onUpdateSettings { it.copy(themeMode = ThemeMode.SYSTEM) } },
                    modifier = Modifier.weight(1f)
                )
            }

            // Calculator Mode Section
            Text(
                text = "Calculator Mode",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
            )

            GlassPanel(
                modifier = Modifier.fillMaxWidth(),
                settings = settings,
                isDark = isDark,
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val normalActive = settings.defaultCalculatorMode == CalculatorMode.NORMAL
                    val sciActive = settings.defaultCalculatorMode == CalculatorMode.SCIENTIFIC

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .then(
                                if (normalActive) {
                                    Modifier.background(Brush.linearGradient(listOf(Color(0xFF2563EB), Color(0xFF00E5FF))))
                                } else Modifier
                            )
                            .clickable {
                                onUpdateSettings { it.copy(defaultCalculatorMode = CalculatorMode.NORMAL) }
                            }
                            .padding(vertical = 10.dp)
                            .testTag("setting_mode_normal"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = null,
                                tint = if (normalActive) Color.White else if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Normal",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (normalActive) Color.White else if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .then(
                                if (sciActive) {
                                    Modifier.background(Brush.linearGradient(listOf(Color(0xFF2563EB), Color(0xFF00E5FF))))
                                } else Modifier
                            )
                            .clickable {
                                onUpdateSettings { it.copy(defaultCalculatorMode = CalculatorMode.SCIENTIFIC) }
                            }
                            .padding(vertical = 10.dp)
                            .testTag("setting_mode_scientific"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Science,
                                contentDescription = null,
                                tint = if (sciActive) Color.White else if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Scientific",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (sciActive) Color.White else if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
                            )
                        }
                    }
                }
            }

            // Quick Toggle Controls Card
            GlassPanel(
                modifier = Modifier.fillMaxWidth(),
                settings = settings,
                isDark = isDark,
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    SettingToggleRow(
                        icon = Icons.Outlined.Vibration,
                        title = "Haptic Feedback",
                        checked = settings.hapticFeedback,
                        isDark = isDark,
                        onCheckedChange = { checked ->
                            onUpdateSettings { it.copy(hapticFeedback = checked) }
                        },
                        testTag = "setting_haptic_switch"
                    )

                    HorizontalDivider(color = if (isDark) Color(0x1AFFFFFF) else Color(0x1A000000))

                    SettingToggleRow(
                        icon = Icons.Outlined.VolumeUp,
                        title = "Sound Effects",
                        checked = settings.soundEffects,
                        isDark = isDark,
                        onCheckedChange = { checked ->
                            onUpdateSettings { it.copy(soundEffects = checked) }
                        },
                        testTag = "setting_sound_switch"
                    )

                    HorizontalDivider(color = if (isDark) Color(0x1AFFFFFF) else Color(0x1A000000))

                    SettingNavigationRow(
                        icon = Icons.Outlined.AutoAwesome,
                        title = "Glass Customization",
                        isDark = isDark,
                        onClick = { onNavigate(AppScreen.GlassSettings) },
                        testTag = "setting_glass_nav"
                    )

                    HorizontalDivider(color = if (isDark) Color(0x1AFFFFFF) else Color(0x1A000000))

                    SettingNavigationRow(
                        icon = Icons.Outlined.Wallpaper,
                        title = "Wallpaper",
                        isDark = isDark,
                        onClick = { onNavigate(AppScreen.WallpaperSettings) },
                        testTag = "setting_wallpaper_nav"
                    )

                    HorizontalDivider(color = if (isDark) Color(0x1AFFFFFF) else Color(0x1A000000))

                    SettingNavigationRow(
                        icon = Icons.Outlined.BlurOn,
                        title = "Blur & Neon",
                        isDark = isDark,
                        onClick = { onNavigate(AppScreen.BlurNeonSettings) },
                        testTag = "setting_blur_neon_nav"
                    )

                    HorizontalDivider(color = if (isDark) Color(0x1AFFFFFF) else Color(0x1A000000))

                    SettingToggleRow(
                        icon = Icons.Outlined.BlurOn,
                        title = "Blur Background",
                        checked = settings.backgroundBlur,
                        isDark = isDark,
                        onCheckedChange = { checked ->
                            onUpdateSettings { it.copy(backgroundBlur = checked) }
                        },
                        testTag = "setting_blur_switch"
                    )

                    HorizontalDivider(color = if (isDark) Color(0x1AFFFFFF) else Color(0x1A000000))

                    SettingToggleRow(
                        icon = Icons.Outlined.Lightbulb,
                        title = "Neon Lights",
                        checked = settings.neonLights,
                        isDark = isDark,
                        onCheckedChange = { checked ->
                            onUpdateSettings { it.copy(neonLights = checked) }
                        },
                        testTag = "setting_neon_switch"
                    )
                }
            }

            // Reset All Customization Button
            GlassPanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showResetDialog = true }
                    .testTag("setting_reset_button"),
                settings = settings,
                isDark = isDark,
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.RestartAlt,
                        contentDescription = null,
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Reset All Customization",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFEF4444)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ThemeCard(
    title: String,
    theme: ThemeMode,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)
    val borderBrush = if (isSelected) {
        Brush.linearGradient(listOf(Color(0xFF00E5FF), Color(0xFF2563EB)))
    } else {
        Brush.linearGradient(listOf(Color(0x33FFFFFF), Color(0x11FFFFFF)))
    }

    Column(
        modifier = modifier
            .clip(shape)
            .clickable(onClick = onClick)
            .border(if (isSelected) 2.dp else 1.dp, borderBrush, shape)
            .background(
                when (theme) {
                    ThemeMode.LIGHT -> Color(0xFFD6E3F2)
                    ThemeMode.DARK -> Color(0xFF0D1424)
                    ThemeMode.SYSTEM -> Color(0xFF1E293B)
                }
            )
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Mini simulated screen card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    when (theme) {
                        ThemeMode.LIGHT -> Color(0xFFF1F5F9)
                        ThemeMode.DARK -> Color(0xFF070B14)
                        ThemeMode.SYSTEM -> Color(0xFF0F172A)
                    }
                )
                .padding(6.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Mini display
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (theme == ThemeMode.LIGHT) Color(0x33000000) else Color(0x33FFFFFF)
                        )
                )
                // Mini buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(12.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    if (it == 2) Color(0xFF2563EB)
                                    else if (theme == ThemeMode.LIGHT) Color(0x44000000)
                                    else Color(0x44FFFFFF)
                                )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color(0xFF38BDF8) else Color.White
            )
            if (isSelected) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = Color(0xFF00E5FF),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
fun SettingToggleRow(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    isDark: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    testTag: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = if (isDark) Color.White else Color(0xFF0F172A)
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.testTag(testTag),
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF2563EB),
                uncheckedThumbColor = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
                uncheckedTrackColor = if (isDark) Color(0xFF1E293B) else Color(0xFFCBD5E1)
            )
        )
    }
}

@Composable
fun SettingNavigationRow(
    icon: ImageVector,
    title: String,
    isDark: Boolean,
    onClick: () -> Unit,
    testTag: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp)
            .testTag(testTag),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B),
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = if (isDark) Color.White else Color(0xFF0F172A)
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Navigate",
            tint = if (isDark) Color(0xFF64748B) else Color(0xFF94A3B8),
            modifier = Modifier.size(20.dp)
        )
    }
}
