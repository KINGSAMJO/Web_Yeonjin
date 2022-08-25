package com.example.soptseminar1.presentation.profile.follower

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.soptseminar1.util.MyItemDecoration
import com.example.soptseminar1.data.api.GithubApiCreator
import com.example.soptseminar1.data.api.GithubApiService
import com.example.soptseminar1.data.api.SoptService
import com.example.soptseminar1.data.model.response.ResponseGithubUserFollow
import com.example.soptseminar1.databinding.FragmentFollowerBinding
import com.example.soptseminar1.util.enqueueUtil
import com.example.soptseminar1.util.BaseFragment
import com.example.soptseminar1.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

@AndroidEntryPoint
class FollowerFragment : BaseFragment<FragmentFollowerBinding>(FragmentFollowerBinding::inflate) {

    @Inject
    lateinit var service: GithubApiService
    private lateinit var followerAdapter: FollowerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followingNetwork()
        initFollowerRecyclerView()
    }
    private fun initFollowerRecyclerView() {
        binding.rvFollower.addItemDecoration(MyItemDecoration())
        followerAdapter = FollowerAdapter {
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("image", it.avatar_url)
                putExtra("name", it.userId)
            }
            startActivity(intent)
        }
        binding.rvFollower.adapter = followerAdapter

    }

    private fun followingNetwork() {
        lifecycleScope.launch {
            runCatching {
                service.fetchGithubFollowers()
            }.onSuccess {
                it.let {
                    followerAdapter.submitList(it)
                }
            }.onFailure {
                requireContext().showToast("$it")
            }
        }
    }
}