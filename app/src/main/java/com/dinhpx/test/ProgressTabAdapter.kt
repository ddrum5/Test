package com.dinhpx.test

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dinhpx.test.databinding.ItemTabBinding
import com.google.android.material.tabs.TabLayout

class ProgressTabAdapter(private val size: Int) :
    RecyclerView.Adapter<ProgressTabAdapter.TabViewHolder>() {

    companion object {
        private const val TIME_TAB = 8000L

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        return TabViewHolder(
            ItemTabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return size
    }


    inner class TabViewHolder(private val binding: ItemTabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind() {
            binding.progressBar.max = 100
            val timer = object : CountDownTimer(TIME_TAB, 500) {
                override fun onTick(millisUntilFinished: Long) {
                    val percent =
                        (1 - millisUntilFinished.toFloat() / TIME_TAB) * binding.progressBar.max
                    binding.progressBar.setProgress(percent.toInt(), true)
                }

                override fun onFinish() {
                    binding.progressBar.setProgress(binding.progressBar.max, true)
                }
            }
            timer.start()

        }
    }

}