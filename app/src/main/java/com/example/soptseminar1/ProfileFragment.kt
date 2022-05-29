package com.example.soptseminar1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.soptseminar1.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    fun <ResponseGithubUserInformation> Call<ResponseGithubUserInformation>.enqueueUtil(
        onSuccess: (ResponseGithubUserInformation) -> Unit,
        onError: ((stateCode: Int) -> Unit)? = null
    ) {
        this.enqueue(object : Callback<ResponseGithubUserInformation> {
            override fun onResponse(
                call: Call<ResponseGithubUserInformation>,
                response: Response<ResponseGithubUserInformation>
            ) {
                if (response.isSuccessful) {
                    onSuccess.invoke(response.body() ?: return)
                } else {
                    onError?.invoke(response.code())
                }
            }

            override fun onFailure(call: Call<ResponseGithubUserInformation>, t: Throwable) {
                Log.d("NetworkTest", "error:$t")
            }
        })
    }

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
                    binding.myBio.text = it.bio
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