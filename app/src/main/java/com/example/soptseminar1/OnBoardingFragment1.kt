package com.example.soptseminar1

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.soptseminar1.databinding.FragmentOnBoarding1Binding

class OnBoardingFragment1 : BaseFragment<FragmentOnBoarding1Binding>(FragmentOnBoarding1Binding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment1_to_onBoardingFragment2)
        }
    }
}