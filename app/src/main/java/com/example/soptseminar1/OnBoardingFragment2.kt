package com.example.soptseminar1

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.soptseminar1.databinding.FragmentOnBoarding2Binding

class OnBoardingFragment2 : BaseFragment<FragmentOnBoarding2Binding>(FragmentOnBoarding2Binding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment2_to_onBoardingFragment3)
        }
    }
}