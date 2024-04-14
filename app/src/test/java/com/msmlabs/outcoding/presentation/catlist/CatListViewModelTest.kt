package com.msmlabs.outcoding.presentation.catlist

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
class CatListViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val repository: CatRepository = TestCatRepository()
    private lateinit var viewModel: CatListViewModel

    @Before
    fun setup() {
        viewModel = CatListViewModel(repository)
    }

    @Test
    fun uiState_whenInitialized_thenShowLoading() = runTest {
        assertEquals(ListUiState.Loading, viewModel.listUiState.value)
    }

    @Test
    fun uiState_whenGetCatListSuccessful_thenEmitSuccess() = runTest {
        val collectJob = launch { viewModel.listUiState.collect() }

        (repository as TestCatRepository).setCatListResponse(Either.Success(CatListTestHelper.catList))
        advanceTimeBy(5_000)
        repository.getCatList()

        assertEquals(ListUiState.Success(CatListTestHelper.catList), viewModel.listUiState.value)

        collectJob.cancel()
    }

    @Test
    fun uiState_whenNoInternetConnection_thenEmitNetworkFailure() = runTest {
        val collectJob = launch { viewModel.listUiState.collect() }

        (repository as TestCatRepository).setCatListResponse(Either.Failure(NetworkException.NetworkUnavailable))
        advanceTimeBy(5_000)
        repository.getCatList()

        assertEquals(
            ListUiState.Error(NetworkException.NetworkUnavailable),
            viewModel.listUiState.value
        )

        collectJob.cancel()
    }

    @Test
    fun uiState_whenServerError_thenEmitNetworkFailure() = runTest {
        val collectJob = launch { viewModel.listUiState.collect() }

        (repository as TestCatRepository).setCatListResponse(Either.Failure(NetworkException.ServerResponseError))
        advanceTimeBy(5_000)
        repository.getCatList()

        assertEquals(
            ListUiState.Error(NetworkException.ServerResponseError),
            viewModel.listUiState.value
        )

        collectJob.cancel()
    }

    @Test
    fun uiState_whenGeneralError_thenEmitNetworkFailure() = runTest {
        val collectJob = launch { viewModel.listUiState.collect() }

        (repository as TestCatRepository).setCatListResponse(Either.Failure(NetworkException.GeneralException))
        advanceTimeBy(5_000)
        repository.getCatList()

        assertEquals(
            ListUiState.Error(NetworkException.GeneralException),
            viewModel.listUiState.value
        )

        collectJob.cancel()
    }
}
