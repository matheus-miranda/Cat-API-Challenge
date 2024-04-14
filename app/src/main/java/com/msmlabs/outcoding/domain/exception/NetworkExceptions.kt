package com.msmlabs.outcoding.domain.exception

sealed class NetworkException : Exception() {
    data object NetworkUnavailable : NetworkException()
    data object ServerResponseError : NetworkException()
    data object GeneralException : NetworkException()
}
