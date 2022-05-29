package com.example.soptseminar1

import android.os.Bundle
import android.telecom.Call
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.soptseminar1.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var position = FOLLOWER
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        initTransactionEvent()
        initImage()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun initImage() {
        Glide.with(this)
            .load(R.drawable.pic)
            .circleCrop()
            .into(binding.myImage)
    }

    companion object {
        const val FOLLOWER = 1
        const val REPOSITORY = 2
    }
}