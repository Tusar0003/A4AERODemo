package com.example.a4aerodemo.view.fragment

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.a4aerodemo.R
import com.example.a4aerodemo.databinding.FragmentCabinClassBinding
import com.example.a4aerodemo.viewModel.BookFlightViewModel

/**
 * A simple [Fragment] subclass.
 */
class CabinClassFragment : Fragment() {

    private lateinit var binding: FragmentCabinClassBinding
    private lateinit var bookFlightViewModel: BookFlightViewModel

    private var cabinClass: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cabin_class,
            container,
            false
        )

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

        binding.imageViewAdultsPlus.setOnClickListener {
            binding.textViewAdults.text = binding.textViewAdults.text.toString().toInt().plus(1).toString()
        }

        binding.imageViewAdultsMinus.setOnClickListener {
            if (binding.textViewAdults.text.toString().toInt() >= 0) {
                binding.textViewAdults.text = binding.textViewAdults.text.toString().toInt().minus(1).toString()
            }
        }

        binding.imageViewChildrenPlus.setOnClickListener {
            binding.textViewChildren.text = binding.textViewChildren.text.toString().toInt().plus(1).toString()
        }

        binding.imageViewChildrenMinus.setOnClickListener {
            if (binding.textViewChildren.text.toString().toInt() >= 0) {
                binding.textViewChildren.text = binding.textViewChildren.text.toString().toInt().minus(1).toString()
            }
        }

        binding.imageViewInfantPlus.setOnClickListener {
            binding.textViewInfant.text = binding.textViewInfant.text.toString().toInt().plus(1).toString()
        }

        binding.imageViewInfantMinus.setOnClickListener {
            if (binding.textViewInfant.text.toString().toInt() >= 0) {
                binding.textViewInfant.text = binding.textViewInfant.text.toString().toInt().minus(1).toString()
            }
        }

        binding.radioGroupCabin.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_button_economy -> {
                    cabinClass = "Economy"
                }
                R.id.radio_button_business -> {
                    cabinClass = "Business"
                }
                R.id.radio_button_any_cabin -> {
                    cabinClass = "Any Cabin"
                }
            }
        }

        binding.buttonDone.setOnClickListener {
            bookFlightViewModel.passengerNumber.value = binding.textViewAdults.text.toString().toInt()
                .plus(binding.textViewChildren.text.toString().toInt())
                .plus(binding.textViewInfant.text.toString().toInt())

            bookFlightViewModel.cabinClass.value = cabinClass

            findNavController().popBackStack()
        }
    }
}
