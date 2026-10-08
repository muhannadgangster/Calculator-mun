package com.example.calculator.engine

import kotlin.math.*

class CalculatorEngine {

    enum class AngleMode { DEG, RAD }

    sealed class EvalResult {
        data class Success(val value: Double, val formatted: String) : EvalResult()
        data class Error(val message: String) : EvalResult()
    }

    fun evaluate(expression: String, angleMode: AngleMode): EvalResult {
        if (expression.isBlank()) return EvalResult.Success(0.0, "0")
        return try {
            val sanitized = sanitize(expression)
            val tokens = tokenize(sanitized)
            val rpn = shuntingYard(tokens)
            val result = evaluateRpn(rpn, angleMode)
            if (result.isNaN()) {
                EvalResult.Error("Undefined")
            } else if (result.isInfinite()) {
                EvalResult.Error("Overflow")
            } else {
                EvalResult.Success(result, formatResult(result))
            }
        } catch (e: ArithmeticException) {
            EvalResult.Error(e.message ?: "Math Error")
        } catch (e: Exception) {
            EvalResult.Error("Invalid Expression")
        }
    }

    private fun sanitize(expr: String): String {
        return expr
            .replace("×", "*")
            .replace("÷", "/")
            .replace("−", "-")
            .replace("π", "PI")
            .replace("²", "^2")
            .replace("³", "^3")
            .replace("√", "sqrt")
            .replace("∛", "cbrt")
            .replace("sin⁻¹", "asin")
            .replace("cos⁻¹", "acos")
            .replace("tan⁻¹", "atan")
            .replace("10^", "tenpow")
            .replace("e^", "exp")
    }

    private sealed class Token {
        data class Number(val value: Double) : Token()
        data class Operator(val op: Char, val precedence: Int, val rightAssociative: Boolean) : Token()
        data class Function(val name: String) : Token()
        object LeftParen : Token()
        object RightParen : Token()
        object Factorial : Token()
        object Percent : Token()
    }

    private fun tokenize(expr: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var i = 0
        var expectUnary = true

        while (i < expr.length) {
            val c = expr[i]
            when {
                c.isWhitespace() -> {
                    i++
                }
                c.isDigit() || c == '.' -> {
                    val sb = StringBuilder()
                    while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) {
                        sb.append(expr[i])
                        i++
                    }
                    val num = sb.toString().toDoubleOrNull() ?: throw IllegalArgumentException("Invalid number")
                    tokens.add(Token.Number(num))
                    expectUnary = false
                }
                c == '+' || c == '-' -> {
                    if (expectUnary) {
                        if (c == '-') {
                            // Unary minus treated as 0 - x or negate function
                            tokens.add(Token.Number(0.0))
                            tokens.add(Token.Operator('-', 1, false))
                        }
                        // Unary plus ignored
                    } else {
                        tokens.add(Token.Operator(c, 1, false))
                        expectUnary = true
                    }
                    i++
                }
                c == '*' || c == '/' -> {
                    tokens.add(Token.Operator(c, 2, false))
                    expectUnary = true
                    i++
                }
                c == '^' -> {
                    tokens.add(Token.Operator('^', 3, true))
                    expectUnary = true
                    i++
                }
                c == '!' -> {
                    tokens.add(Token.Factorial)
                    expectUnary = false
                    i++
                }
                c == '%' -> {
                    tokens.add(Token.Percent)
                    expectUnary = false
                    i++
                }
                c == '(' -> {
                    // Check for implicit multiplication e.g., 2(3) -> 2*(3)
                    if (tokens.isNotEmpty()) {
                        val last = tokens.last()
                        if (last is Token.Number || last is Token.RightParen || last is Token.Factorial || last is Token.Percent) {
                            tokens.add(Token.Operator('*', 2, false))
                        }
                    }
                    tokens.add(Token.LeftParen)
                    expectUnary = true
                    i++
                }
                c == ')' -> {
                    tokens.add(Token.RightParen)
                    expectUnary = false
                    i++
                }
                c.isLetter() -> {
                    val sb = StringBuilder()
                    while (i < expr.length && (expr[i].isLetter() || expr[i].isDigit())) {
                        sb.append(expr[i])
                        i++
                    }
                    val word = sb.toString()
                    when (word) {
                        "PI" -> {
                            // Implicit multiply: e.g. 2PI
                            if (tokens.isNotEmpty() && (tokens.last() is Token.Number || tokens.last() is Token.RightParen)) {
                                tokens.add(Token.Operator('*', 2, false))
                            }
                            tokens.add(Token.Number(PI))
                            expectUnary = false
                        }
                        "e" -> {
                            if (tokens.isNotEmpty() && (tokens.last() is Token.Number || tokens.last() is Token.RightParen)) {
                                tokens.add(Token.Operator('*', 2, false))
                            }
                            tokens.add(Token.Number(E))
                            expectUnary = false
                        }
                        else -> {
                            if (tokens.isNotEmpty() && (tokens.last() is Token.Number || tokens.last() is Token.RightParen)) {
                                tokens.add(Token.Operator('*', 2, false))
                            }
                            tokens.add(Token.Function(word))
                            expectUnary = true
                        }
                    }
                }
                else -> {
                    i++
                }
            }
        }
        return tokens
    }

    private fun shuntingYard(tokens: List<Token>): List<Token> {
        val output = mutableListOf<Token>()
        val stack = ArrayDeque<Token>()

        for (token in tokens) {
            when (token) {
                is Token.Number -> output.add(token)
                is Token.Function -> stack.addLast(token)
                is Token.Factorial, is Token.Percent -> output.add(token) // postfix operator
                is Token.Operator -> {
                    while (stack.isNotEmpty()) {
                        val top = stack.last()
                        if (top is Token.Operator && (
                                    (!token.rightAssociative && top.precedence >= token.precedence) ||
                                            (token.rightAssociative && top.precedence > token.precedence)
                                    )) {
                            output.add(stack.removeLast())
                        } else if (top is Token.Function) {
                            output.add(stack.removeLast())
                        } else {
                            break
                        }
                    }
                    stack.addLast(token)
                }
                is Token.LeftParen -> stack.addLast(token)
                is Token.RightParen -> {
                    var foundParen = false
                    while (stack.isNotEmpty()) {
                        val top = stack.removeLast()
                        if (top is Token.LeftParen) {
                            foundParen = true
                            break
                        } else {
                            output.add(top)
                        }
                    }
                    if (!foundParen) {
                        throw IllegalArgumentException("Mismatched parentheses")
                    }
                    if (stack.isNotEmpty() && stack.last() is Token.Function) {
                        output.add(stack.removeLast())
                    }
                }
            }
        }

        while (stack.isNotEmpty()) {
            val top = stack.removeLast()
            if (top is Token.LeftParen || top is Token.RightParen) {
                throw IllegalArgumentException("Mismatched parentheses")
            }
            output.add(top)
        }
        return output
    }

    private fun evaluateRpn(rpn: List<Token>, angleMode: AngleMode): Double {
        val stack = ArrayDeque<Double>()

        fun toRadians(deg: Double): Double = if (angleMode == AngleMode.DEG) Math.toRadians(deg) else deg
        fun fromRadians(rad: Double): Double = if (angleMode == AngleMode.DEG) Math.toDegrees(rad) else rad

        for (token in rpn) {
            when (token) {
                is Token.Number -> stack.addLast(token.value)
                is Token.Operator -> {
                    if (stack.size < 2) throw IllegalArgumentException("Malformed expression")
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    val res = when (token.op) {
                        '+' -> a + b
                        '-' -> a - b
                        '*' -> a * b
                        '/' -> {
                            if (b == 0.0) throw ArithmeticException("Cannot divide by zero")
                            a / b
                        }
                        '^' -> a.pow(b)
                        else -> throw IllegalArgumentException("Unknown operator ${token.op}")
                    }
                    stack.addLast(res)
                }
                is Token.Factorial -> {
                    if (stack.isEmpty()) throw IllegalArgumentException("Malformed expression")
                    val a = stack.removeLast()
                    stack.addLast(factorial(a))
                }
                is Token.Percent -> {
                    if (stack.isEmpty()) throw IllegalArgumentException("Malformed expression")
                    val a = stack.removeLast()
                    stack.addLast(a / 100.0)
                }
                is Token.Function -> {
                    if (stack.isEmpty()) throw IllegalArgumentException("Malformed expression")
                    val a = stack.removeLast()
                    val res = when (token.name.lowercase()) {
                        "sin" -> {
                            val r = toRadians(a)
                            // Clean up exact angles like sin(180) = 0
                            val v = sin(r)
                            if (abs(v) < 1e-15) 0.0 else v
                        }
                        "cos" -> {
                            val r = toRadians(a)
                            val v = cos(r)
                            if (abs(v) < 1e-15) 0.0 else v
                        }
                        "tan" -> {
                            val r = toRadians(a)
                            if (abs(cos(r)) < 1e-15) throw ArithmeticException("Undefined (tan 90°)")
                            val v = tan(r)
                            if (abs(v) < 1e-15) 0.0 else v
                        }
                        "asin" -> {
                            if (a < -1.0 || a > 1.0) throw ArithmeticException("Domain error")
                            fromRadians(asin(a))
                        }
                        "acos" -> {
                            if (a < -1.0 || a > 1.0) throw ArithmeticException("Domain error")
                            fromRadians(acos(a))
                        }
                        "atan" -> fromRadians(atan(a))
                        "sinh" -> sinh(a)
                        "cosh" -> cosh(a)
                        "tanh" -> tanh(a)
                        "ln" -> {
                            if (a <= 0.0) throw ArithmeticException("Domain error")
                            ln(a)
                        }
                        "log" -> {
                            if (a <= 0.0) throw ArithmeticException("Domain error")
                            log10(a)
                        }
                        "sqrt" -> {
                            if (a < 0.0) throw ArithmeticException("Domain error")
                            sqrt(a)
                        }
                        "cbrt" -> cbrt(a)
                        "exp" -> exp(a)
                        "tenpow" -> 10.0.pow(a)
                        "abs" -> abs(a)
                        "recip" -> {
                            if (a == 0.0) throw ArithmeticException("Cannot divide by zero")
                            1.0 / a
                        }
                        else -> throw IllegalArgumentException("Unknown function ${token.name}")
                    }
                    stack.addLast(res)
                }
                else -> {}
            }
        }

        if (stack.size != 1) throw IllegalArgumentException("Malformed expression")
        return stack.removeLast()
    }

    private fun factorial(n: Double): Double {
        if (n < 0 || n != floor(n)) throw ArithmeticException("Factorial requires non-negative integer")
        if (n > 170) throw ArithmeticException("Factorial overflow")
        var result = 1.0
        val count = n.toLong()
        for (i in 2..count) {
            result *= i
        }
        return result
    }

    fun formatResult(value: Double): String {
        if (value.isNaN()) return "Undefined"
        if (value.isInfinite()) return if (value > 0) "Infinity" else "-Infinity"
        if (abs(value) < 1e-15) return "0"

        // Check if value is very large or very small
        if (abs(value) >= 1e12 || (abs(value) > 0 && abs(value) < 1e-6)) {
            return String.format(java.util.Locale.US, "%.6e", value)
                .replace("e+", "e")
        }

        val rounded = (value * 1e10).roundToLong() / 1e10
        return if (rounded == floor(rounded) && abs(rounded) < 1e15) {
            rounded.toLong().toString()
        } else {
            val formatted = String.format(java.util.Locale.US, "%.8f", value)
                .trimEnd('0')
                .trimEnd('.')
            if (formatted == "-0") "0" else formatted
        }
    }
}
