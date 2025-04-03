package com.qpeterp.assetmanagement.presentation.features.auth.register.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.qpeterp.assetmanagement.application.MyApplication
import com.qpeterp.assetmanagement.presentation.core.components.AmButton
import com.qpeterp.assetmanagement.presentation.core.components.AmTextField
import com.qpeterp.assetmanagement.presentation.core.components.ErrorText
import com.qpeterp.assetmanagement.presentation.core.components.TextFieldType
import com.qpeterp.assetmanagement.presentation.features.auth.register.viewmodel.RegisterViewModel
import com.qpeterp.assetmanagement.presentation.root.navigation.NavGroup

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val step by viewModel.step.collectAsState()
    val id by viewModel.id.collectAsState()
    val password by viewModel.password.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val email by viewModel.email.collectAsState()
    val idError by viewModel.idError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val phoneNumberError by viewModel.phoneNumberError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()

    Column(modifier = Modifier.padding(32.dp, 160.dp)) {
        Text(
            text = when (step) {
                1 -> "아이디를 입력해주세요"
                2 -> "비밀번호를 입력해주세요"
                3 -> "전화번호를 입력해주세요"
                4 -> "이메일을 입력해주세요"
                else -> "회원가입 완료"
            },
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 64.dp)
        )

        if (step >= 4) {
            AmTextField(value = email, onValueChange = viewModel::updateEmail, label = "이메일")
            ErrorText(isVisible = emailError, text = "이메일 형식을 지켜서 입력해주세요.")
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (step >= 3) {
            AmTextField(
                value = phoneNumber,
                onValueChange = viewModel::updatePhoneNumber,
                label = "핸드폰 번호",
                type = TextFieldType.PHONE_NUMBER
            )
            ErrorText(isVisible = phoneNumberError, text = "전화번호는 010-0000-0000 형식으로 입력해주세요.")
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (step >= 2) {
            AmTextField(
                value = password,
                onValueChange = viewModel::updatePassword,
                label = "비밀번호",
                type = TextFieldType.PASSWORD
            )
            ErrorText(isVisible = passwordError, text = "비밀번호는 대소문자, 숫자, 특수문자 포함 10자 이상만 가능합니다.")
            Spacer(modifier = Modifier.height(8.dp))
        }

        AmTextField(value = id, onValueChange = viewModel::updateId, label = "아이디")
        ErrorText(isVisible = idError, text = "아이디는 영문 7자 이상만 가능합니다.")
        Spacer(modifier = Modifier.height(8.dp))

        AmButton(
            // TODO: api 연동 시, 회원 검증 추가
            onClick = {
                if (step < 4) {
                    viewModel.nextStep()
                } else {
                    MyApplication.prefs.userId = id
                    navController.navigate(NavGroup.Main.HOME)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = when (step) {
                1 -> !idError
                2 -> !idError && !passwordError
                3 -> !idError && !passwordError && !phoneNumberError
                4 -> !idError && !passwordError && !phoneNumberError && !emailError
                else -> false
            }
        ) {
            Text(if (step < 4) "다음" else "완료")
        }
    }
}