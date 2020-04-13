package com.example.a4aerodemo.view.fragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.andexert.calendarlistview.library.SimpleMonthAdapter

import com.example.a4aerodemo.R
import com.example.a4aerodemo.databinding.FragmentSelectDateBinding
import com.example.a4aerodemo.viewModel.BookFlightViewModel

/**
 * A simple [Fragment] subclass.
 */
class SelectDateFragment : Fragment() {

    private lateinit var binding: FragmentSelectDateBinding
    private lateinit var bookFlightViewModel: BookFlightViewModel

    private var isDeparture: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_select_date,
            container,
            false
        )

        isDeparture = arguments?.getBoolean("isDeparture")
        if (isDeparture!!) {
            setDeparture()
        } else {
            setReturn()
        }

        initViews()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookFlightViewModel = ViewModelProvider(
            activity!!,
            BookFlightViewModel.BookFlightViewModelFactory(context!!.applicationContext as Application)
        ).get(BookFlightViewModel::class.java)
    }

    private fun initViews() {
        binding.imageViewBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.layoutDeparture.setOnClickListener {
            isDeparture = true
            setDeparture()
        }

        binding.layoutReturn.setOnClickListener {
            isDeparture = false
            setReturn()
        }

        // Set listener first before set the selection to ensure we can track changed date range
        // This will be called when range is selected
        binding.calendarView.setOnRangeSelectedListener { startDate, endDate, startLabel, endLabel ->
//            Log.e("DATE", "$startDate $startLabel")
            binding.textViewDepartureDate.text = startLabel
            binding.textViewReturnDate.text = endLabel
            bookFlightViewModel.departureDate.value = startLabel
            bookFlightViewModel.returnDate.value = endLabel
        }

        // This will be called when only single day is selected for both SINGLE and RANGE type
        binding.calendarView.setOnStartSelectedListener { date, label ->
//            Log.e("DATE", "$startDate $label")
            if (isDeparture!!) {
                binding.textViewDepartureDate.text = label
                bookFlightViewModel.departureDate.value = label
            } else {
                binding.textViewReturnDate.text = label
                bookFlightViewModel.returnDate.value = label
            }
        }

        binding.buttonDone.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setDeparture() {
        binding.textViewDeparture.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.textViewDepartureDate.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.layoutDeparture.setBackgroundDrawable(ContextCompat.getDrawable(context!!, R.drawable.white_square_background))

        binding.textViewReturn.setTextColor(resources.getColor(android.R.color.white))
        binding.textViewReturnDate.setTextColor(resources.getColor(android.R.color.white))
        binding.layoutReturn.setBackgroundDrawable(ContextCompat.getDrawable(context!!, R.drawable.white_transparent_square_background))
    }

    private fun setReturn() {
        binding.textViewReturn.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.textViewReturnDate.setTextColor(resources.getColor(R.color.colorPrimary))
        binding.layoutReturn.setBackgroundDrawable(ContextCompat.getDrawable(context!!, R.drawable.white_square_background))

        binding.textViewDeparture.setTextColor(resources.getColor(android.R.color.white))
        binding.textViewDepartureDate.setTextColor(resources.getColor(android.R.color.white))
        binding.layoutDeparture.setBackgroundDrawable(ContextCompat.getDrawable(context!!, R.drawable.white_transparent_square_background))
    }
}
