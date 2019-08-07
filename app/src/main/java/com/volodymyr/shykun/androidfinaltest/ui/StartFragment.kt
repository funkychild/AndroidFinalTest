package com.volodymyr.shykun.androidfinaltest.ui


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.volodymyr.shykun.androidfinaltest.R
import com.volodymyr.shykun.androidfinaltest.databinding.FragmentStartBinding
import kotlinx.android.synthetic.main.fragment_start.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StartFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =
            FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupActionBar()
        setOnSearchButtonClickListener()
    }

    private fun setupActionBar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(startFragmentToolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_start_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_start_fragment_clear -> clearInputData()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setOnSearchButtonClickListener() {
        searchButton.setOnClickListener {
            viewModel.startSearch()
            findNavController().navigate(R.id.searchFragment)
        }
    }

    private fun clearInputData(): Boolean {
        searchingTextTIL.editText?.setText("")
        startUrlTIL.editText?.setText("")
        threadCountTIL.editText?.setText("")
        linkCountTil.editText?.setText("")
        return true
    }
}
