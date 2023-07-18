package com.dinhpx.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dinhpx.test.databinding.ItemSimpleBinding

class TextAdapter() : RecyclerView.Adapter<ViewHolder>() {

    private val listData = mutableListOf<MainViewModel.CouData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }



    fun updateData(data: MainViewModel.CouData) {
        if (listData.any { it.position == data.position }) {
            listData[data.position] = data
            notifyItemChanged(data.position)
        } else {
            listData.add(data)
            notifyItemInserted(data.position)
        }

    }


}

class ViewHolder(private val binding: ItemSimpleBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: MainViewModel.CouData) {
        adapterPosition
        binding.textView.text = "${data.position} --- ${data.time}"
        binding.textView2.text = data.threadName
    }
}

