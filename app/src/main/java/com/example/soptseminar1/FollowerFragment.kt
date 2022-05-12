package com.example.soptseminar1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.soptseminar1.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private lateinit var followerAdapter: FollowerAdapter

    //https://velog.io/@hoyaho/View-Binding%EC%97%90-%EB%8C%80%ED%95%B4-%EC%95%8C%EC%95%84%EB%B3%B4%EC%9E%90-%EF%BD%9C-Android-Study
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

    private fun initFollowerRecyclerView() {
        binding.rvFollower.addItemDecoration(MyItemDecoration())
        followerAdapter = FollowerAdapter {
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("image", it.image)
                putExtra("name", it.name)
                putExtra("introduction", it.introduction)
            }
            startActivity(intent)
        }
        //여기 사진 불러오는 법... 물어보기
        val kingsamzo =
            "https://user-images.githubusercontent.com/102457618/167554911-81d86787-6f57-48b2-b4b5-035da28af156.jpg"
        binding.rvFollower.adapter = followerAdapter
        followerAdapter.followerList.addAll(
            listOf(
                FollowerData(kingsamzo, "한승현", "KINGSAMZO 대장"),
                FollowerData(kingsamzo, "박현정", "KINGSAMZO"),
                FollowerData(kingsamzo, "이영주", "KINGSAMZO"),
                FollowerData(kingsamzo, "황연진", "KINGSAMZO")
            )
        )
        followerAdapter.notifyDataSetChanged()
    }
}