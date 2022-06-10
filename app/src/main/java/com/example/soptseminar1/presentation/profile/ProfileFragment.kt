package com.example.soptseminar1.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.soptseminar1.R
import com.example.soptseminar1.data.api.GithubApiCreator
import com.example.soptseminar1.data.model.response.ResponseGithubUserInformation
import com.example.soptseminar1.databinding.FragmentProfileBinding
import com.example.soptseminar1.enqueueUtil
import com.example.soptseminar1.presentation.profile.follower.FollowerFragment
import com.example.soptseminar1.presentation.profile.logout.LogoutActivity
import com.example.soptseminar1.presentation.profile.repository.RepositoryFragment
import com.example.soptseminar1.util.BaseFragment
import retrofit2.Call

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private var position = FOLLOWER

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goLogoutMenu()
        userInfoNetworking()
        initTransactionEvent()
    }

    private fun goLogoutMenu(){
        binding.ivMenu.setOnClickListener {
            val intent = Intent(requireContext(), LogoutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun userInfoNetworking() {
        val call: Call<ResponseGithubUserInformation> =
            GithubApiCreator.githubApiService.fetchGithubUserInformation()

        call.enqueueUtil(
            onSuccess = {
                it.let {
                    Glide.with(this)
                        .load(it.avatar_url)
                        .circleCrop()
                        .into(binding.myImage)
                    binding.myName.text = it.name
                    binding.myId.text = it.userId
                }
            },
            onError = {
                Toast.makeText(requireContext(), "불러오기에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun initTransactionEvent() {
        val followerfragment = FollowerFragment()
        val repositoryfragment = RepositoryFragment()

        childFragmentManager.beginTransaction()
            .add(R.id.main_fragment, followerfragment)
            .commit()

        binding.btnFollower.isSelected = true
        binding.btnFollower.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            when (position) {
                REPOSITORY -> {
                    binding.btnFollower.isSelected = true
                    binding.btnRepo.isSelected = false
                    transaction.replace(R.id.main_fragment, followerfragment)
                    position = FOLLOWER
                }
            }
            transaction.commit()
        }

        binding.btnRepo.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            when (position) {
                FOLLOWER -> {
                    binding.btnRepo.isSelected = true
                    binding.btnFollower.isSelected = false
                    transaction.replace(R.id.main_fragment, repositoryfragment)
                    position = REPOSITORY
                }
            }
            transaction.commit()
        }
    }

    companion object {
        const val FOLLOWER = 1
        const val REPOSITORY = 2
    }
}