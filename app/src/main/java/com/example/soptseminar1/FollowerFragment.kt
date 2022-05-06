package com.example.soptseminar1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.soptseminar1.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private lateinit var followerAdapter: FollowerAdapter
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        initFollowerRecyclerView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initFollowerRecyclerView(){
        binding.rvFollower.addItemDecoration(MyItemDecoration())
        followerAdapter = FollowerAdapter{
            val intent = Intent(requireContext(), DetailActivity::class.java).apply{
                putExtra("name", it.name)
                putExtra("introduction", it.introduction)
            }
            startActivity(intent)
        }
        _binding?.rvFollower?.adapter = followerAdapter
        followerAdapter.followerList.addAll(
            listOf(
                FollowerData("한승현", "KINGSAMZO 대장"),
                FollowerData("박현정", "KINGSAMZO"),
                FollowerData("이영주", "KINGSAMZO"),
                FollowerData("황연진", "KINGSAMZO")
            )
        )
        followerAdapter.notifyDataSetChanged()
    }
}