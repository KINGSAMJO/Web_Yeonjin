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
    }

    private fun getFollowerInfo() {
        val image = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        Glide.with(this)
            .load(image)
            .circleCrop()
            .into(binding.detailImage)
        binding.detailName.setText(name)
    }
}