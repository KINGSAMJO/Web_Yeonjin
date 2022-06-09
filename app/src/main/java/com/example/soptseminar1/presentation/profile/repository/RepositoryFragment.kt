package com.example.soptseminar1.presentation.profile.repository

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.soptseminar1.data.api.GithubApiCreator
import com.example.soptseminar1.data.model.response.ResponseGithubUserRepo
import com.example.soptseminar1.databinding.FragmentRepositoryBinding
import com.example.soptseminar1.enqueueUtil
import com.example.soptseminar1.util.MyItemDecoration
import com.example.soptseminar1.util.BaseFragment
import retrofit2.Call

class RepositoryFragment : BaseFragment<FragmentRepositoryBinding>(FragmentRepositoryBinding::inflate) {

    private lateinit var repositoryAdapter: RepositoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repoNetwork()
        initRepoRecyclerView()
    }
    private fun initRepoRecyclerView() {
        binding.rvFollower.addItemDecoration(MyItemDecoration())
        repositoryAdapter = RepositoryAdapter()
        binding.rvFollower.adapter = repositoryAdapter
    }

    private fun repoNetwork() {
        val call: Call<List<ResponseGithubUserRepo>> =
            GithubApiCreator.githubApiService.fetchGithubRepos()

        call.enqueueUtil(
            onSuccess = {
                it.let {
                    repositoryAdapter.submitList(it)
                }
            },
            onError = {
                Toast.makeText(requireContext(), "불러오기에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        )
    }
}