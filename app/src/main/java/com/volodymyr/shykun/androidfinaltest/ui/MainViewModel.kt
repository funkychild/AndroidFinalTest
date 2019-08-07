package com.volodymyr.shykun.androidfinaltest.ui

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.volodymyr.shykun.androidfinaltest.data.Repository
import com.volodymyr.shykun.androidfinaltest.entity.SearchUrl

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val searchUrlMutableLiveData = MutableLiveData<List<SearchUrl>>().apply { value = arrayListOf() }
    val searchUrlLiveData: LiveData<List<SearchUrl>> = searchUrlMutableLiveData

    val searchingTextObservableField = ObservableField<String>()
    val startUrlObservableField = ObservableField<String>()
    val maxThreadCountObservableField = ObservableField<String>()
    val maxLinkObservableField = ObservableField<String>()

    val searchButtonEnabled = ObservableField<Boolean>()

    init {
        setupStartFragmentFieldsValidation()
    }

    fun startSearch() {
        searchUrlMutableLiveData.value = arrayListOf()
        repository.startSearch(
            startUrlObservableField.get()!!,
            searchingTextObservableField.get()!!,
            maxThreadCountObservableField.get()!!.toInt(),
            maxLinkObservableField.get()!!.toInt(),
            searchUrlMutableLiveData
        )
    }

    fun stopSearch() = repository.stopSearch()

    fun clearData() {
        searchUrlMutableLiveData.value = arrayListOf()
    }

    private fun setupStartFragmentFieldsValidation() {
        val callback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                if (searchingTextObservableField.get() != null
                    && startUrlObservableField.get() != null
                    && maxThreadCountObservableField.get() != null
                    && maxLinkObservableField.get() != null
                    && searchingTextObservableField.get()!!.trim().isNotEmpty()
                    && startUrlObservableField.get()!!.trim().isNotEmpty()
                    && maxThreadCountObservableField.get()!!.trim().isNotEmpty()
                    && maxLinkObservableField.get()!!.trim().isNotEmpty()
                )
                    searchButtonEnabled.set(true)
                else
                    searchButtonEnabled.set(false)
            }
        }
        startUrlObservableField.addOnPropertyChangedCallback(callback)
        searchingTextObservableField.addOnPropertyChangedCallback(callback)
        maxThreadCountObservableField.addOnPropertyChangedCallback(callback)
        maxLinkObservableField.addOnPropertyChangedCallback(callback)
    }

}