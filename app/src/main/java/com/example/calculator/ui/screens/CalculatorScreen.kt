package com.example.calculator.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.calculator.engine.CalculatorEngine
import com.example.calculator.model.CalculatorMode
import com.example.calculator.model.UserSettings
import com.example.calculator.ui.components.*

@Composable
fun CalculatorScreen(
    expression: String,
    result: String,
    isError: Boolean,
    angleMode: CalculatorEngine.AngleMode,
    calculatorMode: CalculatorMode,
    settings: UserSettings,
    isDark: Boolean,
    onDigitClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onFunctionClick: (String) -> Unit,
    onConstantClick: (String) -> Unit,
    onClearClick: () -> Unit,
    onBackspaceClick: () -> Unit,
    onEqualsClick: () -> Unit,
    onPlusMinusClick: () -> Unit,
    onPercentClick: () -> Unit,
    onAngleModeToggle: () -> Unit,
    onModeChanged: (CalculatorMode) -> Unit,
    onHistoryClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        CalculatorHeader(
            onHistoryClick = onHistoryClick,
            onSettingsClick = onSettingsClick,
            settings = settings,
            isDark = isDark
        )

        if (!isLandscape) {
            // Portrait Layout: Display on top, Mode switcher, Keypad below
            CalculatorDisplay(
                expression = expression,
                result = result,
                isError = isError,
                angleMode = angleMode,
                calculatorMode = calculatorMode,
                settings = settings,
                isDark = isDark
            )

            ModeSwitcher(
                currentMode = calculatorMode,
                onModeChanged = onModeChanged,
                settings = settings,
                isDark = isDark
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (calculatorMode == CalculatorMode.NORMAL) {
                    NormalKeypad(
                        onDigitClick = onDigitClick,
                        onOperatorClick = onOperatorClick,
                        onClearClick = onClearClick,
                        onBackspaceClick = onBackspaceClick,
                        onEqualsClick = onEqualsClick,
                        onPlusMinusClick = onPlusMinusClick,
                        onPercentClick = onPercentClick,
                        settings = settings,
                        isDark = isDark,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    ScientificKeypad(
                        onDigitClick = onDigitClick,
                        onOperatorClick = onOperatorClick,
                        onFunctionClick = onFunctionClick,
                        onConstantClick = onConstantClick,
                        onClearClick = onClearClick,
                        onBackspaceClick = onBackspaceClick,
                        onEqualsClick = onEqualsClick,
                        onPlusMinusClick = onPlusMinusClick,
                        onAngleModeToggle = onAngleModeToggle,
                        angleMode = angleMode,
                        settings = settings,
                        isDark = isDark,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        } else {
            // Landscape Layout: Display + Mode switcher on the left side, Keypad on the right side
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    CalculatorDisplay(
                        expression = expression,
                        result = result,
                        isError = isError,
                        angleMode = angleMode,
                        calculatorMode = calculatorMode,
                        settings = settings,
                        isDark = isDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ModeSwitcher(
                        currentMode = calculatorMode,
                        onModeChanged = onModeChanged,
                        settings = settings,
                        isDark = isDark
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1.4f)
                        .fillMaxHeight()
                ) {
                    if (calculatorMode == CalculatorMode.NORMAL) {
                        NormalKeypad(
                            onDigitClick = onDigitClick,
                            onOperatorClick = onOperatorClick,
                            onClearClick = onClearClick,
                            onBackspaceClick = onBackspaceClick,
                            onEqualsClick = onEqualsClick,
                            onPlusMinusClick = onPlusMinusClick,
                            onPercentClick = onPercentClick,
                            settings = settings,
                            isDark = isDark,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        ScientificKeypad(
                            onDigitClick = onDigitClick,
                            onOperatorClick = onOperatorClick,
                            onFunctionClick = onFunctionClick,
                            onConstantClick = onConstantClick,
                            onClearClick = onClearClick,
                            onBackspaceClick = onBackspaceClick,
                            onEqualsClick = onEqualsClick,
                            onPlusMinusClick = onPlusMinusClick,
                            onAngleModeToggle = onAngleModeToggle,
                            angleMode = angleMode,
                            settings = settings,
                            isDark = isDark,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
