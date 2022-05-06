package com.example.soptseminar1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.soptseminar1.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getFollowerInfo()
        initImage()
    }

    private fun getFollowerInfo() {
        val name = intent.getStringExtra("name")
        val introduction = intent.getStringExtra("introduction")
        binding.detailName.setText(name)
        binding.detailIntroduction.setText(introduction)
    }

    private fun initImage(){
        Glide.with(this)
            .load(R.drawable.kingsamzo)
            .circleCrop()
            .into(binding.detailImage)
    }
}