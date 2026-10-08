package com.example

import com.example.calculator.engine.CalculatorEngine
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CalculatorEngineTest {

    private lateinit var engine: CalculatorEngine

    @Before
    fun setUp() {
        engine = CalculatorEngine()
    }

    @Test
    fun testNormalExpressionFromReference() {
        // (12 + 8) × 5 -> 100
        val res = engine.evaluate("(12 + 8) × 5", CalculatorEngine.AngleMode.DEG)
        assertTrue(res is CalculatorEngine.EvalResult.Success)
        assertEquals("100", (res as CalculatorEngine.EvalResult.Success).formatted)
    }

    @Test
    fun testOrderOfOperations() {
        // 2 + 3 × 4 = 14
        val res = engine.evaluate("2 + 3 × 4", CalculatorEngine.AngleMode.DEG)
        assertTrue(res is CalculatorEngine.EvalResult.Success)
        assertEquals("14", (res as CalculatorEngine.EvalResult.Success).formatted)
    }

    @Test
    fun testScientificTrigonometryDegree() {
        // sin(30) = 0.5
        val res = engine.evaluate("sin(30)", CalculatorEngine.AngleMode.DEG)
        assertTrue(res is CalculatorEngine.EvalResult.Success)
        assertEquals("0.5", (res as CalculatorEngine.EvalResult.Success).formatted)
    }

    @Test
    fun testHistoryExpressionsFromReference() {
        // sqrt(16) -> 4
        val res1 = engine.evaluate("sqrt(16)", CalculatorEngine.AngleMode.DEG)
        assertTrue(res1 is CalculatorEngine.EvalResult.Success)
        assertEquals("4", (res1 as CalculatorEngine.EvalResult.Success).formatted)

        // (25 × 4) + 10 -> 110
        val res2 = engine.evaluate("(25 × 4) + 10", CalculatorEngine.AngleMode.DEG)
        assertTrue(res2 is CalculatorEngine.EvalResult.Success)
        assertEquals("110", (res2 as CalculatorEngine.EvalResult.Success).formatted)

        // log(100) -> 2
        val res3 = engine.evaluate("log(100)", CalculatorEngine.AngleMode.DEG)
        assertTrue(res3 is CalculatorEngine.EvalResult.Success)
        assertEquals("2", (res3 as CalculatorEngine.EvalResult.Success).formatted)

        // (7 + 3) × (5 − 2) -> 30
        val res4 = engine.evaluate("(7 + 3) × (5 − 2)", CalculatorEngine.AngleMode.DEG)
        assertTrue(res4 is CalculatorEngine.EvalResult.Success)
        assertEquals("30", (res4 as CalculatorEngine.EvalResult.Success).formatted)
    }

    @Test
    fun testFactorialAndPower() {
        // 5! -> 120
        val res = engine.evaluate("5!", CalculatorEngine.AngleMode.DEG)
        assertTrue(res is CalculatorEngine.EvalResult.Success)
        assertEquals("120", (res as CalculatorEngine.EvalResult.Success).formatted)

        // 2^3 -> 8
        val res2 = engine.evaluate("2^3", CalculatorEngine.AngleMode.DEG)
        assertTrue(res2 is CalculatorEngine.EvalResult.Success)
        assertEquals("8", (res2 as CalculatorEngine.EvalResult.Success).formatted)
    }

    @Test
    fun testDivideByZeroHandling() {
        val res = engine.evaluate("10 ÷ 0", CalculatorEngine.AngleMode.DEG)
        assertTrue(res is CalculatorEngine.EvalResult.Error)
        assertEquals("Cannot divide by zero", (res as CalculatorEngine.EvalResult.Error).message)
    }

    @Test
    fun testUnbalancedParentheses() {
        val res = engine.evaluate("(12 + 5", CalculatorEngine.AngleMode.DEG)
        assertTrue(res is CalculatorEngine.EvalResult.Error)
    }
}
