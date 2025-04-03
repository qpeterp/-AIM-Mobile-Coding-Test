package com.qpeterp.assetmanagement.data.remote.base

import kotlinx.serialization.Serializable

@Serializable
sealed interface BaseResponse {
    val result: Result
}

@Serializable
data class DataResponse<T>(
    override val result: Result,
    val data: T,
) : BaseResponse

@Serializable
data class VoidResponse(
    override val result: Result,
) : BaseResponse

@Serializable
data class ErrorResponse(
    override val result: Result,
    val kind: String,
) : BaseResponse

@Serializable
data class Result(
    val message: String,
    val code: Int,
)