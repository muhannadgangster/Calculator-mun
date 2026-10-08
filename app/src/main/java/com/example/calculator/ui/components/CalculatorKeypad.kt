package com.example.calculator.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.engine.CalculatorEngine
import com.example.calculator.model.UserSettings

@Composable
fun NormalKeypad(
    onDigitClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onClearClick: () -> Unit,
    onBackspaceClick: () -> Unit,
    onEqualsClick: () -> Unit,
    onPlusMinusClick: () -> Unit,
    onPercentClick: () -> Unit,
    settings: UserSettings,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    val buttonSpacing = 10.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        // Row 1: AC, ⌫, %, ÷
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            GlassCalculatorButton(
                text = "AC",
                type = ButtonType.FUNCTION,
                settings = settings,
                isDark = isDark,
                onClick = onClearClick,
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_clear"
            )
            GlassCalculatorButton(
                text = "⌫",
                type = ButtonType.FUNCTION,
                settings = settings,
                isDark = isDark,
                onClick = onBackspaceClick,
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_backspace"
            )
            GlassCalculatorButton(
                text = "%",
                type = ButtonType.FUNCTION,
                settings = settings,
                isDark = isDark,
                onClick = onPercentClick,
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_percent"
            )
            GlassCalculatorButton(
                text = "÷",
                type = ButtonType.OPERATOR,
                settings = settings,
                isDark = isDark,
                onClick = { onOperatorClick("÷") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                fontSize = 28.sp,
                testTag = "btn_divide"
            )
        }

        // Row 2: 7, 8, 9, ×
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            GlassCalculatorButton(
                text = "7",
                type = ButtonType.NUMBER,
                settings = settings,
                isDark = isDark,
                onClick = { onDigitClick("7") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_7"
            )
            GlassCalculatorButton(
                text = "8",
                type = ButtonType.NUMBER,
                settings = settings,
                isDark = isDark,
                onClick = { onDigitClick("8") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_8"
            )
            GlassCalculatorButton(
                text = "9",
                type = ButtonType.NUMBER,
                settings = settings,
                isDark = isDark,
                onClick = { onDigitClick("9") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_9"
            )
            GlassCalculatorButton(
                text = "×",
                type = ButtonType.OPERATOR,
                settings = settings,
                isDark = isDark,
                onClick = { onOperatorClick("×") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                fontSize = 28.sp,
                testTag = "btn_multiply"
            )
        }

        // Row 3: 4, 5, 6, −
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            GlassCalculatorButton(
                text = "4",
                type = ButtonType.NUMBER,
                settings = settings,
                isDark = isDark,
                onClick = { onDigitClick("4") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_4"
            )
            GlassCalculatorButton(
                text = "5",
                type = ButtonType.NUMBER,
                settings = settings,
                isDark = isDark,
                onClick = { onDigitClick("5") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_5"
            )
            GlassCalculatorButton(
                text = "6",
                type = ButtonType.NUMBER,
                settings = settings,
                isDark = isDark,
                onClick = { onDigitClick("6") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_6"
            )
            GlassCalculatorButton(
                text = "−",
                type = ButtonType.OPERATOR,
                settings = settings,
                isDark = isDark,
                onClick = { onOperatorClick("−") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                fontSize = 28.sp,
                testTag = "btn_subtract"
            )
        }

        // Row 4: 1, 2, 3, +
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            GlassCalculatorButton(
                text = "1",
                type = ButtonType.NUMBER,
                settings = settings,
                isDark = isDark,
                onClick = { onDigitClick("1") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_1"
            )
            GlassCalculatorButton(
                text = "2",
                type = ButtonType.NUMBER,
                settings = settings,
                isDark = isDark,
                onClick = { onDigitClick("2") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_2"
            )
            GlassCalculatorButton(
                text = "3",
                type = ButtonType.NUMBER,
                settings = settings,
                isDark = isDark,
                onClick = { onDigitClick("3") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_3"
            )
            GlassCalculatorButton(
                text = "+",
                type = ButtonType.OPERATOR,
                settings = settings,
                isDark = isDark,
                onClick = { onOperatorClick("+") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                fontSize = 28.sp,
                testTag = "btn_add"
            )
        }

        // Row 5: ±, 0, ., =
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            GlassCalculatorButton(
                text = "±",
                type = ButtonType.FUNCTION,
                settings = settings,
                isDark = isDark,
                onClick = onPlusMinusClick,
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_plus_minus"
            )
            GlassCalculatorButton(
                text = "0",
                type = ButtonType.NUMBER,
                settings = settings,
                isDark = isDark,
                onClick = { onDigitClick("0") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                testTag = "btn_0"
            )
            GlassCalculatorButton(
                text = ".",
                type = ButtonType.NUMBER,
                settings = settings,
                isDark = isDark,
                onClick = { onDigitClick(".") },
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                fontSize = 28.sp,
                testTag = "btn_decimal"
            )
            GlassCalculatorButton(
                text = "=",
                type = ButtonType.ACCENT,
                settings = settings,
                isDark = isDark,
                onClick = onEqualsClick,
                modifier = Modifier.weight(1f).aspectRatio(1.2f),
                fontSize = 28.sp,
                testTag = "btn_equals"
            )
        }
    }
}

@Composable
fun ScientificKeypad(
    onDigitClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onFunctionClick: (String) -> Unit,
    onConstantClick: (String) -> Unit,
    onClearClick: () -> Unit,
    onBackspaceClick: () -> Unit,
    onEqualsClick: () -> Unit,
    onPlusMinusClick: () -> Unit,
    onAngleModeToggle: () -> Unit,
    angleMode: CalculatorEngine.AngleMode,
    settings: UserSettings,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val spacing = 6.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 14.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        // Row 1: sin, cos, tan, ln, log
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            SciBtn("sin", { onFunctionClick("sin(") }, settings, isDark, Modifier.weight(1f))
            SciBtn("cos", { onFunctionClick("cos(") }, settings, isDark, Modifier.weight(1f))
            SciBtn("tan", { onFunctionClick("tan(") }, settings, isDark, Modifier.weight(1f))
            SciBtn("ln", { onFunctionClick("ln(") }, settings, isDark, Modifier.weight(1f))
            SciBtn("log", { onFunctionClick("log(") }, settings, isDark, Modifier.weight(1f))
        }

        // Row 2: sin⁻¹, cos⁻¹, tan⁻¹, eˣ, 10ˣ
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            SciBtn("sin⁻¹", { onFunctionClick("sin⁻¹(") }, settings, isDark, Modifier.weight(1f), fontSize = 13.sp)
            SciBtn("cos⁻¹", { onFunctionClick("cos⁻¹(") }, settings, isDark, Modifier.weight(1f), fontSize = 13.sp)
            SciBtn("tan⁻¹", { onFunctionClick("tan⁻¹(") }, settings, isDark, Modifier.weight(1f), fontSize = 13.sp)
            SciBtn("eˣ", { onFunctionClick("e^(") }, settings, isDark, Modifier.weight(1f))
            SciBtn("10ˣ", { onFunctionClick("10^(") }, settings, isDark, Modifier.weight(1f), fontSize = 14.sp)
        }

        // Row 3: x², xʸ, √, ∛, π
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            SciBtn("x²", { onOperatorClick("²") }, settings, isDark, Modifier.weight(1f))
            SciBtn("xʸ", { onOperatorClick("^") }, settings, isDark, Modifier.weight(1f))
            SciBtn("√", { onFunctionClick("√(") }, settings, isDark, Modifier.weight(1f))
            SciBtn("∛", { onFunctionClick("∛(") }, settings, isDark, Modifier.weight(1f))
            SciBtn("π", { onConstantClick("π") }, settings, isDark, Modifier.weight(1f))
        }

        // Row 4: e, (, ), !, %
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            SciBtn("e", { onConstantClick("e") }, settings, isDark, Modifier.weight(1f))
            SciBtn("(", { onOperatorClick("(") }, settings, isDark, Modifier.weight(1f))
            SciBtn(")", { onOperatorClick(")") }, settings, isDark, Modifier.weight(1f))
            SciBtn("!", { onOperatorClick("!") }, settings, isDark, Modifier.weight(1f))
            SciBtn("%", { onOperatorClick("%") }, settings, isDark, Modifier.weight(1f))
        }

        // Row 5: DEG/RAD, sinh, cosh, tanh, |x|
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            GlassCalculatorButton(
                text = angleMode.name,
                type = ButtonType.OPERATOR,
                settings = settings,
                isDark = isDark,
                onClick = onAngleModeToggle,
                modifier = Modifier.weight(1f).height(46.dp),
                fontSize = 14.sp,
                testTag = "btn_angle_mode"
            )
            SciBtn("sinh", { onFunctionClick("sinh(") }, settings, isDark, Modifier.weight(1f), fontSize = 14.sp)
            SciBtn("cosh", { onFunctionClick("cosh(") }, settings, isDark, Modifier.weight(1f), fontSize = 14.sp)
            SciBtn("tanh", { onFunctionClick("tanh(") }, settings, isDark, Modifier.weight(1f), fontSize = 14.sp)
            SciBtn("|x|", { onFunctionClick("abs(") }, settings, isDark, Modifier.weight(1f))
        }

        // Row 6: 1/x, ±, AC, ⌫, ÷
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            SciBtn("¹/x", { onFunctionClick("recip(") }, settings, isDark, Modifier.weight(1f), fontSize = 14.sp)
            GlassCalculatorButton(
                text = "±",
                type = ButtonType.FUNCTION,
                settings = settings,
                isDark = isDark,
                onClick = onPlusMinusClick,
                modifier = Modifier.weight(1f).height(46.dp),
                fontSize = 18.sp,
                testTag = "btn_sci_pm"
            )
            GlassCalculatorButton(
                text = "AC",
                type = ButtonType.FUNCTION,
                settings = settings,
                isDark = isDark,
                onClick = onClearClick,
                modifier = Modifier.weight(1f).height(46.dp),
                fontSize = 16.sp,
                testTag = "btn_sci_ac"
            )
            GlassCalculatorButton(
                text = "⌫",
                type = ButtonType.FUNCTION,
                settings = settings,
                isDark = isDark,
                onClick = onBackspaceClick,
                modifier = Modifier.weight(1f).height(46.dp),
                fontSize = 16.sp,
                testTag = "btn_sci_bksp"
            )
            GlassCalculatorButton(
                text = "÷",
                type = ButtonType.OPERATOR,
                settings = settings,
                isDark = isDark,
                onClick = { onOperatorClick("÷") },
                modifier = Modifier.weight(1f).height(46.dp),
                fontSize = 20.sp,
                testTag = "btn_sci_div"
            )
        }

        // Row 7: 7, 8, 9, ×, ^
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            NumBtn("7", { onDigitClick("7") }, settings, isDark, Modifier.weight(1f))
            NumBtn("8", { onDigitClick("8") }, settings, isDark, Modifier.weight(1f))
            NumBtn("9", { onDigitClick("9") }, settings, isDark, Modifier.weight(1f))
            GlassCalculatorButton(
                text = "×",
                type = ButtonType.OPERATOR,
                settings = settings,
                isDark = isDark,
                onClick = { onOperatorClick("×") },
                modifier = Modifier.weight(1f).height(46.dp),
                fontSize = 20.sp,
                testTag = "btn_sci_mul"
            )
            GlassCalculatorButton(
                text = "−",
                type = ButtonType.OPERATOR,
                settings = settings,
                isDark = isDark,
                onClick = { onOperatorClick("−") },
                modifier = Modifier.weight(1f).height(46.dp),
                fontSize = 20.sp,
                testTag = "btn_sci_sub"
            )
        }

        // Row 8: 4, 5, 6, +
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            NumBtn("4", { onDigitClick("4") }, settings, isDark, Modifier.weight(1f))
            NumBtn("5", { onDigitClick("5") }, settings, isDark, Modifier.weight(1f))
            NumBtn("6", { onDigitClick("6") }, settings, isDark, Modifier.weight(1f))
            GlassCalculatorButton(
                text = "+",
                type = ButtonType.OPERATOR,
                settings = settings,
                isDark = isDark,
                onClick = { onOperatorClick("+") },
                modifier = Modifier.weight(1f).height(46.dp),
                fontSize = 20.sp,
                testTag = "btn_sci_add"
            )
            GlassCalculatorButton(
                text = "=",
                type = ButtonType.ACCENT,
                settings = settings,
                isDark = isDark,
                onClick = onEqualsClick,
                modifier = Modifier.weight(1f).height(46.dp),
                fontSize = 20.sp,
                testTag = "btn_sci_eq"
            )
        }

        // Row 9: 1, 2, 3, 0, .
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            NumBtn("1", { onDigitClick("1") }, settings, isDark, Modifier.weight(1f))
            NumBtn("2", { onDigitClick("2") }, settings, isDark, Modifier.weight(1f))
            NumBtn("3", { onDigitClick("3") }, settings, isDark, Modifier.weight(1f))
            NumBtn("0", { onDigitClick("0") }, settings, isDark, Modifier.weight(1f))
            NumBtn(".", { onDigitClick(".") }, settings, isDark, Modifier.weight(1f))
        }
    }
}

@Composable
private fun SciBtn(
    text: String,
    onClick: () -> Unit,
    settings: UserSettings,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    fontSize: androidx.compose.ui.unit.TextUnit = 15.sp
) {
    GlassCalculatorButton(
        text = text,
        type = ButtonType.SCIENTIFIC,
        settings = settings,
        isDark = isDark,
        onClick = onClick,
        modifier = modifier.height(44.dp),
        fontSize = fontSize,
        testTag = "btn_sci_$text"
    )
}

@Composable
private fun NumBtn(
    text: String,
    onClick: () -> Unit,
    settings: UserSettings,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    GlassCalculatorButton(
        text = text,
        type = ButtonType.NUMBER,
        settings = settings,
        isDark = isDark,
        onClick = onClick,
        modifier = modifier.height(46.dp),
        fontSize = 20.sp,
        testTag = "btn_num_$text"
    )
}
