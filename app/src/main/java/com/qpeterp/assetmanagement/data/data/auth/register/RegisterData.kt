package com.qpeterp.assetmanagement.data.data.auth.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterData(
    val loginId: String,
    val password: String,
    val name: String
)
