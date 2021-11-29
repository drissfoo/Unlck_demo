package com.unlck.domain.util

sealed class Result<out TResultModel> {
    companion object {
        fun <TResultModel> fromNullable(result: TResultModel?) = when (result) {
            null -> Failure()
            else -> Success(result)
        }
    }

    data class Success<out TResultModel>(val result: TResultModel) : Result<TResultModel>()
    data class Failure(val error: Throwable? = null) : Result<Nothing>()
    object Loading: Result<Nothing>()
}