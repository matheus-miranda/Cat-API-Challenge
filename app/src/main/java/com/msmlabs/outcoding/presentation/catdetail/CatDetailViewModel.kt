package com.msmlabs.outcoding.presentation.catdetail

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msmlabs.outcoding.domain.Either
import com.msmlabs.outcoding.domain.exception.NetworkException
import com.msmlabs.outcoding.domain.model.Cat
import com.msmlabs.outcoding.domain.repository.CatRepository
import com.msmlabs.outcoding.presentation.catdetail.navigation.CAT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val repository: CatRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val catId: String = checkNotNull(savedStateHandle[CAT_ID])

    val detailUiState: StateFlow<DetailUiState> = flow {
        when (val response = repository.getCatById(catId)) {
            is Either.Success -> emit(DetailUiState.Success(response.data))
            is Either.Failure -> {
                when (response.cause) {
                    is NetworkException.GeneralException ->
                        emit(DetailUiState.Error(NetworkException.GeneralException))

                    is NetworkException.NetworkUnavailable ->
                        emit(DetailUiState.Error(NetworkException.NetworkUnavailable))

                    is NetworkException.ServerResponseError ->
                        emit(DetailUiState.Error(NetworkException.ServerResponseError))
                }
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        DetailUiState.Loading
    )

}

sealed interface DetailUiState {
    @Stable
    data class Success(val cat: Cat) : DetailUiState
    data class Error(val error: NetworkException) : DetailUiState
    data object Loading : DetailUiState
}
