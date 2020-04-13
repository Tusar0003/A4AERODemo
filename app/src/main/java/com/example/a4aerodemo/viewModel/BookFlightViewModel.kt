package com.example.a4aerodemo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BookFlightViewModel(application: Application) : AndroidViewModel(application) {

    var passengerNumber = MutableLiveData<Int>()
    var cabinClass = MutableLiveData<String>()
    var from = MutableLiveData<String>()
    var to = MutableLiveData<String>()
    var fromAirport = MutableLiveData<String>()
    var toAirport = MutableLiveData<String>()
    var departureDate = MutableLiveData<String>()
    var returnDate = MutableLiveData<String>()

    @Suppress("UNCHECKED_CAST")
    class BookFlightViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookFlightViewModel::class.java)) {
                return BookFlightViewModel(application) as T
            }

            throw IllegalStateException("Unknown ViewModel Class")
        }

    }
}