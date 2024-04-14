package com.msmlabs.outcoding.presentation.shared.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.msmlabs.outcoding.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageLoader(
    url: String,
    modifier: Modifier = Modifier,
    cntntDescription: String? = null,
) {
    val context = LocalContext.current
    GlideSubcomposition(
        model = url,
        modifier = modifier.semantics {
            contentDescription = context.getString(R.string.cat_image)
        },
    ) {
        when (state) {
            RequestState.Loading -> CircularProgressIndicator()

            is RequestState.Success -> Image(
                painter = painter,
                contentDescription = cntntDescription,
                contentScale = ContentScale.Crop,
            )

            RequestState.Failure -> {
                Image(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = stringResource(R.string.error_loading_image)
                )
            }
        }
    }
}
