package com.msmlabs.outcoding.presentation.catdetail

import androidx.lifecycle.SavedStateHandle
import com.msmlabs.outcoding.MainDispatcherRule
import com.msmlabs.outcoding.data.TestCatRepository
import com.msmlabs.outcoding.domain.Either
import com.msmlabs.outcoding.domain.exception.NetworkException
import com.msmlabs.outcoding.domain.repository.CatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatDetailViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val repository: CatRepository = TestCatRepository()
    private val savedStateHandle = SavedStateHandle(mapOf("catId" to "catId"))

    private lateinit var viewModel: CatDetailViewModel

    @Before
    fun setup() {
        viewModel = CatDetailViewModel(repository, savedStateHandle)
    }

    @Test
    fun uiState_whenInitialized_thenShowLoading() = runTest {
        assertEquals(DetailUiState.Loading, viewModel.detailUiState.value)
    }

    @Test
    fun uiState_whenGetCatByIdSuccess_thenEmitSuccess() = runTest {
        val collectJob = launch { viewModel.detailUiState.collect() }

        (repository as TestCatRepository).setCatDetailResponse(Either.Success(CatDetailTestHelper.cat))
        advanceTimeBy(5_000)
        repository.getCatById("id")

        assertEquals(DetailUiState.Success(CatDetailTestHelper.cat), viewModel.detailUiState.value)

        collectJob.cancel()
    }

    @Test
    fun uiState_whenNoInternetConnection_thenEmitNetworkFailure() = runTest {
        val collectJob = launch { viewModel.detailUiState.collect() }

        (repository as TestCatRepository).setCatDetailResponse(Either.Failure(NetworkException.NetworkUnavailable))
        advanceTimeBy(5_000)
        repository.getCatById("id")

        assertEquals(
            DetailUiState.Error(NetworkException.NetworkUnavailable),
            viewModel.detailUiState.value
        )

        collectJob.cancel()
    }

    @Test
    fun uiState_whenServerError_thenEmitNetworkFailure() = runTest {
        val collectJob = launch { viewModel.detailUiState.collect() }

        (repository as TestCatRepository).setCatDetailResponse(Either.Failure(NetworkException.ServerResponseError))
        advanceTimeBy(5_000)
        repository.getCatById("id")

        assertEquals(
            DetailUiState.Error(NetworkException.ServerResponseError),
            viewModel.detailUiState.value
        )

        collectJob.cancel()
    }

    @Test
    fun uiState_whenGeneralError_thenEmitNetworkFailure() = runTest {
        val collectJob = launch { viewModel.detailUiState.collect() }

        (repository as TestCatRepository).setCatDetailResponse(Either.Failure(NetworkException.GeneralException))
        advanceTimeBy(5_000)
        repository.getCatById("id")

        assertEquals(
            DetailUiState.Error(NetworkException.GeneralException),
            viewModel.detailUiState.value
        )

        collectJob.cancel()
    }
}
