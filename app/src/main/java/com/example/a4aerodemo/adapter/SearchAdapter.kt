package com.example.a4aerodemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.a4aerodemo.R
import com.example.a4aerodemo.data.SearchResult
import com.example.a4aerodemo.databinding.SingleSearchLayoutBinding

class SearchAdapter(
    private val context: Context,
    private val searchList: List<SearchResult>,
    private val listener: OnItemSelected
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    interface OnItemSelected {
        fun onItemSelected(destination: String, airport: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.single_search_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchList[position])

        holder.itemView.setOnClickListener {
            listener.onItemSelected(searchList[position].destination.toString(), searchList[position].airport.toString())
        }
    }

    class ViewHolder(private val itemBinding: SingleSearchLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(searchResult: SearchResult) {
            itemBinding.textViewDestination.text = searchResult.destination
            itemBinding.textViewAirportAddress.text = searchResult.airport
        }
    }
}