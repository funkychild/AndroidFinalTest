package com.volodymyr.shykun.androidfinaltest.entity

data class SearchResult(
    val url: String,
    val foundUrls: List<String>,
    val isContainText: Boolean
)