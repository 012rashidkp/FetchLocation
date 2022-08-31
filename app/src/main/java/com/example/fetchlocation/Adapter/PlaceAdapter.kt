package com.example.fetchlocation.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchlocation.ItemClickListener
import com.example.fetchlocation.Model.Places
import com.example.fetchlocation.databinding.PlacesListBinding

class PlaceAdapter(val context: Context,val places:List<Places>,val listener: ItemClickListener):RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val binding=PlacesListBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val datas=places[position]
        with(holder){
            binding.placeName.text=datas.placeName
            itemView.setOnClickListener {
                listener.itemclick(position)

            }
        }
    }

    override fun getItemCount(): Int {
       return places.size
    }
    class MyViewHolder(val binding: PlacesListBinding):RecyclerView.ViewHolder(binding!!.root) {

    }
}