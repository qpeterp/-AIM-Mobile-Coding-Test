package com.qpeterp.assetmanagement.presentation.features.auth.register.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.qpeterp.assetmanagement.application.MyApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {
    private val _step = MutableStateFlow(1)
    val step = _step.asStateFlow()

    private val _id = MutableStateFlow("")
    val id = _id.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _idError = MutableStateFlow(true)
    val idError = _idError.asStateFlow()

    private val _passwordError = MutableStateFlow(true)
    val passwordError = _passwordError.asStateFlow()

    private val _phoneNumberError = MutableStateFlow(true)
    val phoneNumberError = _phoneNumberError.asStateFlow()

    private val _emailError = MutableStateFlow(true)
    val emailError = _emailError.asStateFlow()


    fun updateId(value: String) {
        if (value.length >= 20) return

        _id.value = value
        _idError.value = !value.matches(Regex("^[a-zA-Z]{7,}$"))
    }

    fun updatePassword(value: String) {
        if (value.length >= 32) return

        _password.value = value
        _passwordError.value =
            !value.matches(Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&]).{10,}$"))
    }

    fun updatePhoneNumber(value: String) {
        if (value.length >= 12) return

        _phoneNumber.value = value
        _phoneNumberError.value = !value.matches(Regex("^01[0-9]\\d{8}$"))
    }

    fun updateEmail(value: String) {
        _email.value = value
        _emailError.value = !Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

    fun nextStep() {
        when (_step.value) {
            1 -> if (_idError.value.not()) _step.value++
            2 -> if (_passwordError.value.not()) _step.value++
            3 -> if (_phoneNumberError.value.not()) _step.value++
            4 -> if (_emailError.value.not()) _step.value++
        }
    }
}