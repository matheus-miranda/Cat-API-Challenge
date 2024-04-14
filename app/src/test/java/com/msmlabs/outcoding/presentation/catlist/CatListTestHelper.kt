package com.msmlabs.outcoding.presentation.catlist

import com.msmlabs.outcoding.domain.model.Cat

object CatListTestHelper {
    private val cat = Cat("id", "imageUrl", emptyList())
    val catList = listOf(cat)
}
