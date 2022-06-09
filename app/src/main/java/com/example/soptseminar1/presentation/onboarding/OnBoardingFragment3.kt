package com.example.soptseminar1.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.soptseminar1.R
import com.example.soptseminar1.presentation.signin.SignInActivity
import com.example.soptseminar1.databinding.FragmentOnBoarding3Binding
import com.example.soptseminar1.util.BaseFragment

class OnBoardingFragment3 : BaseFragment<FragmentOnBoarding3Binding>(FragmentOnBoarding3Binding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goSignIn()
        getImageCrop()
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