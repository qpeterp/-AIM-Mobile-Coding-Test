package com.qpeterp.assetmanagement.presentation.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.qpeterp.assetmanagement.R
import com.qpeterp.assetmanagement.presentation.utils.PhoneNumberVisualTransformation

enum class TextFieldType {
    COMMON, PASSWORD, PHONE_NUMBER
}

@Composable
fun AmTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    type: TextFieldType = TextFieldType.COMMON,
    modifier: Modifier = Modifier,
) {
    var passwordVisible by remember { mutableStateOf(type != TextFieldType.PASSWORD) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier.then(Modifier.fillMaxWidth()),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ),
        visualTransformation = when (type) {
            TextFieldType.PASSWORD -> if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            TextFieldType.PHONE_NUMBER -> PhoneNumberVisualTransformation()
            else -> VisualTransformation.None
        },
        keyboardOptions = when (type) {
            TextFieldType.PASSWORD -> KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            TextFieldType.PHONE_NUMBER -> KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            else -> KeyboardOptions.Default
        },
        trailingIcon = {
            if (type == TextFieldType.PASSWORD) {
                val icon =
                    if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = painterResource(id = icon), contentDescription = "비밀번호 보기/숨기기")
                }
            }
        }
    )
}