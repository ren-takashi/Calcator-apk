package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CalculatorApp() }
    }
}

@Composable
fun CalculatorApp() {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val buttons = listOf(
        listOf("C", "(", ")", "/"),
        listOf("7", "8", "9", "*"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("0", ".", "^", "="),
        listOf("sin", "cos", "tan", "log")
    )

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF121212)).padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = expression, fontSize = 36.sp, color = Color.LightGray,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = result, fontSize = 52.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50),
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.height(32.dp))

        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (btn in row) {
                    Button(
                        onClick = {
                            when (btn) {
                                "C" -> { expression = ""; result = "" }
                                "=" -> {
                                    try {
                                        val calc = ExpressionBuilder(expression).build().evaluate()
                                        // Drop the .0 if it's a whole number
                                        result = if (calc % 1.0 == 0.0) calc.toLong().toString() else calc.toString()
                                    } catch (e: Exception) { result = "Error" }
                                }
                                "sin", "cos", "tan", "log" -> expression += "$btn("
                                else -> expression += btn
                            }
                        },
                        modifier = Modifier.weight(1f).padding(4.dp).aspectRatio(if(row.size == 4) 1.2f else 2f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C2C2C))
                    ) {
                        Text(text = btn, fontSize = if (row.size == 4) 24.sp else 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
