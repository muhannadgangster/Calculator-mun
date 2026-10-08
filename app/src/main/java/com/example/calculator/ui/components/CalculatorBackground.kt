package com.example.calculator.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.R
import com.example.calculator.model.UserSettings

@Composable
fun CalculatorBackground(
    settings: UserSettings,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Base solid/gradient background
        val baseGradient = if (isDark) {
            Brush.verticalGradient(
                listOf(
                    Color(0xFF070B14),
                    Color(0xFF0B1324),
                    Color(0xFF060911)
                )
            )
        } else {
            Brush.verticalGradient(
                listOf(
                    Color(0xFFE8EEF5),
                    Color(0xFFD6E3F2),
                    Color(0xFFE2EAF4)
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(baseGradient)
        )

        // Wallpaper layer (custom URI or default scenic lake wallpaper)
        val hasCustom = !settings.customWallpaperUri.isNullOrEmpty()
        // If custom URI or if blur/wallpaper is enabled, show the wallpaper
        val showWallpaper = hasCustom || settings.wallpaperOpacity > 0f

        if (showWallpaper) {
            val blurRadius = if (settings.backgroundBlur) {
                (settings.wallpaperBlur * 28f).dp
            } else 0.dp

            val brightnessFactor = (settings.wallpaperBrightness * 1.5f).coerceIn(0.2f, 2.0f)
            // Color matrix for brightness adjustment
            val colorMatrix = ColorMatrix(
                floatArrayOf(
                    brightnessFactor, 0f, 0f, 0f, 0f,
                    0f, brightnessFactor, 0f, 0f, 0f,
                    0f, 0f, brightnessFactor, 0f, 0f,
                    0f, 0f, 0f, settings.wallpaperOpacity, 0f
                )
            )

            val wallpaperModifier = Modifier
                .fillMaxSize()
                .then(if (blurRadius > 0.dp) Modifier.blur(blurRadius) else Modifier)

            if (hasCustom) {
                AsyncImage(
                    model = Uri.parse(settings.customWallpaperUri),
                    contentDescription = "Custom Wallpaper",
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.colorMatrix(colorMatrix),
                    modifier = wallpaperModifier
                )
            } else {
                // Default scenic alpine lake wallpaper generated for MUN Calculator
                Image(
                    painter = painterResource(id = R.drawable.img_default_wallpaper),
                    contentDescription = "Default Alpine Wallpaper",
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.colorMatrix(colorMatrix),
                    modifier = wallpaperModifier
                )
            }
        }

        // Dark overlay if enabled
        if (settings.darkOverlay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = if (isDark) 0.35f else 0.15f))
            )
        }

        // Content (calculator UI) on top
        content()
    }
}
