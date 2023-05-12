package com.dinhpx.test.adapter

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dinhpx.test.databinding.ItemTabBinding

class ProgressTabAdapter(private val tabSize: Int) :
    RecyclerView.Adapter<ProgressTabAdapter.TabViewHolder>() {

    companion object {
        private const val PROGRESS_PAYLOAD = "PROGRESS_PAYLOAD"
        private const val SMOOTH_PROGRESS_PAYLOAD = "PROGRESS_SMOOTH_PAYLOAD"
        private const val HIDE_PAYLOAD = "HIDE_PAYLOAD"
    }


    private val mTabList = mutableListOf<TabEntity>()

    private val screenWidth: Int = Resources.getSystem().displayMetrics.widthPixels

    init {
        repeat(tabSize) { mTabList.add(TabEntity()) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        return TabViewHolder(
            ItemTabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {}

    override fun onBindViewHolder(
        holder: TabViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        holder.onBind(mTabList[position], payloads)
    }


    override fun getItemCount(): Int {
        return mTabList.size
    }

    fun progressTab(position: Int, process: Int, isSmooth: Boolean = true) {
        mTabList[position].progress = process
        notifyItemChanged(position, if (isSmooth) SMOOTH_PROGRESS_PAYLOAD else PROGRESS_PAYLOAD)
    }

    fun unselectTab(position: Int) {
        notifyItemChanged(position, HIDE_PAYLOAD)
    }

    fun unSelectAllTab() {
        mTabList.forEachIndexed { index, _ ->
            unselectTab(index)
        }
    }


    inner class TabViewHolder(private val binding: ItemTabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.layoutParams.apply {
                val w = screenWidth - getDPtoPX(binding.root.context, 16F) * 2
                width = w / tabSize - getDPtoPX(binding.root.context, 2F) * 2
            }
        }


        fun onBind(tabEntity: TabEntity, payloads: MutableList<Any>) {
            payloads.forEach {
                when (it.toString()) {
                    PROGRESS_PAYLOAD -> {
                        binding.progressBar.progress = tabEntity.progress
                    }

                    SMOOTH_PROGRESS_PAYLOAD -> {
                        binding.progressBar.setProgress(tabEntity.progress, true)
                    }

                    HIDE_PAYLOAD -> {
                        binding.progressBar.progress = 0
                    }
                }
            }


        }


        private fun getDPtoPX(context: Context, dp: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics
            ).toInt()
        }
    }

    class TabEntity {
        var progress = 0
    }

}
