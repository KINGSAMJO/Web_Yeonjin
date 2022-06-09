package com.example.soptseminar1.presentation.home

import android.os.Bundle
import android.view.View
import com.example.soptseminar1.databinding.FragmentHomeBinding
import com.example.soptseminar1.util.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var followingTabViewPagerAdapter: FollowTabViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initTabLayout()
    }

    private fun initAdapter() {
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