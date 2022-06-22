package com.example.soptseminar1.presentation.profile.logout

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.soptseminar1.data.api.SOPTSharedPreferences
import com.example.soptseminar1.databinding.ActivityLogoutBinding
import com.example.soptseminar1.presentation.signin.SignInActivity
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
            showToast("로그아웃 되었습니다.")
            val intent = Intent(this, SignInActivity::class.java)
            ActivityCompat.finishAffinity(this)
            startActivity(intent)
        }
    }
}