package com.example.composeotp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composeotp.ui.theme.ComposeOTPTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeOTPTheme {
                var otpValue by remember { mutableStateOf("") }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OtpTextField(
                        otpText = otpValue,
                        onOtpTextChange = { value, otpInputFilled ->
                            otpValue = value
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 4,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()

            ) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .height(70.dp)
            .width(80.dp)

            .clip(RoundedCornerShape(10.dp))
            .background(Color.Blue.copy(alpha = 0.1f, 0.1f))
            .wrapContentSize(align = Alignment.Center),
        text = char,
        style = MaterialTheme.typography.titleLarge,
        color = if (isFocused) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        textAlign = TextAlign.Center,

        )
}