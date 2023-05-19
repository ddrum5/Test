package com.dinhpx.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dinhpx.test.data.ItemData
import com.dinhpx.test.databinding.ItemDataBinding
import com.dinhpx.test.databinding.ItemLoadingBinding

class LoadMoreAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val LOADING_TYPE = 1
    }

    private val mListData = mutableListOf<Any>()

    override fun getItemViewType(position: Int): Int {
        return when (mListData[position]) {
            is ItemLoading -> LOADING_TYPE
            else -> super.getItemViewType(position)
        }
    }

    override fun getItemCount() = mListData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LOADING_TYPE) {
            LoadingVH(
                ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            DataVH(
                ItemDataBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LOADING_TYPE -> (holder as LoadingVH).onBind()
            else -> (holder as DataVH).onBind(mListData[position] as ItemData)
        }
    }

    fun isLoading(): Boolean {
        return mListData.lastOrNull() is ItemLoading
    }

    fun showLoading() {
        mListData.add(ItemLoading())
        notifyItemRangeInserted(if (mListData.isEmpty()) 0 else mListData.lastIndex - 1, 1)
    }

    private fun resetData(listData: List<ItemData>) {
        mListData.clear()
        mListData.addAll(listData)
        notifyDataSetChanged()
    }


    fun addData(listData: List<ItemData>) {
        if (mListData.lastOrNull() is ItemLoading) {
            val position = mListData.lastIndex

            mListData.removeLast()
            notifyItemRangeRemoved(position, 1)
        }

        val positionAdd = mListData.lastIndex + 1

        listData.forEach { itemData ->
            mListData.add(itemData.apply {
                title = (mListData.lastIndex + 1).toString()
            })
        }

        notifyItemRangeInserted(positionAdd, listData.size)


    }


    inner class DataVH(private val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ItemData) {
            binding.textView.text = data.title
        }
    }

    class LoadingVH(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind() {

        }
    }

    class ItemLoading

}