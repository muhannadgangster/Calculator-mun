package com.example.calculator.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.data.CalculatorRepository
import com.example.calculator.engine.CalculatorEngine
import com.example.calculator.model.AppScreen
import com.example.calculator.model.CalculatorMode
import com.example.calculator.model.HistoryItem
import com.example.calculator.model.UserSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CalculatorRepository(application)
    private val engine = CalculatorEngine()

    private val _settings = MutableStateFlow(repository.loadSettings())
    val settings: StateFlow<UserSettings> = _settings.asStateFlow()

    private val _history = MutableStateFlow(repository.loadHistory())
    val history: StateFlow<List<HistoryItem>> = _history.asStateFlow()

    private val _currentScreen = MutableStateFlow<AppScreen>(AppScreen.Calculator)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _calculatorMode = MutableStateFlow(_settings.value.defaultCalculatorMode)
    val calculatorMode: StateFlow<CalculatorMode> = _calculatorMode.asStateFlow()

    private val _angleMode = MutableStateFlow(CalculatorEngine.AngleMode.DEG)
    val angleMode: StateFlow<CalculatorEngine.AngleMode> = _angleMode.asStateFlow()

    private val _expression = MutableStateFlow("")
    val expression: StateFlow<String> = _expression.asStateFlow()

    private val _result = MutableStateFlow("0")
    val result: StateFlow<String> = _result.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val screenStack = ArrayDeque<AppScreen>()

    init {
        // Compute initial live result
        evaluateLive()
    }

    fun navigateTo(screen: AppScreen) {
        screenStack.addLast(_currentScreen.value)
        _currentScreen.value = screen
    }

    fun navigateBack(): Boolean {
        if (screenStack.isNotEmpty()) {
            _currentScreen.value = screenStack.removeLast()
            return true
        } else if (_currentScreen.value !is AppScreen.Calculator) {
            _currentScreen.value = AppScreen.Calculator
            return true
        }
        return false
    }

    fun setCalculatorMode(mode: CalculatorMode) {
        _calculatorMode.value = mode
    }

    fun toggleAngleMode() {
        _angleMode.value = if (_angleMode.value == CalculatorEngine.AngleMode.DEG) {
            CalculatorEngine.AngleMode.RAD
        } else {
            CalculatorEngine.AngleMode.DEG
        }
        evaluateLive()
    }

    fun onDigit(digit: String) {
        if (_isError.value) {
            _expression.value = digit
            _isError.value = false
        } else {
            _expression.value += digit
        }
        evaluateLive()
    }

    fun onOperator(op: String) {
        if (_isError.value) {
            _isError.value = false
            _expression.value = ""
        }
        if (_expression.value.isEmpty()) {
            if (op == "−" || op == "-") {
                _expression.value = "−"
                return
            } else if (_result.value != "0" && !_result.value.contains("Error") && !_result.value.contains("Undefined")) {
                _expression.value = _result.value + op
                return
            }
        }
        _expression.value += op
        evaluateLive()
    }

    fun onFunction(func: String) {
        if (_isError.value) {
            _isError.value = false
            _expression.value = ""
        }
        _expression.value += func
        evaluateLive()
    }

    fun onConstant(constant: String) {
        if (_isError.value) {
            _isError.value = false
            _expression.value = ""
        }
        _expression.value += constant
        evaluateLive()
    }

    fun onClear() {
        _expression.value = ""
        _result.value = "0"
        _isError.value = false
    }

    fun onBackspace() {
        if (_isError.value) {
            onClear()
            return
        }
        val cur = _expression.value
        if (cur.isNotEmpty()) {
            // Check for multi-character tokens like "sin(", "asin(", "cos(", "tan(", "ln(", "log(", "sinh("
            val multiTokens = listOf(
                "sin⁻¹(", "cos⁻¹(", "tan⁻¹(", "sinh(", "cosh(", "tanh(",
                "sin(", "cos(", "tan(", "ln(", "log(", "sqrt(", "cbrt(",
                "recip(", "abs(", "10^(", "e^("
            )
            val matched = multiTokens.firstOrNull { cur.endsWith(it) }
            if (matched != null) {
                _expression.value = cur.dropLast(matched.length)
            } else {
                _expression.value = cur.dropLast(1)
            }
        }
        evaluateLive()
    }

    fun onPlusMinus() {
        val cur = _expression.value
        if (cur.isEmpty()) {
            _expression.value = "−"
        } else if (cur.startsWith("−") && !cur.drop(1).contains(Regex("[+\\-×÷]"))) {
            _expression.value = cur.drop(1)
        } else if (!cur.contains(Regex("[+\\-×÷]"))) {
            _expression.value = "−$cur"
        } else {
            _expression.value = "−($cur)"
        }
        evaluateLive()
    }

    fun onPercent() {
        _expression.value += "%"
        evaluateLive()
    }

    fun onEquals() {
        val expr = _expression.value
        if (expr.isBlank()) return

        when (val eval = engine.evaluate(expr, _angleMode.value)) {
            is CalculatorEngine.EvalResult.Success -> {
                val formatted = eval.formatted
                _result.value = formatted
                _isError.value = false

                // Add to history
                val newItem = HistoryItem(
                    expression = expr,
                    result = formatted,
                    timestamp = System.currentTimeMillis()
                )
                val updated = listOf(newItem) + _history.value
                _history.value = updated
                viewModelScope.launch {
                    repository.saveHistory(updated)
                }

                // Prepared for next chained calculation
                _expression.value = formatted
            }
            is CalculatorEngine.EvalResult.Error -> {
                _result.value = eval.message
                _isError.value = true
            }
        }
    }

    private fun evaluateLive() {
        val expr = _expression.value
        if (expr.isBlank()) {
            _result.value = "0"
            _isError.value = false
            return
        }

        // Try evaluating live
        when (val eval = engine.evaluate(expr, _angleMode.value)) {
            is CalculatorEngine.EvalResult.Success -> {
                _result.value = eval.formatted
                _isError.value = false
            }
            is CalculatorEngine.EvalResult.Error -> {
                // If ending with operator or unbalanced paren, don't show error during active typing
                if (expr.endsWith("+") || expr.endsWith("−") || expr.endsWith("×") || expr.endsWith("÷") || expr.endsWith("(")) {
                    // keep previous result or don't trigger error state yet
                } else {
                    // Still don't show aggressive error until user presses =
                }
            }
        }
    }

    fun updateSettings(transform: (UserSettings) -> UserSettings) {
        val updated = transform(_settings.value)
        _settings.value = updated
        viewModelScope.launch {
            repository.saveSettings(updated)
        }
    }

    fun resetSettings() {
        val def = repository.resetSettings()
        _settings.value = def
    }

    fun clearHistory() {
        _history.value = emptyList()
        viewModelScope.launch {
            repository.saveHistory(emptyList())
        }
    }

    fun deleteHistoryItem(id: Long) {
        val updated = _history.value.filterNot { it.id == id }
        _history.value = updated
        viewModelScope.launch {
            repository.saveHistory(updated)
        }
    }

    fun reuseHistoryItem(item: HistoryItem) {
        _expression.value = item.result
        _result.value = item.result
        _isError.value = false
        navigateBack()
    }
}
