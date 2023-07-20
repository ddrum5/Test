package com.dinhpx.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dinhpx.test.databinding.ItemSimpleBinding

class TextAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val mapData = HashMap<Int, MainViewModel.CouData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mapData[position])
    }

    override fun getItemCount(): Int {
        return mapData.size
    }

    fun updateData(data :MainViewModel.CouData) {

        if (mapData.contains(data.position)) {
            mapData[data.position] = data
            notifyItemChanged(data.position)
        } else {
            mapData[data.position] = data
            notifyItemInserted(data.position)
        }

    }

}

class ViewHolder(private val binding: ItemSimpleBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: MainViewModel.CouData?) {
        binding.textView.text = data?.time
    }
}

