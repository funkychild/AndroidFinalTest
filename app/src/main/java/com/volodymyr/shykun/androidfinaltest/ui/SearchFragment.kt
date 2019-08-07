package com.volodymyr.shykun.androidfinaltest.ui


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.volodymyr.shykun.androidfinaltest.R
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()
    private val searchAdapter: SearchAdapter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActionBar()
        setupSearchList()
        subscribeSearchResult()

    }

    private fun setupActionBar() {
        setHasOptionsMenu(true)
        val activity = (activity as AppCompatActivity)

        activity.setSupportActionBar(searchFragmentToolbar)
        activity.supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_search_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search_fragment_stop -> stopSearch()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeSearchResult() {
        viewModel.searchUrlLiveData.observe(this, Observer { newSearchList ->

            val difUtilCallback =
                SearchUrlDiffUtilCallback(searchAdapter.items, newSearchList)
            val difResult = DiffUtil.calculateDiff(difUtilCallback)
            searchAdapter.updateItems(newSearchList)
            difResult.dispatchUpdatesTo(searchAdapter)

            itemCountText.text = newSearchList.size.toString()
        })
    }

    private fun stopSearch(): Boolean {
        viewModel.stopSearch()
        return true
    }

    private fun setupSearchList() {
        searchResultList.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }
}
