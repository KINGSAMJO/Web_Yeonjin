package com.example.soptseminar1.presentation.profile.logout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.soptseminar1.data.api.SOPTSharedPreferences
import com.example.soptseminar1.databinding.ActivityLogoutBinding
import com.example.soptseminar1.util.showToast

class LogoutActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logoutBtnClink()
    }

    private fun logoutBtnClink(){
        binding.ivLogout.setOnClickListener {
            SOPTSharedPreferences.setLogout(this)
            showToast("자동 로그인이 해제되었습니다.")
        }
    }
}