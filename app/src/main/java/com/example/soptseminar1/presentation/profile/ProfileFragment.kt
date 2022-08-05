package com.example.soptseminar1.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.soptseminar1.R
import com.example.soptseminar1.data.api.GithubApiCreator
import com.example.soptseminar1.data.model.response.ResponseGithubUserInformation
import com.example.soptseminar1.databinding.FragmentProfileBinding
import com.example.soptseminar1.util.enqueueUtil
import com.example.soptseminar1.presentation.profile.follower.FollowerFragment
import com.example.soptseminar1.presentation.profile.logout.LogoutActivity
import com.example.soptseminar1.presentation.profile.repository.RepositoryFragment
import com.example.soptseminar1.util.BaseFragment
import com.example.soptseminar1.util.showToast
import kotlinx.coroutines.launch
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
        lifecycleScope.launch {
            runCatching {
                GithubApiCreator.githubApiService.fetchGithubUserInformation()
            }.onSuccess {
                it.let {
                    //바인딩어댑터 사용
                    Glide.with(this@ProfileFragment)
                        .load(it.avatar_url)
                        .circleCrop()
                        .into(binding.myImage)
                    binding.userinfo = it
                }
            }.onFailure {
                requireContext().showToast("$it")
            }
        }
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