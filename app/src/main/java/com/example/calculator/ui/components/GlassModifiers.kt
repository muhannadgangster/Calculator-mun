package com.example.calculator.ui.components

import android.view.HapticFeedbackConstants
import android.view.SoundEffectConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.model.ThemeMode
import com.example.calculator.model.UserSettings

enum class ButtonType {
    NUMBER,
    FUNCTION,
    OPERATOR,
    ACCENT,
    SCIENTIFIC
}

fun calculateCornerRadiusDp(factor: Float): Dp {
    // 0f -> 12dp, 1f -> 36dp
    return (12f + factor * 24f).dp
}

@Composable
fun GlassPanel(
    modifier: Modifier = Modifier,
    settings: UserSettings,
    isDark: Boolean,
    shape: Shape = RoundedCornerShape(calculateCornerRadiusDp(settings.cornerRadius)),
    content: @Composable BoxScope.() -> Unit
) {
    val opacity = (settings.glassOpacity * (1f - settings.glassTransparency * 0.5f)).coerceIn(0.1f, 0.95f)
    val brightness = settings.glassBrightness

    val baseColor = if (isDark) {
        val r = (16 * brightness).toInt().coerceIn(8, 60)
        val g = (24 * brightness).toInt().coerceIn(12, 75)
        val b = (42 * brightness).toInt().coerceIn(24, 110)
        Color(r, g, b).copy(alpha = opacity)
    } else {
        Color.White.copy(alpha = (opacity * 1.1f).coerceIn(0.2f, 0.95f))
    }

    val reflectionAlpha = (settings.reflectionIntensity * 0.45f).coerceIn(0.05f, 0.8f)
    val borderAlpha = if (isDark) {
        (settings.reflectionIntensity * 0.6f + 0.15f).coerceIn(0.1f, 0.9f)
    } else {
        0.75f
    }

    val neonActive = settings.neonLights
    val neonAlpha = (settings.neonIntensity * 0.7f).coerceIn(0.1f, 1f)

    val shadowElevation = (settings.depthShadow * 16f).dp

    Box(
        modifier = modifier
            .shadow(
                elevation = shadowElevation,
                shape = shape,
                ambientColor = if (neonActive) Color(0xFF00E5FF).copy(alpha = neonAlpha * 0.4f) else Color.Black.copy(alpha = 0.4f),
                spotColor = if (neonActive) Color(0xFF2979FF).copy(alpha = neonAlpha * 0.5f) else Color.Black.copy(alpha = 0.5f)
            )
            .clip(shape)
            .background(baseColor)
            .drawBehind {
                // Top specular liquid reflection gradient
                val specularBrush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = reflectionAlpha),
                        Color.White.copy(alpha = reflectionAlpha * 0.3f),
                        Color.Transparent
                    ),
                    startY = 0f,
                    endY = size.height * 0.45f
                )
                drawRect(brush = specularBrush)

                // Optional subtle neon inner rim glow
                if (neonActive) {
                    val neonBrush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF00E5FF).copy(alpha = neonAlpha * 0.18f),
                            Color.Transparent
                        ),
                        center = Offset(size.width * 0.5f, 0f),
                        radius = size.width * 0.8f
                    )
                    drawRect(brush = neonBrush)
                }
            }
            .border(
                width = if (neonActive) 1.5.dp else 1.dp,
                brush = if (neonActive) {
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF00E5FF).copy(alpha = neonAlpha),
                            Color(0xFF2979FF).copy(alpha = neonAlpha * 0.6f),
                            Color.White.copy(alpha = borderAlpha * 0.5f),
                            Color(0xFF00E5FF).copy(alpha = neonAlpha * 0.8f)
                        )
                    )
                } else {
                    Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = borderAlpha),
                            Color.White.copy(alpha = borderAlpha * 0.2f),
                            if (isDark) Color(0xFF38BDF8).copy(alpha = 0.25f) else Color.White.copy(alpha = 0.4f),
                            Color.White.copy(alpha = borderAlpha * 0.5f)
                        )
                    )
                },
                shape = shape
            ),
        content = content
    )
}

@Composable
fun GlassCalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.NUMBER,
    settings: UserSettings,
    isDark: Boolean,
    testTag: String = "btn_$text",
    fontSize: TextUnit = 24.sp,
    subText: String? = null
) {
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Smooth physical spring scale animation
    val animScale by animateFloatAsState(
        targetValue = if (isPressed) {
            1f - (0.08f * settings.animationIntensity).coerceIn(0.02f, 0.12f)
        } else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 600f),
        label = "btn_scale"
    )

    // Button corner radius
    val buttonCornerRadius = calculateCornerRadiusDp(settings.cornerRadius)
    val shape = RoundedCornerShape(buttonCornerRadius)

    val neonActive = settings.neonLights
    val neonIntensity = settings.neonIntensity

    // Colors according to button type and theme
    val (bgBrush, textColor) = remember(type, isDark, settings, isPressed) {
        val btnAlpha = (settings.buttonTransparency * 0.7f + 0.15f).coerceIn(0.2f, 0.95f)
        val pressedAlphaBoost = if (isPressed) 0.15f else 0f

        when (type) {
            ButtonType.ACCENT -> {
                // Vibrant electric neon blue gradient
                val start = if (isPressed) Color(0xFF3B82F6) else Color(0xFF2563EB)
                val end = if (isPressed) Color(0xFF38BDF8) else Color(0xFF00E5FF)
                Pair(
                    Brush.linearGradient(listOf(start, end)),
                    Color.White
                )
            }
            ButtonType.OPERATOR -> {
                if (isDark) {
                    val c = Color(0xFF1E2D4A).copy(alpha = (btnAlpha + pressedAlphaBoost).coerceAtMost(0.95f))
                    Pair(Brush.linearGradient(listOf(c, c)), Color(0xFF60A5FA))
                } else {
                    val c = Color(0xFFDBEAFE).copy(alpha = (btnAlpha + pressedAlphaBoost).coerceAtMost(0.95f))
                    Pair(Brush.linearGradient(listOf(c, c)), Color(0xFF1D4ED8))
                }
            }
            ButtonType.FUNCTION -> {
                if (isDark) {
                    val c = Color(0xFF1A2438).copy(alpha = (btnAlpha + pressedAlphaBoost).coerceAtMost(0.95f))
                    Pair(Brush.linearGradient(listOf(c, c)), Color(0xFF93C5FD))
                } else {
                    val c = Color(0xFFE2E8F0).copy(alpha = (btnAlpha + pressedAlphaBoost).coerceAtMost(0.95f))
                    Pair(Brush.linearGradient(listOf(c, c)), Color(0xFF334155))
                }
            }
            ButtonType.SCIENTIFIC -> {
                if (isDark) {
                    val c = Color(0xFF131E33).copy(alpha = (btnAlpha * 0.9f + pressedAlphaBoost).coerceAtMost(0.95f))
                    Pair(Brush.linearGradient(listOf(c, c)), Color(0xFFCBD5E1))
                } else {
                    val c = Color(0xFFF1F5F9).copy(alpha = (btnAlpha * 0.9f + pressedAlphaBoost).coerceAtMost(0.95f))
                    Pair(Brush.linearGradient(listOf(c, c)), Color(0xFF334155))
                }
            }
            ButtonType.NUMBER -> {
                if (isDark) {
                    val c = Color(0xFF131B2D).copy(alpha = (btnAlpha + pressedAlphaBoost).coerceAtMost(0.95f))
                    Pair(Brush.linearGradient(listOf(c, c)), Color.White)
                } else {
                    val c = Color(0xFFFFFFFF).copy(alpha = (btnAlpha * 1.1f + pressedAlphaBoost).coerceIn(0.4f, 0.98f))
                    Pair(Brush.linearGradient(listOf(c, c)), Color(0xFF0F172A))
                }
            }
        }
    }

    val borderBrush = remember(type, isDark, neonActive, neonIntensity, isPressed) {
        if (type == ButtonType.ACCENT || (neonActive && (type == ButtonType.OPERATOR || isPressed))) {
            val alpha = (neonIntensity * 0.8f).coerceIn(0.3f, 1f)
            Brush.linearGradient(
                listOf(
                    Color(0xFF00E5FF).copy(alpha = alpha),
                    Color(0xFF3B82F6).copy(alpha = alpha * 0.8f),
                    Color.White.copy(alpha = alpha * 0.6f)
                )
            )
        } else {
            val borderAlpha = if (isDark) 0.35f else 0.7f
            Brush.linearGradient(
                listOf(
                    Color.White.copy(alpha = borderAlpha),
                    Color.White.copy(alpha = borderAlpha * 0.15f),
                    Color.White.copy(alpha = borderAlpha * 0.4f)
                )
            )
        }
    }

    Box(
        modifier = modifier
            .scale(animScale)
            .shadow(
                elevation = if (type == ButtonType.ACCENT) (4f + settings.depthShadow * 10f).dp else (settings.depthShadow * 6f).dp,
                shape = shape,
                ambientColor = if (type == ButtonType.ACCENT) Color(0xFF00E5FF).copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.3f),
                spotColor = if (type == ButtonType.ACCENT) Color(0xFF2979FF).copy(alpha = 0.6f) else Color.Black.copy(alpha = 0.3f)
            )
            .clip(shape)
            .background(bgBrush)
            .drawBehind {
                // Specular highlight at the top half
                val highlightAlpha = (settings.reflectionIntensity * 0.35f).coerceIn(0.05f, 0.6f)
                val specular = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = if (type == ButtonType.ACCENT) 0.4f else highlightAlpha),
                        Color.Transparent
                    ),
                    startY = 0f,
                    endY = size.height * 0.5f
                )
                drawRect(brush = specular)
            }
            .border(
                width = if (type == ButtonType.ACCENT || (neonActive && isPressed)) 1.5.dp else 1.dp,
                brush = borderBrush,
                shape = shape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    if (settings.hapticFeedback) {
                        try {
                            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                        } catch (_: Exception) { }
                    }
                    if (settings.soundEffects) {
                        try {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                        } catch (_: Exception) { }
                    }
                    onClick()
                }
            )
            .testTag(testTag),
        contentAlignment = Alignment.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = if (type == ButtonType.ACCENT || type == ButtonType.NUMBER) FontWeight.Bold else FontWeight.SemiBold,
                color = textColor,
                textAlign = TextAlign.Center
            )
            if (subText != null) {
                Text(
                    text = subText,
                    fontSize = 10.sp,
                    color = textColor.copy(alpha = 0.65f),
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}
