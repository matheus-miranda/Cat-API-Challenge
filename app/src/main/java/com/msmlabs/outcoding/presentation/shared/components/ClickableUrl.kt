package com.msmlabs.outcoding.presentation.shared.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import com.msmlabs.outcoding.presentation.shared.extensions.convertToHttpUrl
import com.msmlabs.outcoding.presentation.shared.extensions.makeHyperlinkText

@Composable
fun ClickableUrl(url: String) {
    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = url.makeHyperlinkText(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        onClick = {
            url.makeHyperlinkText()
                .getStringAnnotations("URL", 0, url.length)
                .firstOrNull()?.let { stringAnnotation ->
                    val urlItem = stringAnnotation.item.convertToHttpUrl()
                    runCatching {
                        uriHandler.openUri(urlItem)
                    }
                }
        }
    )
}
