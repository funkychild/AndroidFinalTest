package com.volodymyr.shykun.androidfinaltest

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<List<T>>.add(item: T) {
    val updatedItems = this.value as ArrayList
    updatedItems.add(item)
    this.value = updatedItems
}

fun <T> MutableLiveData<List<T>>.replace(item: T, position: Int) {
    val updatedItems = this.value as ArrayList
    if (updatedItems.isEmpty())
        return
    updatedItems[position] = item
    this.value = updatedItems
}


