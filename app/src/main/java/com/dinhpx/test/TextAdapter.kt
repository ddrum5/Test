package com.dinhpx.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dinhpx.test.databinding.ItemSimpleBinding

class TextAdapter() : RecyclerView.Adapter<ViewHolder>() {

    private val listData = mutableListOf<String>()

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

    fun setData(list: List<String>) {
        listData.clear()
        listData.addAll(list)
        notifyDataSetChanged()
    }

    fun updateData(position: Int, text: String) {
        listData[position] = text
        notifyItemChanged(position)
    }


}

class ViewHolder(private val binding: ItemSimpleBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: String) {
        binding.textView.text = data
    }
}

