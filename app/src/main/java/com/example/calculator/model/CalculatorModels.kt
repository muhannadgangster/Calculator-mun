package com.example.calculator.model

enum class CalculatorMode {
    NORMAL,
    SCIENTIFIC
}

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}

data class HistoryItem(
    val id: Long = System.currentTimeMillis(),
    val expression: String,
    val result: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class UserSettings(
    val themeMode: ThemeMode = ThemeMode.DARK,
    val defaultCalculatorMode: CalculatorMode = CalculatorMode.NORMAL,
    val hapticFeedback: Boolean = true,
    val soundEffects: Boolean = true,
    val backgroundBlur: Boolean = true,
    val blurIntensity: Float = 0.50f,
    val neonLights: Boolean = true,
    val neonIntensity: Float = 0.50f,

    // Glass customization
    val glassTransparency: Float = 0.70f,
    val glassOpacity: Float = 0.60f,
    val glassBlurIntensity: Float = 0.50f,
    val glassBrightness: Float = 0.60f,
    val reflectionIntensity: Float = 0.40f,
    val cornerRadius: Float = 0.25f, // mapped to dp
    val depthShadow: Float = 0.40f,
    val buttonTransparency: Float = 0.70f,
    val buttonBrightness: Float = 0.60f,
    val animationIntensity: Float = 0.70f,

    // Wallpaper
    val customWallpaperUri: String? = null,
    val wallpaperOpacity: Float = 0.70f,
    val wallpaperBlur: Float = 0.60f,
    val wallpaperBrightness: Float = 0.50f,
    val darkOverlay: Boolean = true
)

sealed class AppScreen {
    object Calculator : AppScreen()
    object History : AppScreen()
    object Settings : AppScreen()
    object ThemeSettings : AppScreen()
    object GlassSettings : AppScreen()
    object WallpaperSettings : AppScreen()
    object BlurNeonSettings : AppScreen()
}
