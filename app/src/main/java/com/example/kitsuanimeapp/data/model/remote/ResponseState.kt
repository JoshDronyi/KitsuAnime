package com.example.kitsuanimeapp.data.model.remote

import com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO.CategoryData
import com.example.kitsuanimeapp.util.ErrorTypes
import com.example.kitsuanimeapp.util.StatusCodes

sealed class ResponseState {

    sealed class Success : ResponseState() {
        data class CategorySuccess(
            val categories: List<CategoryData>,
        ) : Success()
    }

    data class ErrorResponse(
        val errorType: ErrorTypes,
        val statusCode: Int,
        val throwable: Throwable? = null,
    ) : ResponseState() {
        val description: String
            get() = when {
                errorType == ErrorTypes.INVALID_REQUEST &&
                    statusCode == StatusCodes.SC_400 -> {
                    "The request is missing a parameter, uses an unsupported parameter or " +
                        "repeats a parameter."
                }
                errorType == ErrorTypes.INVALID_CLIENT &&
                    statusCode == StatusCodes.SC_401 -> {
                    "The request contains an invalid client ID or secret."
                }
                errorType == ErrorTypes.INVALID_GRANT &&
                    statusCode == StatusCodes.SC_400 -> {
                    "The authorization code (or password with the password grant) is invalid or expired."
                }
                errorType == ErrorTypes.INVALID_SCOPE &&
                    statusCode == StatusCodes.SC_400 -> {
                    "The request contains an invalid scope (password or client credential grants)."
                }
                errorType == ErrorTypes.UNAUTHORIZED_CLIENT &&
                    statusCode == StatusCodes.SC_400 -> {
                    "The client is not authorized to use the requested grant type."
                }
                errorType == ErrorTypes.UNSUPPORTED_GRANT_TYPE &&
                    statusCode == StatusCodes.SC_400 -> {
                    "The grant type requested is not recognized by the server."
                }
                else -> {
                    "Sorry, not sure what went wrong this time."
                }
            }
    }
}
