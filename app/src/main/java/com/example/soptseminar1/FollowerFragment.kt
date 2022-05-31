package com.example.soptseminar1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.soptseminar1.databinding.FragmentFollowerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment : Fragment() {

    private lateinit var followerAdapter: FollowerAdapter
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        followingNetwork()
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
                    followerAdapter.followerList.addAll(
                        it.toMutableList()
                    )
                    followerAdapter.notifyDataSetChanged()
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