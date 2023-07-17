package com.dinhpx.test

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.dinhpx.test.databinding.LoadingDialogBinding
import java.text.DecimalFormat

class LoadingDialog(context: Context) : Dialog(context) {

    private val binding by lazy { LoadingDialogBinding.inflate(layoutInflater) }

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    private var timeStart = System.currentTimeMillis()



    init {
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        runnable = Runnable {
            val time = (System.currentTimeMillis() - timeStart) / 1000F
            binding.text.text = String.format("%.1f", time)
            handler.postDelayed(runnable, 200)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun show() {
        super.show()
        timeStart = System.currentTimeMillis()
        handler.post(runnable)
    }

    override fun dismiss() {
        handler.removeCallbacks(runnable)
        super.dismiss()
    }


}