package com.volodymyr.shykun.androidfinaltest.di

import com.volodymyr.shykun.androidfinaltest.ui.MainViewModel
import com.volodymyr.shykun.androidfinaltest.data.Repository
import com.volodymyr.shykun.androidfinaltest.ui.SearchAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { MainViewModel(get()) }

    single { Repository() }

    factory { SearchAdapter() }
}