package com.example.soptseminar1.presentation.signin

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.soptseminar1.data.api.SOPTSharedPreferences
import com.example.soptseminar1.data.api.ServiceCreator
import com.example.soptseminar1.data.api.SoptService
import com.example.soptseminar1.data.model.request.RequestSignIn
import com.example.soptseminar1.data.model.response.ResponseSignIn
import com.example.soptseminar1.databinding.ActivityMainBinding
import com.example.soptseminar1.util.enqueueUtil
import com.example.soptseminar1.presentation.home.HomeActivity
import com.example.soptseminar1.presentation.signup.SignUpActivity
import com.example.soptseminar1.util.BaseActivity
import com.example.soptseminar1.util.showToast
import kotlinx.coroutines.launch
import retrofit2.Call

class SignInActivity : BaseActivity<ActivityMainBinding>({
    ActivityMainBinding.inflate(it)
}) {

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val edit_id = it.data?.getStringExtra("edit_id") ?: ""
                val edit_pw = it.data?.getStringExtra("edit_pw") ?: ""
                binding.idEdit.setText(edit_id)
                binding.pwEdit.setText(edit_pw)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClickEvent()
        isAutoLogin()
        initLoginEvent()
        signUp()
    }

    private fun initClickEvent() {
        binding.btnLogincheck.setOnClickListener {
            binding.btnLogincheck.isSelected = !binding.btnLogincheck.isSelected
            SOPTSharedPreferences.setAutoLogin(
                this@SignInActivity,
                binding.btnLogincheck.isSelected
            )
        }
    }

    private fun isAutoLogin() {
        if (SOPTSharedPreferences.getAutoLogin(this@SignInActivity)) {
            showToast("자동 로그인 되었습니다")
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
            finish()
        }
    }

    private fun initLoginEvent() {
        binding.btnLogin.setOnClickListener {
            loginNetwork()
        }
    }

    private fun loginNetwork() {
        val requestSignIn = RequestSignIn(
            id = binding.idEdit.text.toString(),
            password = binding.pwEdit.text.toString()
        )

        lifecycleScope.launch {
            runCatching {
                ServiceCreator.soptService.postLogin(requestSignIn)
            }.onSuccess {
                val data = it.data
                showToast("${data?.email}님 반갑습니다!")
                startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                if (!isFinishing) {
                    finish()
                }
            }.onFailure {
                showToast("$it")
            }
        }
    }

    private fun signUp() {
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }

}