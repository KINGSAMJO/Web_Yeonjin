package com.example.soptseminar1.presentation.profile.follower

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.soptseminar1.util.MyItemDecoration
import com.example.soptseminar1.data.api.GithubApiCreator
import com.example.soptseminar1.data.model.response.ResponseGithubUserFollow
import com.example.soptseminar1.databinding.FragmentFollowerBinding
import com.example.soptseminar1.enqueueUtil
import com.example.soptseminar1.util.BaseFragment
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
                    304 -> Toast.makeText(requireContext(), "Not modified", Toast.LENGTH_SHORT).show()
                    401 -> Toast.makeText(requireContext(), "Requires authentication", Toast.LENGTH_SHORT).show()
                    403 -> Toast.makeText(requireContext(), "Forbidden", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

}