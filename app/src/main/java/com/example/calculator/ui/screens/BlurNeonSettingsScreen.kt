package com.example.calculator.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.model.UserSettings
import com.example.calculator.ui.components.GlassPanel
import kotlin.math.roundToInt

@Composable
fun BlurNeonSettingsScreen(
    settings: UserSettings,
    isDark: Boolean,
    onBack: () -> Unit,
    onUpdateSettings: ((UserSettings) -> UserSettings) -> Unit,
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
                modifier = Modifier.testTag("blur_neon_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = if (isDark) Color.White else Color(0xFF0F172A)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Blur & Neon",
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
            // Background Blur Section
            GlassPanel(
                modifier = Modifier.fillMaxWidth(),
                settings = settings,
                isDark = isDark,
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Background Blur",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isDark) Color.White else Color(0xFF0F172A)
                        )
                        Switch(
                            checked = settings.backgroundBlur,
                            onCheckedChange = { chk -> onUpdateSettings { it.copy(backgroundBlur = chk) } },
                            modifier = Modifier.testTag("switch_blur_bg"),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF2563EB)
                            )
                        )
                    }

                    val blurPercent = (settings.blurIntensity * 100).roundToInt()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Blur Intensity",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
                        )
                        Text(
                            text = "$blurPercent%",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF38BDF8)
                        )
                    }

                    Slider(
                        value = settings.blurIntensity,
                        onValueChange = { v -> onUpdateSettings { it.copy(blurIntensity = v) } },
                        valueRange = 0f..1f,
                        enabled = settings.backgroundBlur,
                        modifier = Modifier.fillMaxWidth().testTag("slider_blur_intensity"),
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF00E5FF),
                            activeTrackColor = Color(0xFF2563EB)
                        )
                    )

                    // Live blur preview box
                    val blurPreviewDp = if (settings.backgroundBlur) (settings.blurIntensity * 24f).dp else 0.dp
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFFF97316).copy(alpha = 0.5f),
                                        Color(0xFF3B82F6).copy(alpha = 0.5f),
                                        Color(0xFF06B6D4).copy(alpha = 0.6f)
                                    )
                                )
                            )
                            .then(if (blurPreviewDp > 0.dp) Modifier.blur(blurPreviewDp) else Modifier)
                    )
                }
            }

            // Neon Lights Section
            GlassPanel(
                modifier = Modifier.fillMaxWidth(),
                settings = settings,
                isDark = isDark,
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Neon Lights",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isDark) Color.White else Color(0xFF0F172A)
                        )
                        Switch(
                            checked = settings.neonLights,
                            onCheckedChange = { chk -> onUpdateSettings { it.copy(neonLights = chk) } },
                            modifier = Modifier.testTag("switch_neon_lights"),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF00E5FF)
                            )
                        )
                    }

                    val neonPercent = (settings.neonIntensity * 100).roundToInt()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Neon Intensity",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)
                        )
                        Text(
                            text = "$neonPercent%",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00E5FF)
                        )
                    }

                    Slider(
                        value = settings.neonIntensity,
                        onValueChange = { v -> onUpdateSettings { it.copy(neonIntensity = v) } },
                        valueRange = 0f..1f,
                        enabled = settings.neonLights,
                        modifier = Modifier.fillMaxWidth().testTag("slider_neon_intensity"),
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF00E5FF),
                            activeTrackColor = Color(0xFF00E5FF)
                        )
                    )

                    // Live neon glow preview box
                    val neonActive = settings.neonLights
                    val glowAlpha = if (neonActive) (settings.neonIntensity * 0.9f).coerceIn(0.2f, 1f) else 0f
                    val glowElevation = if (neonActive) (settings.neonIntensity * 20f).dp else 0.dp

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .shadow(
                                elevation = glowElevation,
                                shape = RoundedCornerShape(14.dp),
                                ambientColor = Color(0xFF00E5FF).copy(alpha = glowAlpha),
                                spotColor = Color(0xFF2979FF).copy(alpha = glowAlpha)
                            )
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color(0xFF0D172A),
                                        Color(0xFF080D1A)
                                    )
                                )
                            )
                            .then(
                                if (neonActive) {
                                    Modifier.border(
                                        width = 2.dp,
                                        brush = Brush.linearGradient(
                                            listOf(
                                                Color(0xFF00E5FF).copy(alpha = glowAlpha),
                                                Color(0xFF2979FF).copy(alpha = glowAlpha * 0.8f),
                                                Color(0xFF00E5FF).copy(alpha = glowAlpha)
                                            )
                                        ),
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                } else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (neonActive) "✨ Liquid Neon Glow Active" else "Neon Disabled",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (neonActive) Color(0xFF00E5FF).copy(alpha = glowAlpha) else Color(0xFF64748B)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
