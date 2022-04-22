package com.example.soptseminar1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.soptseminar1.databinding.FragmentRepositoryBinding

class RepositoryFragment : Fragment() {
    private lateinit var repositoryRecyclerView: RepositoryRecyclerView
    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRepositoryBinding.inflate(layoutInflater, container, false)
        initRepoRecyclerView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRepoRecyclerView(){
        binding.rvFollower.addItemDecoration(MyItemDecoration())
        repositoryRecyclerView = RepositoryRecyclerView()
        _binding?.rvFollower?.adapter = repositoryRecyclerView
        repositoryRecyclerView.repositoryList.addAll(
            listOf(
                RepositoryData("안드로이드 과제 레포지토리", "안드로이드 파트 과제"),
                RepositoryData("iOS 과제 레포지토리", "iOS 파트 과제"),
                RepositoryData("서버 과제 레포지토리", "서버 파트 과제"),
                RepositoryData("기획 과제 레포지토리", "기획 파트 과제"),
                RepositoryData("디자인 과제 레포지토리", "디자인 파트 과제"),
                RepositoryData("웹 과제 레포지토리", "웹 파트 과제")
            )
        )
        repositoryRecyclerView.notifyDataSetChanged()
    }
}