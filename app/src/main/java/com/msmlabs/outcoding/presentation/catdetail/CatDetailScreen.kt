package com.msmlabs.outcoding.presentation.catdetail

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.msmlabs.outcoding.R
import com.msmlabs.outcoding.domain.exception.NetworkException
import com.msmlabs.outcoding.domain.model.Breed
import com.msmlabs.outcoding.domain.model.Cat
import com.msmlabs.outcoding.presentation.shared.components.ClickableUrl
import com.msmlabs.outcoding.presentation.shared.components.ImageLoader
import com.msmlabs.outcoding.presentation.shared.components.LoadingIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun CatDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: CatDetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    onShowSnackBar: suspend (String) -> Unit,
) {
    val selectedCat by viewModel.detailUiState.collectAsStateWithLifecycle()

    CatDetailScreen(selectedCat, modifier, navigateUp, onShowSnackBar)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatDetailScreen(
    uiState: DetailUiState,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    onShowSnackBar: suspend (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { navigateUp.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        )

        when (uiState) {
            DetailUiState.Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())

            is DetailUiState.Error -> ErrorMessages(
                uiState,
                coroutineScope,
                onShowSnackBar,
                context
            )

            is DetailUiState.Success -> CatDetailsView(uiState, modifier)
        }
    }
}

@Composable
private fun ErrorMessages(
    uiState: DetailUiState.Error,
    coroutineScope: CoroutineScope,
    onShowSnackBar: suspend (String) -> Unit,
    context: Context,
) {
    LaunchedEffect(key1 = uiState.error) {
        when (uiState.error) {
            NetworkException.GeneralException -> {
                coroutineScope.launch {
                    onShowSnackBar.invoke(context.getString(R.string.general_error))
                }
            }

            NetworkException.NetworkUnavailable -> {
                coroutineScope.launch {
                    onShowSnackBar.invoke(context.getString(R.string.connection_error))
                }
            }

            NetworkException.ServerResponseError -> {
                coroutineScope.launch {
                    onShowSnackBar.invoke(context.getString(R.string.server_error))
                }
            }
        }
    }
}

@Composable
private fun CatDetailsView(
    uiState: DetailUiState.Success,
    modifier: Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        uiState.cat.run {
            ImageLoader(
                url = imageUrl,
                modifier
                    .size(400.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = breeds.first().name,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(text = breeds.first().description)

            Text(
                text = "Origin: ${breeds.first().origin}",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Life Span: ${breeds.first().lifeSpan}",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Characteristics: ${breeds.first().temperament}",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            breeds.first().wikipediaUrl?.let { wikiUrl ->
                ClickableUrl(url = wikiUrl)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CatDetailScreenPreview() {
    val cat = Cat(
        id = "dVujvBqnu",
        imageUrl = "https://cdn2.thecatapi.com/images/dVujvBqnu.jpg",
        breeds = listOf(
            Breed(
                name = "Exotic Shorthair",
                description = "The Exotic Shorthair is a gentle friendly cat that has the same personality as the Persian.",
                temperament = "Affectionate, Sweet, Loyal, Quiet, Peaceful",
                vcaHospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/exotic-shorthair",
                origin = "United States",
                lifeSpan = "12 - 15",
                wikipediaUrl = "https://en.wikipedia.org/wiki/Exotic_Shorthair"
            )
        )
    )

    CatDetailScreen(DetailUiState.Success(cat), navigateUp = {}, onShowSnackBar = {})
}
