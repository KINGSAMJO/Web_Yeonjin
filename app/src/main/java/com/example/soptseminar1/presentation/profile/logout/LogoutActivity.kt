package com.example.soptseminar1.presentation.profile.logout

import android.os.Bundle
import com.example.soptseminar1.data.api.SOPTSharedPreferences
import com.example.soptseminar1.databinding.ActivityLogoutBinding
import com.example.soptseminar1.util.BaseActivity
import com.example.soptseminar1.util.showToast

class LogoutActivity : BaseActivity<ActivityLogoutBinding>({
    ActivityLogoutBinding.inflate(it) }) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logoutBtnClink()
    }

    private fun logoutBtnClink() {
        binding.ivLogout.setOnClickListener {
            SOPTSharedPreferences.setLogout(this)
            showToast("자동 로그인이 해제되었습니다.")
        }
    }
}