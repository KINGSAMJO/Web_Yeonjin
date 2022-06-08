package com.example.soptseminar1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.soptseminar1.databinding.FragmentOnBoarding3Binding

class OnBoardingFragment3 : Fragment() {
    private var _binding: FragmentOnBoarding3Binding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoarding3Binding.inflate(layoutInflater, container, false)
        goSignIn()
        getImageCrop()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goSignIn(){
        binding.btnNext.setOnClickListener {
            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun getImageCrop(){
        Glide.with(this)
            .load(R.drawable.kingsamzo)
            .circleCrop()
            .into(binding.ivGithub)
    }
}