package com.example.soptseminar1.presentation.profile.follower

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.soptseminar1.util.MyItemDecoration
import com.example.soptseminar1.data.api.GithubApiCreator
import com.example.soptseminar1.data.model.response.ResponseGithubUserFollow
import com.example.soptseminar1.databinding.FragmentFollowerBinding
import com.example.soptseminar1.util.enqueueUtil
import com.example.soptseminar1.util.BaseFragment
import com.example.soptseminar1.util.showToast
import retrofit2.Call

class FollowerFragment : BaseFragment<FragmentFollowerBinding>(FragmentFollowerBinding::inflate) {

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
        val call: Call<List<ResponseGithubUserFollow>> =
            GithubApiCreator.githubApiService.fetchGithubFollowers()

        call.enqueueUtil(
            onSuccess = {
                it.let {
                    followerAdapter.submitList(it)
                }
            },
            onError = {
                when (it) {
                    304 -> requireContext().showToast("Not modified")
                    401 -> requireContext().showToast("Requires authentication")
                    403 -> requireContext().showToast("Forbidden")
                }
            }
        )
    }

}