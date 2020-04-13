package com.example.a4aerodemo.view.fragment

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.a4aerodemo.R
import com.example.a4aerodemo.adapter.SearchAdapter
import com.example.a4aerodemo.databinding.FragmentSearchBinding
import com.example.a4aerodemo.data.SearchResult
import com.example.a4aerodemo.viewModel.BookFlightViewModel

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(), SearchAdapter.OnItemSelected {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var bookFlightViewModel: BookFlightViewModel

    private var isDeparture: Boolean? = null
    private lateinit var searchList: ArrayList<SearchResult>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )

        isDeparture = arguments?.getBoolean("isDeparture")

        initViews()
        initRecyclerView()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookFlightViewModel = ViewModelProvider(
            activity!!,
            BookFlightViewModel.BookFlightViewModelFactory(context!!.applicationContext as Application)
        ).get(BookFlightViewModel::class.java)
    }

    override fun onItemSelected(destination: String, airport: String) {
        binding.editTextSearch.setText(destination)
        if (isDeparture!!) {
            bookFlightViewModel.from.value = destination
            bookFlightViewModel.fromAirport.value = airport
        } else {
            bookFlightViewModel.to.value = destination
            bookFlightViewModel.toAirport.value = airport
        }
    }

    private fun initViews() {
        binding.imageViewBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val tempSearchList = ArrayList<SearchResult>()

                searchList.forEach { searchResult ->
                    if (!searchResult.destination.isNullOrEmpty() &&
                        searchResult.destination.contains(s.toString(), true)) {
                        tempSearchList.add(searchResult)
                    }
                }

                setAdapter(tempSearchList)
            }
        })

        binding.imageViewClear.setOnClickListener {
            binding.editTextSearch.text = null
        }
    }

    private fun initRecyclerView() {
        searchList = ArrayList()
        searchList.add(SearchResult("Dhaka, Bangladesh", "DAC, Hazrat Shahjalal Internation Airport"))
        searchList.add(SearchResult("Chattogram, Bangladesh", "CGP, Shah Amanat International Airport"))
        searchList.add(SearchResult("Barishal, Bangladesh", "BZL, Barishal Airport"))
        searchList.add(SearchResult("Mumbai, India", "Chhatrapati Shivaji International Airport"))
        searchList.add(SearchResult("Selangor, Malaysia", "Kuala Lumpur International Airport"))

        setAdapter(searchList)
    }

    private fun setAdapter(searchList: List<SearchResult>) {
        binding.recyclerViewSearchResult.layoutManager = LinearLayoutManager(context!!)
        binding.recyclerViewSearchResult.setHasFixedSize(true)
        binding.recyclerViewSearchResult.adapter = SearchAdapter(context!!, searchList, this)
    }
}
