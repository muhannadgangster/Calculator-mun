package com.example.calculator.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.model.UserSettings
import com.example.calculator.ui.components.ButtonType
import com.example.calculator.ui.components.GlassCalculatorButton
import com.example.calculator.ui.components.GlassPanel
import kotlin.math.roundToInt

@Composable
fun GlassSettingsScreen(
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
                modifier = Modifier.testTag("glass_settings_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = if (isDark) Color.White else Color(0xFF0F172A)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Glass Customization",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDark) Color.White else Color(0xFF0F172A)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Live Mini Preview Box as seen in reference image Screen 6
            GlassPanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                settings = settings,
                isDark = isDark,
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Mini display
                    GlassPanel(
                        modifier = Modifier.fillMaxWidth(),
                        settings = settings,
                        isDark = isDark,
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = "0",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDark) Color.White else Color(0xFF0F172A),
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Row 1 buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GlassCalculatorButton("AC", {}, Modifier.weight(1f).height(42.dp), ButtonType.FUNCTION, settings, isDark, fontSize = 14.sp)
                        GlassCalculatorButton("⌫", {}, Modifier.weight(1f).height(42.dp), ButtonType.FUNCTION, settings, isDark, fontSize = 14.sp)
                        GlassCalculatorButton("%", {}, Modifier.weight(1f).height(42.dp), ButtonType.FUNCTION, settings, isDark, fontSize = 14.sp)
                        GlassCalculatorButton("÷", {}, Modifier.weight(1f).height(42.dp), ButtonType.OPERATOR, settings, isDark, fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Row 2 buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GlassCalculatorButton("7", {}, Modifier.weight(1f).height(42.dp), ButtonType.NUMBER, settings, isDark, fontSize = 16.sp)
                        GlassCalculatorButton("8", {}, Modifier.weight(1f).height(42.dp), ButtonType.NUMBER, settings, isDark, fontSize = 16.sp)
                        GlassCalculatorButton("9", {}, Modifier.weight(1f).height(42.dp), ButtonType.NUMBER, settings, isDark, fontSize = 16.sp)
                        GlassCalculatorButton("×", {}, Modifier.weight(1f).height(42.dp), ButtonType.OPERATOR, settings, isDark, fontSize = 18.sp)
                    }
                }
            }

            // 10 Glass Customization Sliders
            CustomSliderCard(
                title = "Glass Transparency",
                value = settings.glassTransparency,
                onValueChange = { v -> onUpdateSettings { it.copy(glassTransparency = v) } },
                isDark = isDark,
                testTag = "slider_glass_transparency"
            )

            CustomSliderCard(
                title = "Glass Opacity",
                value = settings.glassOpacity,
                onValueChange = { v -> onUpdateSettings { it.copy(glassOpacity = v) } },
                isDark = isDark,
                testTag = "slider_glass_opacity"
            )

            CustomSliderCard(
                title = "Blur Intensity",
                value = settings.glassBlurIntensity,
                onValueChange = { v -> onUpdateSettings { it.copy(glassBlurIntensity = v) } },
                isDark = isDark,
                testTag = "slider_blur_intensity"
            )

            CustomSliderCard(
                title = "Glass Brightness",
                value = settings.glassBrightness,
                onValueChange = { v -> onUpdateSettings { it.copy(glassBrightness = v) } },
                isDark = isDark,
                testTag = "slider_glass_brightness"
            )

            CustomSliderCard(
                title = "Reflection Intensity",
                value = settings.reflectionIntensity,
                onValueChange = { v -> onUpdateSettings { it.copy(reflectionIntensity = v) } },
                isDark = isDark,
                testTag = "slider_reflection_intensity"
            )

            CustomSliderCard(
                title = "Corner Radius",
                value = settings.cornerRadius,
                onValueChange = { v -> onUpdateSettings { it.copy(cornerRadius = v) } },
                isDark = isDark,
                testTag = "slider_corner_radius"
            )

            CustomSliderCard(
                title = "Depth / Shadow",
                value = settings.depthShadow,
                onValueChange = { v -> onUpdateSettings { it.copy(depthShadow = v) } },
                isDark = isDark,
                testTag = "slider_depth_shadow"
            )

            CustomSliderCard(
                title = "Button Transparency",
                value = settings.buttonTransparency,
                onValueChange = { v -> onUpdateSettings { it.copy(buttonTransparency = v) } },
                isDark = isDark,
                testTag = "slider_btn_transparency"
            )

            CustomSliderCard(
                title = "Button Brightness",
                value = settings.buttonBrightness,
                onValueChange = { v -> onUpdateSettings { it.copy(buttonBrightness = v) } },
                isDark = isDark,
                testTag = "slider_btn_brightness"
            )

            CustomSliderCard(
                title = "Animation Intensity",
                value = settings.animationIntensity,
                onValueChange = { v -> onUpdateSettings { it.copy(animationIntensity = v) } },
                isDark = isDark,
                testTag = "slider_anim_intensity"
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CustomSliderCard(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    isDark: Boolean,
    testTag: String = ""
) {
    val percent = (value * 100).roundToInt()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isDark) Color(0xFFE2E8F0) else Color(0xFF1E293B)
            )
            Text(
                text = "$percent%",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDark) Color(0xFF38BDF8) else Color(0xFF0284C7)
            )
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1f,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF00E5FF),
                activeTrackColor = Color(0xFF2563EB),
                inactiveTrackColor = if (isDark) Color(0x33FFFFFF) else Color(0x33000000)
            )
        )
    }
}
