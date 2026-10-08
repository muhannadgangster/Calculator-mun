package com.example.calculator.data

import android.content.Context
import android.content.SharedPreferences
import com.example.calculator.model.CalculatorMode
import com.example.calculator.model.HistoryItem
import com.example.calculator.model.ThemeMode
import com.example.calculator.model.UserSettings
import org.json.JSONArray
import org.json.JSONObject

class CalculatorRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("mun_calculator_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_THEME = "theme_mode"
        private const val KEY_CALC_MODE = "calc_mode"
        private const val KEY_HAPTIC = "haptic_enabled"
        private const val KEY_SOUND = "sound_enabled"
        private const val KEY_BG_BLUR = "bg_blur_enabled"
        private const val KEY_BLUR_INTENSITY = "blur_intensity"
        private const val KEY_NEON = "neon_enabled"
        private const val KEY_NEON_INTENSITY = "neon_intensity"
        private const val KEY_GLASS_TRANSPARENCY = "glass_transparency"
        private const val KEY_GLASS_OPACITY = "glass_opacity"
        private const val KEY_GLASS_BLUR = "glass_blur"
        private const val KEY_GLASS_BRIGHTNESS = "glass_brightness"
        private const val KEY_REFLECTION_INTENSITY = "reflection_intensity"
        private const val KEY_CORNER_RADIUS = "corner_radius"
        private const val KEY_DEPTH_SHADOW = "depth_shadow"
        private const val KEY_BTN_TRANSPARENCY = "btn_transparency"
        private const val KEY_BTN_BRIGHTNESS = "btn_brightness"
        private const val KEY_ANIM_INTENSITY = "anim_intensity"
        private const val KEY_WALLPAPER_URI = "wallpaper_uri"
        private const val KEY_WALLPAPER_OPACITY = "wallpaper_opacity"
        private const val KEY_WALLPAPER_BLUR = "wallpaper_blur"
        private const val KEY_WALLPAPER_BRIGHTNESS = "wallpaper_brightness"
        private const val KEY_DARK_OVERLAY = "dark_overlay"
        private const val KEY_HISTORY = "calc_history_json"
    }

    fun loadSettings(): UserSettings {
        return UserSettings(
            themeMode = ThemeMode.valueOf(prefs.getString(KEY_THEME, ThemeMode.DARK.name) ?: ThemeMode.DARK.name),
            defaultCalculatorMode = CalculatorMode.valueOf(prefs.getString(KEY_CALC_MODE, CalculatorMode.NORMAL.name) ?: CalculatorMode.NORMAL.name),
            hapticFeedback = prefs.getBoolean(KEY_HAPTIC, true),
            soundEffects = prefs.getBoolean(KEY_SOUND, true),
            backgroundBlur = prefs.getBoolean(KEY_BG_BLUR, true),
            blurIntensity = prefs.getFloat(KEY_BLUR_INTENSITY, 0.50f),
            neonLights = prefs.getBoolean(KEY_NEON, true),
            neonIntensity = prefs.getFloat(KEY_NEON_INTENSITY, 0.50f),
            glassTransparency = prefs.getFloat(KEY_GLASS_TRANSPARENCY, 0.70f),
            glassOpacity = prefs.getFloat(KEY_GLASS_OPACITY, 0.60f),
            glassBlurIntensity = prefs.getFloat(KEY_GLASS_BLUR, 0.50f),
            glassBrightness = prefs.getFloat(KEY_GLASS_BRIGHTNESS, 0.60f),
            reflectionIntensity = prefs.getFloat(KEY_REFLECTION_INTENSITY, 0.40f),
            cornerRadius = prefs.getFloat(KEY_CORNER_RADIUS, 0.25f),
            depthShadow = prefs.getFloat(KEY_DEPTH_SHADOW, 0.40f),
            buttonTransparency = prefs.getFloat(KEY_BTN_TRANSPARENCY, 0.70f),
            buttonBrightness = prefs.getFloat(KEY_BTN_BRIGHTNESS, 0.60f),
            animationIntensity = prefs.getFloat(KEY_ANIM_INTENSITY, 0.70f),
            customWallpaperUri = prefs.getString(KEY_WALLPAPER_URI, null),
            wallpaperOpacity = prefs.getFloat(KEY_WALLPAPER_OPACITY, 0.70f),
            wallpaperBlur = prefs.getFloat(KEY_WALLPAPER_BLUR, 0.60f),
            wallpaperBrightness = prefs.getFloat(KEY_WALLPAPER_BRIGHTNESS, 0.50f),
            darkOverlay = prefs.getBoolean(KEY_DARK_OVERLAY, true)
        )
    }

    fun saveSettings(settings: UserSettings) {
        prefs.edit().apply {
            putString(KEY_THEME, settings.themeMode.name)
            putString(KEY_CALC_MODE, settings.defaultCalculatorMode.name)
            putBoolean(KEY_HAPTIC, settings.hapticFeedback)
            putBoolean(KEY_SOUND, settings.soundEffects)
            putBoolean(KEY_BG_BLUR, settings.backgroundBlur)
            putFloat(KEY_BLUR_INTENSITY, settings.blurIntensity)
            putBoolean(KEY_NEON, settings.neonLights)
            putFloat(KEY_NEON_INTENSITY, settings.neonIntensity)
            putFloat(KEY_GLASS_TRANSPARENCY, settings.glassTransparency)
            putFloat(KEY_GLASS_OPACITY, settings.glassOpacity)
            putFloat(KEY_GLASS_BLUR, settings.glassBlurIntensity)
            putFloat(KEY_GLASS_BRIGHTNESS, settings.glassBrightness)
            putFloat(KEY_REFLECTION_INTENSITY, settings.reflectionIntensity)
            putFloat(KEY_CORNER_RADIUS, settings.cornerRadius)
            putFloat(KEY_DEPTH_SHADOW, settings.depthShadow)
            putFloat(KEY_BTN_TRANSPARENCY, settings.buttonTransparency)
            putFloat(KEY_BTN_BRIGHTNESS, settings.buttonBrightness)
            putFloat(KEY_ANIM_INTENSITY, settings.animationIntensity)
            putString(KEY_WALLPAPER_URI, settings.customWallpaperUri)
            putFloat(KEY_WALLPAPER_OPACITY, settings.wallpaperOpacity)
            putFloat(KEY_WALLPAPER_BLUR, settings.wallpaperBlur)
            putFloat(KEY_WALLPAPER_BRIGHTNESS, settings.wallpaperBrightness)
            putBoolean(KEY_DARK_OVERLAY, settings.darkOverlay)
            apply()
        }
    }

    fun resetSettings(): UserSettings {
        prefs.edit().apply {
            remove(KEY_THEME)
            remove(KEY_CALC_MODE)
            remove(KEY_HAPTIC)
            remove(KEY_SOUND)
            remove(KEY_BG_BLUR)
            remove(KEY_BLUR_INTENSITY)
            remove(KEY_NEON)
            remove(KEY_NEON_INTENSITY)
            remove(KEY_GLASS_TRANSPARENCY)
            remove(KEY_GLASS_OPACITY)
            remove(KEY_GLASS_BLUR)
            remove(KEY_GLASS_BRIGHTNESS)
            remove(KEY_REFLECTION_INTENSITY)
            remove(KEY_CORNER_RADIUS)
            remove(KEY_DEPTH_SHADOW)
            remove(KEY_BTN_TRANSPARENCY)
            remove(KEY_BTN_BRIGHTNESS)
            remove(KEY_ANIM_INTENSITY)
            remove(KEY_WALLPAPER_URI)
            remove(KEY_WALLPAPER_OPACITY)
            remove(KEY_WALLPAPER_BLUR)
            remove(KEY_WALLPAPER_BRIGHTNESS)
            remove(KEY_DARK_OVERLAY)
            apply()
        }
        return UserSettings()
    }

    fun loadHistory(): List<HistoryItem> {
        val raw = prefs.getString(KEY_HISTORY, null) ?: return emptyList()
        val list = mutableListOf<HistoryItem>()
        try {
            val jsonArray = JSONArray(raw)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    HistoryItem(
                        id = obj.optLong("id", System.currentTimeMillis()),
                        expression = obj.optString("expression", ""),
                        result = obj.optString("result", ""),
                        timestamp = obj.optLong("timestamp", System.currentTimeMillis())
                    )
                )
            }
        } catch (_: Exception) { }
        return list
    }

    fun saveHistory(items: List<HistoryItem>) {
        val jsonArray = JSONArray()
        // Store last 100 entries
        items.take(100).forEach { item ->
            val obj = JSONObject().apply {
                put("id", item.id)
                put("expression", item.expression)
                put("result", item.result)
                put("timestamp", item.timestamp)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString(KEY_HISTORY, jsonArray.toString()).apply()
    }
}
