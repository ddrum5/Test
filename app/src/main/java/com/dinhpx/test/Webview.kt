package com.dinhpx.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinhpx.test.databinding.ActivityWebviewBinding

class Webview : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =  ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}