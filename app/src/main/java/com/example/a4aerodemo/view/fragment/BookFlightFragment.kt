package com.example.a4aerodemo.view.fragment

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.a4aerodemo.R
import com.example.a4aerodemo.databinding.FragmentBookFlightBinding
import com.example.a4aerodemo.viewModel.BookFlightViewModel

/**
 * A simple [Fragment] subclass.
 */
class BookFlightFragment : Fragment() {

    private lateinit var binding: FragmentBookFlightBinding
    private lateinit var bookFlightViewModel: BookFlightViewModel

    private var isRoundTrip = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_book_flight,
            container,
            false
        )

        initView()
        initObservers()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookFlightViewModel = ViewModelProvider(
            activity!!,
            BookFlightViewModel.BookFlightViewModelFactory(context!!.applicationContext as Application)
        ).get(BookFlightViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        if (isRoundTrip) {
            setRoundTrip()
        } else {
            setOneWay()
        }
    }

    private fun initView() {
        binding.textViewOneWay.setOnClickListener {
            isRoundTrip = false
            setOneWay()
        }

        binding.textViewRoundTrip.setOnClickListener {
            isRoundTrip = true
            setRoundTrip()
        }

        binding.textViewFrom.setOnClickListener {
            val action = BookFlightFragmentDirections.actionBookFlightFragmentToSearchFragment(true)
            findNavController().navigate(action)
        }

        binding.textViewFromAirport.setOnClickListener {
            val action = BookFlightFragmentDirections.actionBookFlightFragmentToSearchFragment(true)
            findNavController().navigate(action)
        }

        binding.textViewTo.setOnClickListener {
            if (isRoundTrip) {
                val action = BookFlightFragmentDirections.actionBookFlightFragmentToSearchFragment(false)
                findNavController().navigate(action)
            }
        }

        binding.textViewToAirport.setOnClickListener {
            if (isRoundTrip) {
                val action = BookFlightFragmentDirections.actionBookFlightFragmentToSearchFragment(false)
                findNavController().navigate(action)
            }
        }

        binding.cardViewDeparture.setOnClickListener {
            val action = BookFlightFragmentDirections.actionBookFlightFragmentToSelectDateFragment(true)
            findNavController().navigate(action)
        }

        binding.cardViewReturn.setOnClickListener {
            if (isRoundTrip) {
                val action = BookFlightFragmentDirections.actionBookFlightFragmentToSelectDateFragment(false)
                findNavController().navigate(action)
            }
        }

        binding.cardViewAnyCabin.setOnClickListener {
            findNavController().navigate(R.id.action_bookFlightFragment_to_cabinClassFragment)
        }
    }

    private fun initObservers() {
        bookFlightViewModel.cabinClass.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding.textViewCabin.text = it
            }
        })

        bookFlightViewModel.passengerNumber.observe(viewLifecycleOwner, Observer {
            if (it != 0) {
                binding.textViewTotalPassengers.text = "$it Passenger"
            }
        })

        bookFlightViewModel.from.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding.textViewFrom.text = it
            }
        })

        bookFlightViewModel.to.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding.textViewTo.text = it
            }
        })

        bookFlightViewModel.fromAirport.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding.textViewFromAirport.text = it
            }
        })

        bookFlightViewModel.toAirport.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding.textViewToAirport.text = it
            }
        })

        bookFlightViewModel.departureDate.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding.textViewDeparture.text = it
            }
        })

        bookFlightViewModel.returnDate.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding.textViewReturn.text = it
            }
        })
    }

    private fun setOneWay() {
        binding.textViewOneWay.setTextColor(resources.getColor(android.R.color.white))
        binding.textViewOneWay.setBackgroundDrawable(ContextCompat.getDrawable(context!!, R.drawable.red_circle_background))

        binding.textViewRoundTrip.setTextColor(resources.getColor(android.R.color.black))
        binding.textViewRoundTrip.setBackgroundDrawable(ContextCompat.getDrawable(context!!, R.drawable.white_circle_background))

        binding.textViewTo.text = "To"
        binding.textViewToAirport.text = "To Airport"
        binding.textViewReturn.text = "Select Date"
    }

    private fun setRoundTrip() {
        binding.textViewRoundTrip.setTextColor(resources.getColor(android.R.color.white))
        binding.textViewRoundTrip.setBackgroundDrawable(ContextCompat.getDrawable(context!!, R.drawable.red_circle_background))

        binding.textViewOneWay.setTextColor(resources.getColor(android.R.color.black))
        binding.textViewOneWay.setBackgroundDrawable(ContextCompat.getDrawable(context!!, R.drawable.white_circle_background))
    }
}
