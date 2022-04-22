package com.example.soptseminar1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.soptseminar1.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private lateinit var followerRecyclerView: FollowerRecyclerView
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

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
        followerRecyclerView = FollowerRecyclerView{
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("name", it.name)
            intent.putExtra("introduction", it.introduction)
            startActivity(intent)
        }
        _binding?.rvFollower?.adapter = followerRecyclerView
        followerRecyclerView.followerList.addAll(
            listOf(
                FollowerData("한승현", "KINGSAMZO 대장"),
                FollowerData("박현정", "KINGSAMZO"),
                FollowerData("이영주", "KINGSAMZO"),
                FollowerData("황연진", "KINGSAMZO")
            )
        )
        followerRecyclerView.notifyDataSetChanged()
    }
}