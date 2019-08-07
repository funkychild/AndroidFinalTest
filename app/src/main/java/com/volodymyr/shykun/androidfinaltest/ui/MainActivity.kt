package com.volodymyr.shykun.androidfinaltest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.volodymyr.shykun.androidfinaltest.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onSupportNavigateUp(): Boolean {
        viewModel.stopSearch()
        viewModel.clearData()
        onBackPressed()
        return true
    }
}
