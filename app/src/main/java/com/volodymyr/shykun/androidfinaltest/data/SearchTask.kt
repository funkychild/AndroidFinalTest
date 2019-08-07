package com.volodymyr.shykun.androidfinaltest.data

import android.util.Log
import com.volodymyr.shykun.androidfinaltest.entity.SearchResult
import io.reactivex.subjects.Subject
import org.jsoup.Jsoup
import java.io.IOException

class SearchTask(
    private val url: String,
    private val text: String,
    private val subject: Subject<SearchResult>
) : Runnable {
    override fun run() {
        try {
            val doc = Jsoup.connect(url).get()
            val body = doc.body()?.text()
            val links = doc.select("a[href]")
            val isContainText = body?.contains(text) ?: false

            val foundLinks = arrayListOf<String>()

            for (link in links) {
                val url = link.attr("href")
                if (url.startsWith("http"))
                    foundLinks.add(url)
            }

            subject.onNext(SearchResult(url, foundLinks, isContainText))
        } catch (e: IOException) {
            Log.i("Error", e.message!!)
            subject.onNext(SearchResult(url, listOf(), false))
        }
    }
}