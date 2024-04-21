package com.msmlabs.outcoding.presentation.catlist

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msmlabs.outcoding.domain.Either
import com.msmlabs.outcoding.domain.exception.NetworkException
import com.msmlabs.outcoding.domain.model.Cat
import com.msmlabs.outcoding.domain.repository.CatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val repository: CatRepository,
) : ViewModel() {

    val listUiState: StateFlow<ListUiState> = flow {
        when (val response = repository.getCatList()) {
            is Either.Success -> emit(ListUiState.Success(response.data))
            is Either.Failure -> {
                when (response.cause) {
                    is NetworkException.GeneralException ->
                        emit(ListUiState.Error(NetworkException.GeneralException))

                    is NetworkException.NetworkUnavailable ->
                        emit(ListUiState.Error(NetworkException.NetworkUnavailable))

                    is NetworkException.ServerResponseError ->
                        emit(ListUiState.Error(NetworkException.ServerResponseError))
                }
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        ListUiState.Loading
    )
}

sealed interface ListUiState {
    @Stable
    data class Success(val catList: List<Cat> = emptyList()) : ListUiState
    data class Error(val error: NetworkException) : ListUiState
    data object Loading : ListUiState
}
