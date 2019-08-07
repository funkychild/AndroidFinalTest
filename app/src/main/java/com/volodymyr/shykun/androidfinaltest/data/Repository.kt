package com.volodymyr.shykun.androidfinaltest.data

import androidx.lifecycle.MutableLiveData
import com.volodymyr.shykun.androidfinaltest.add
import com.volodymyr.shykun.androidfinaltest.entity.SearchResult
import com.volodymyr.shykun.androidfinaltest.entity.SearchUrl
import com.volodymyr.shykun.androidfinaltest.entity.Status
import com.volodymyr.shykun.androidfinaltest.replace
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository {

    private lateinit var used: HashMap<String, Int>
    private lateinit var executor: ExecutorService
    private lateinit var subject: Subject<SearchResult>
    private lateinit var searchUrlListMutableLiveData: MutableLiveData<List<SearchUrl>>
    private var count = 0


    fun startSearch(
        startUrl: String,
        text: String,
        threadCount: Int,
        linkCount: Int,
        searchUrlListMutableLiveData: MutableLiveData<List<SearchUrl>>
    ) {
        this.searchUrlListMutableLiveData = searchUrlListMutableLiveData
        executor = Executors.newFixedThreadPool(threadCount)
        used = HashMap()
        subject = PublishSubject.create<SearchResult>().toSerialized()
        subject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                val searchStatus = if (it.isContainText) Status.Complete.Found else Status.Complete.NotFound
                val res = SearchUrl(it.url, searchStatus)

                val position = used[res.url]
                this.searchUrlListMutableLiveData.replace(res, position!!)
                for (url in it.foundUrls)
                    if (used[url] == null && count < linkCount && !executor.isShutdown) {
                        searchUrlListMutableLiveData.add(
                            SearchUrl(
                                url,
                                Status.Loading
                            )
                        )
                        executor.execute(SearchTask(url, text, subject))
                        used[url] = searchUrlListMutableLiveData.value!!.lastIndex
                        count++
                    }
            }.subscribe()

        val startSearchTask = SearchTask(startUrl, text, subject)
        if (!executor.isShutdown)
            executor.execute(startSearchTask)

        this.searchUrlListMutableLiveData.add(
            SearchUrl(
                startUrl,
                Status.Loading
            )
        )
        val lastIndex = searchUrlListMutableLiveData.value?.lastIndex
        if (lastIndex != null)
            used[startUrl] = lastIndex
        count++
    }

    fun stopSearch() {
        executor.shutdownNow()
        val searchUrlList = searchUrlListMutableLiveData.value
        searchUrlList?.forEachIndexed { index, searchUrl ->
            if (searchUrl.status is Status.Loading)
                searchUrlListMutableLiveData.replace(
                    SearchUrl(
                        searchUrl.url,
                        Status.Stopped
                    ), index)
        }
    }
}