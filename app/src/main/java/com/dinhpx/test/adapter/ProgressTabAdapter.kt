package com.dinhpx.test.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dinhpx.test.databinding.ItemTabBinding
import com.dinhpx.test.model.TabEntity
import com.dinhpx.test.utils.toPx

class ProgressTabAdapter(tabSize: Int) : RecyclerView.Adapter<ProgressTabAdapter.TabViewHolder>() {

    companion object {
        private const val PROGRESS_PAYLOAD = "PROGRESS_PAYLOAD"
        private const val SMOOTH_PROGRESS_PAYLOAD = "PROGRESS_SMOOTH_PAYLOAD"
        private const val HIDE_PAYLOAD = "HIDE_PAYLOAD"
    }

    private val mTabList = mutableListOf<TabEntity>()
    private var mCurrentTabPosition = 0

    init {
        repeat(tabSize) {
            mTabList.add(TabEntity())
        }
    }

    private val screenWidth: Int =
        Resources.getSystem().displayMetrics.widthPixels - 10.toPx() * 2 - 2.toPx() * 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        return TabViewHolder(
            ItemTabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {}
    override fun getItemCount(): Int { return mTabList.size }

    override fun onBindViewHolder(
        holder: TabViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        holder.onBind(mTabList[position], payloads)
    }


    fun getCurrentTabPosition(): Int {
        return mCurrentTabPosition
    }

    fun setCurrentTab(position: Int) {
        mCurrentTabPosition = position
        unselectTab(position)
        if (position < mTabList.lastIndex) {
            unselectTab(position + 1)
        }
        if (position > 0) {
            selectTab(position - 1)
        }
    }


    fun setTabProgress(position: Int = mCurrentTabPosition, process: Int, isSmooth: Boolean = true) {
        if (position in mTabList.indices) {
            mTabList[position].progress = process
            notifyItemChanged(position, if (isSmooth) SMOOTH_PROGRESS_PAYLOAD else PROGRESS_PAYLOAD)
        }
    }

    fun selectTab(position: Int = mCurrentTabPosition) {
        setTabProgress(position, 100, false)
    }

    fun unselectTab(position: Int) {
        notifyItemChanged(position, HIDE_PAYLOAD)
    }


    inner class TabViewHolder(private val binding: ItemTabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.layoutParams.apply {
                width = screenWidth / mTabList.size
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
    }
}

