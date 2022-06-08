package com.example.soptseminar1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.soptseminar1.databinding.FragmentRepositoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryFragment : Fragment() {

    private lateinit var repositoryAdapter: RepositoryAdapter
    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryBinding.inflate(layoutInflater, container, false)
        repoNetwork()
        initRepoRecyclerView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRepoRecyclerView() {
        binding.rvFollower.addItemDecoration(MyItemDecoration())
        repositoryAdapter = RepositoryAdapter()
        _binding?.rvFollower?.adapter = repositoryAdapter
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