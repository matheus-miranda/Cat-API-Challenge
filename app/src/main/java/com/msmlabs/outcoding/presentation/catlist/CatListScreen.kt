package com.msmlabs.outcoding.presentation.catlist

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.msmlabs.outcoding.R
import com.msmlabs.outcoding.domain.exception.NetworkException
import com.msmlabs.outcoding.domain.model.Breed
import com.msmlabs.outcoding.domain.model.Cat
import com.msmlabs.outcoding.presentation.shared.components.ImageLoader
import com.msmlabs.outcoding.presentation.shared.components.LoadingIndicator
import com.msmlabs.outcoding.ui.theme.OutcodingTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun CatListRoute(
    onCatClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CatListViewModel = hiltViewModel(),
    onShowSnackBar: suspend (String) -> Unit,
) {
    val uiState by viewModel.listUiState.collectAsStateWithLifecycle()

    CatListScreen(
        uiState = uiState,
        onCatClicked = onCatClick,
        onShowSnackBar = onShowSnackBar,
        modifier = modifier
    )
}

@Composable
fun CatListScreen(
    uiState: ListUiState,
    onCatClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    onShowSnackBar: suspend (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        when (uiState) {
            ListUiState.Loading -> item { LoadingIndicator(modifier = Modifier.fillParentMaxSize()) }

            is ListUiState.Error -> item {
                ErrorMessages(uiState, coroutineScope, onShowSnackBar, context)
            }

            is ListUiState.Success -> items(uiState.catList, key = { it.id }) { cat ->
                Spacer(modifier = Modifier.padding(top = 8.dp))
                CatListItem(cat, onCatClicked, modifier)
            }
        }
    }
}

@Composable
private fun ErrorMessages(
    uiState: ListUiState.Error,
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
private fun CatListItem(
    cat: Cat,
    onCatClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Card(
        onClick = { onCatClicked.invoke(cat.id) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(
            modifier = modifier
                .height(120.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            ImageLoader(
                url = cat.imageUrl,
                cntntDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .semantics { contentDescription = context.getString(R.string.cat_image) },
            )
            Column(Modifier.padding(start = 8.dp)) {
                Text(text = cat.breeds[0].name, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(text = cat.breeds[0].description, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CatListItemPreview() {
    val cat = Cat(
        id = "dVujvBqnu",
        imageUrl = "https://cdn2.thecatapi.com/images/dVujvBqnu.jpg",
        breeds = listOf(
            Breed(
                name = "Exotic Shorthair",
                description = "The Exotic Shorthair is a gentle friendly cat that has the same personality as the Persian.",
                temperament = "Affectionate, Sweet, Loyal, Quiet, Peaceful",
                vcaHospitalsUrl = null,
                origin = "United States",
                lifeSpan = "12 - 15",
                wikipediaUrl = null
            )
        )
    )
    OutcodingTheme {
        CatListItem(cat = cat, onCatClicked = {})
    }
}
