package com.example.soptseminar1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.soptseminar1.databinding.ActivityHomeBinding
import com.example.soptseminar1.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var followingTabViewPagerAdapter: FollowTabViewPagerAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        initAdapter()
        initTabLayout()
        return binding.root
    }

    private fun initAdapter(){
        val fragmentList = listOf(FollowingTabFragment(), FollowerTabFragment())

        followingTabViewPagerAdapter = FollowTabViewPagerAdapter(this)
        followingTabViewPagerAdapter.fragments.addAll(fragmentList)
        binding.vpHome.adapter = followingTabViewPagerAdapter
    }

    private fun initTabLayout() {
        val tabLabel = listOf("팔로잉", "팔로워")

        TabLayoutMediator(binding.tabHome, binding.vpHome) { tab, position ->
            tab.text = tabLabel[position]
        }.attach()

    }
}