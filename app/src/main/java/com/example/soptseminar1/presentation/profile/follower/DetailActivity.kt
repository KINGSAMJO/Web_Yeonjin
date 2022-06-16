package com.example.soptseminar1.presentation.profile.follower

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.soptseminar1.databinding.ActivityDetailBinding
import com.example.soptseminar1.util.BaseActivity

class DetailActivity : BaseActivity<ActivityDetailBinding>({
    ActivityDetailBinding.inflate(it) }) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getFollowerInfo()
    }

    private fun getFollowerInfo() {
        val image = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        Glide.with(this)
            .load(image)
            .circleCrop()
            .into(binding.detailImage)
        binding.detailName.text = name
    }
}