package com.example.soptseminar1.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.soptseminar1.presentation.profile.ProfileFragment
import com.example.soptseminar1.R
import com.example.soptseminar1.databinding.ActivityHomeBinding
import com.example.soptseminar1.presentation.camera.CameraFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initBottomNavi()
    }

    private fun initAdapter(){
        val fragmentList = listOf(ProfileFragment(), HomeFragment(), CameraFragment())
        mainViewPagerAdapter = MainViewPagerAdapter(this)
        mainViewPagerAdapter.fragments.addAll(fragmentList)

        binding.vpMain.adapter = mainViewPagerAdapter
    }

    private fun initBottomNavi(){
        binding.vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.bnvMain.menu.getItem(position).isChecked = true
            }
        })

        binding.bnvMain.setOnItemSelectedListener {
            return@setOnItemSelectedListener when(it.itemId){
                R.id.menu_profile -> {
                    binding.vpMain.currentItem = FIRST_FRAGMENT
                    true
                }
                R.id.menu_home -> {
                    binding.vpMain.currentItem = SECOND_FRAGMENT
                    true
                }
                else -> {
                    binding.vpMain.currentItem = THIRD_FRAGMENT
                    true
                }
            }
        }
    }

    companion object {
        const val FIRST_FRAGMENT = 0
        const val SECOND_FRAGMENT = 1
        const val THIRD_FRAGMENT = 2
    }
}