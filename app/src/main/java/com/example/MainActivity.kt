package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.model.AppScreen
import com.example.calculator.model.ThemeMode
import com.example.calculator.ui.CalculatorViewModel
import com.example.calculator.ui.components.CalculatorBackground
import com.example.calculator.ui.screens.*
import com.example.ui.theme.MunCalculatorTheme

class MainActivity : ComponentActivity() {

    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settings by viewModel.settings.collectAsStateWithLifecycle()
            val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
            val calculatorMode by viewModel.calculatorMode.collectAsStateWithLifecycle()
            val angleMode by viewModel.angleMode.collectAsStateWithLifecycle()
            val expression by viewModel.expression.collectAsStateWithLifecycle()
            val result by viewModel.result.collectAsStateWithLifecycle()
            val isError by viewModel.isError.collectAsStateWithLifecycle()
            val history by viewModel.history.collectAsStateWithLifecycle()

            val isDark = when (settings.themeMode) {
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            MunCalculatorTheme(themeMode = settings.themeMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    CalculatorBackground(
                        settings = settings,
                        isDark = isDark
                    ) {
                        AnimatedContent(
                            targetState = currentScreen,
                            transitionSpec = {
                                fadeIn() togetherWith fadeOut()
                            },
                            label = "screen_transition"
                        ) { screen ->
                            when (screen) {
                                is AppScreen.Calculator -> {
                                    CalculatorScreen(
                                        expression = expression,
                                        result = result,
                                        isError = isError,
                                        angleMode = angleMode,
                                        calculatorMode = calculatorMode,
                                        settings = settings,
                                        isDark = isDark,
                                        onDigitClick = viewModel::onDigit,
                                        onOperatorClick = viewModel::onOperator,
                                        onFunctionClick = viewModel::onFunction,
                                        onConstantClick = viewModel::onConstant,
                                        onClearClick = viewModel::onClear,
                                        onBackspaceClick = viewModel::onBackspace,
                                        onEqualsClick = viewModel::onEquals,
                                        onPlusMinusClick = viewModel::onPlusMinus,
                                        onPercentClick = viewModel::onPercent,
                                        onAngleModeToggle = viewModel::toggleAngleMode,
                                        onModeChanged = viewModel::setCalculatorMode,
                                        onHistoryClick = { viewModel.navigateTo(AppScreen.History) },
                                        onSettingsClick = { viewModel.navigateTo(AppScreen.Settings) }
                                    )
                                }
                                is AppScreen.History -> {
                                    HistoryScreen(
                                        history = history,
                                        settings = settings,
                                        isDark = isDark,
                                        onBack = { viewModel.navigateBack() },
                                        onReuse = { item -> viewModel.reuseHistoryItem(item) },
                                        onDelete = { id -> viewModel.deleteHistoryItem(id) },
                                        onClearAll = { viewModel.clearHistory() }
                                    )
                                }
                                is AppScreen.Settings -> {
                                    SettingsScreen(
                                        settings = settings,
                                        isDark = isDark,
                                        onBack = { viewModel.navigateBack() },
                                        onNavigate = { target -> viewModel.navigateTo(target) },
                                        onUpdateSettings = { transform -> viewModel.updateSettings(transform) },
                                        onResetSettings = { viewModel.resetSettings() }
                                    )
                                }
                                is AppScreen.ThemeSettings -> {
                                    ThemeSettingsScreen(
                                        settings = settings,
                                        isDark = isDark,
                                        onBack = { viewModel.navigateBack() },
                                        onThemeSelect = { theme ->
                                            viewModel.updateSettings { it.copy(themeMode = theme) }
                                        }
                                    )
                                }
                                is AppScreen.GlassSettings -> {
                                    GlassSettingsScreen(
                                        settings = settings,
                                        isDark = isDark,
                                        onBack = { viewModel.navigateBack() },
                                        onUpdateSettings = { transform -> viewModel.updateSettings(transform) }
                                    )
                                }
                                is AppScreen.WallpaperSettings -> {
                                    WallpaperSettingsScreen(
                                        settings = settings,
                                        isDark = isDark,
                                        onBack = { viewModel.navigateBack() },
                                        onUpdateSettings = { transform -> viewModel.updateSettings(transform) }
                                    )
                                }
                                is AppScreen.BlurNeonSettings -> {
                                    BlurNeonSettingsScreen(
                                        settings = settings,
                                        isDark = isDark,
                                        onBack = { viewModel.navigateBack() },
                                        onUpdateSettings = { transform -> viewModel.updateSettings(transform) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
