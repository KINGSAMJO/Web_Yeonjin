package com.example.soptseminar1.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.soptseminar1.presentation.signin.SignInActivity
import com.example.soptseminar1.data.api.ServiceCreator
import com.example.soptseminar1.data.api.SoptService
import com.example.soptseminar1.data.model.request.RequestSignUp
import com.example.soptseminar1.data.model.response.ResponseSignUp
import com.example.soptseminar1.databinding.ActivitySignUpBinding
import com.example.soptseminar1.util.BaseActivity
import com.example.soptseminar1.util.enqueueUtil
import com.example.soptseminar1.util.showToast
import kotlinx.coroutines.launch
import retrofit2.Call

class SignUpActivity : BaseActivity<ActivitySignUpBinding>({
    ActivitySignUpBinding.inflate(it)
}) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEvent()
        signUpNetwork()
    }

    private fun initEvent() {
        binding.btnSignupend.setOnClickListener {
            signUpNetwork()
        }
    }

    private fun signUpNetwork() {
        val requestSignUp = RequestSignUp(
            name = binding.nameEdit.text.toString(),
            id = binding.idEdit.text.toString(),
            password = binding.pwEdit.text.toString()
        )

        lifecycleScope.launch {
            runCatching {
                ServiceCreator.soptService.postSignUp(requestSignUp)
            }.onSuccess {
                val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                intent.putExtra("edit_id", binding.idEdit.text.toString())
                intent.putExtra("edit_pw", binding.pwEdit.text.toString())
                setResult(RESULT_OK, intent)
                if (!isFinishing) {
                    finish()
                }
            }.onFailure {
                showToast("$it")
            }
        }
    }
}