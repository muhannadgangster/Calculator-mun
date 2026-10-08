package com.example.calculator.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.calculator.model.UserSettings
import com.example.calculator.ui.components.GlassPanel
import kotlin.math.roundToInt

@Composable
fun WallpaperSettingsScreen(
    settings: UserSettings,
    isDark: Boolean,
    onBack: () -> Unit,
    onUpdateSettings: ((UserSettings) -> UserSettings) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }
    val context = LocalContext.current

    // Android Photo Picker launcher (zero-permission)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                onUpdateSettings { it.copy(customWallpaperUri = uri.toString()) }
                Toast.makeText(context, "Wallpaper updated!", Toast.LENGTH_SHORT).show()
            }
        }
    )

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
                modifier = Modifier.testTag("wallpaper_settings_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = if (isDark) Color.White else Color(0xFF0F172A)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Wallpaper",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Live Wallpaper Preview Window
            val previewShape = RoundedCornerShape(20.dp)
            val brightnessFactor = (settings.wallpaperBrightness * 1.5f).coerceIn(0.2f, 2.0f)
            val colorMatrix = ColorMatrix(
                floatArrayOf(
                    brightnessFactor, 0f, 0f, 0f, 0f,
                    0f, brightnessFactor, 0f, 0f, 0f,
                    0f, 0f, brightnessFactor, 0f, 0f,
                    0f, 0f, 0f, settings.wallpaperOpacity, 0f
                )
            )
            val blurDp = (settings.wallpaperBlur * 20f).dp

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(previewShape)
                    .border(
                        1.5.dp,
                        Brush.linearGradient(listOf(Color(0xFF00E5FF).copy(alpha = 0.5f), Color.White.copy(alpha = 0.2f))),
                        previewShape
                    )
            ) {
                // Wallpaper image
                val imgModifier = Modifier
                    .fillMaxSize()
                    .then(if (blurDp > 0.dp) Modifier.blur(blurDp) else Modifier)

                if (!settings.customWallpaperUri.isNullOrEmpty()) {
                    AsyncImage(
                        model = Uri.parse(settings.customWallpaperUri),
                        contentDescription = "Wallpaper Preview",
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.colorMatrix(colorMatrix),
                        modifier = imgModifier
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.img_default_wallpaper),
                        contentDescription = "Default Wallpaper Preview",
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.colorMatrix(colorMatrix),
                        modifier = imgModifier
                    )
                }

                if (settings.darkOverlay) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.35f))
                    )
                }

                // Sample glass overlay inside preview
                GlassPanel(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth(),
                    settings = settings,
                    isDark = isDark,
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "Liquid Glass on Wallpaper",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }
            }

            // Choose from Gallery Button
            GlassPanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                    .testTag("wallpaper_choose_gallery_button"),
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
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = "Choose from Gallery",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isDark) Color.White else Color(0xFF0F172A)
                    )
                }
            }

            // Reset Wallpaper Button
            GlassPanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        onUpdateSettings { it.copy(customWallpaperUri = null) }
                        Toast.makeText(context, "Wallpaper reset to default", Toast.LENGTH_SHORT).show()
                    }
                    .testTag("wallpaper_reset_button"),
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
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = "Reset Wallpaper",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isDark) Color(0xFFE2E8F0) else Color(0xFF334155)
                    )
                }
            }

            // Sliders Card
            GlassPanel(
                modifier = Modifier.fillMaxWidth(),
                settings = settings,
                isDark = isDark,
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    WallpaperSlider(
                        title = "Wallpaper Opacity",
                        value = settings.wallpaperOpacity,
                        onValueChange = { v -> onUpdateSettings { it.copy(wallpaperOpacity = v) } },
                        isDark = isDark,
                        testTag = "slider_wallpaper_opacity"
                    )

                    WallpaperSlider(
                        title = "Blur",
                        value = settings.wallpaperBlur,
                        onValueChange = { v -> onUpdateSettings { it.copy(wallpaperBlur = v) } },
                        isDark = isDark,
                        testTag = "slider_wallpaper_blur"
                    )

                    WallpaperSlider(
                        title = "Brightness",
                        value = settings.wallpaperBrightness,
                        onValueChange = { v -> onUpdateSettings { it.copy(wallpaperBrightness = v) } },
                        isDark = isDark,
                        testTag = "slider_wallpaper_brightness"
                    )

                    HorizontalDivider(color = if (isDark) Color(0x1AFFFFFF) else Color(0x1A000000))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dark Overlay",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isDark) Color.White else Color(0xFF0F172A)
                        )
                        Switch(
                            checked = settings.darkOverlay,
                            onCheckedChange = { chk -> onUpdateSettings { it.copy(darkOverlay = chk) } },
                            modifier = Modifier.testTag("switch_dark_overlay"),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF2563EB)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun WallpaperSlider(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    isDark: Boolean,
    testTag: String
) {
    val percent = (value * 100).roundToInt()
    Column(modifier = Modifier.fillMaxWidth()) {
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
