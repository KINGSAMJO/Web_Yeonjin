package com.example.soptseminar1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.soptseminar1.databinding.FragmentProfileBinding
import retrofit2.Call

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private var position = FOLLOWER

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userInfoNetworking()
        initTransactionEvent()
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