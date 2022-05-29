package com.example.soptseminar1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.soptseminar1.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private var position = FOLLOWER
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        userInfoNetworking()
        initTransactionEvent()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun userInfoNetworking(){
        val call: Call<ResponseGithubUserInformation> = GithubApiCreator.githubApiService.fetchGithubUserInformation()

        call.enqueue(object : Callback<ResponseGithubUserInformation> {
            override fun onResponse(
                call: Call<ResponseGithubUserInformation>,
                response: Response<ResponseGithubUserInformation>
            ) {
                if(response.isSuccessful){
                    val data = response.body()
                    data?.let {
                        Glide.with(this@ProfileFragment)
                            .load(data.avatar_url)
                            .circleCrop()
                            .into(binding.myImage)
                        binding.myName.text = data.name
                        binding.myId.text = data.userId
                        binding.myBio.text = data.bio
                    }
                }
            }

            override fun onFailure(call: Call<ResponseGithubUserInformation>, t: Throwable) {
                Log.e("NetworkTest", "error:$t")
            }

        })
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