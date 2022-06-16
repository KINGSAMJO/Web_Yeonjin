package com.example.soptseminar1.presentation.signup

import android.content.Intent
import android.os.Bundle
import com.example.soptseminar1.presentation.signin.SignInActivity
import com.example.soptseminar1.data.api.ServiceCreator
import com.example.soptseminar1.data.model.request.RequestSignUp
import com.example.soptseminar1.data.model.response.ResponseSignUp
import com.example.soptseminar1.databinding.ActivitySignUpBinding
import com.example.soptseminar1.util.BaseActivity
import com.example.soptseminar1.util.enqueueUtil
import com.example.soptseminar1.util.showToast
import retrofit2.Call

class SignUpActivity : BaseActivity<ActivitySignUpBinding>({
    ActivitySignUpBinding.inflate(it) }) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEvent()
        signUpNetwork()
    }

    private fun initEvent(){
        binding.btnSignupend.setOnClickListener{
            signUpNetwork()
        }
    }

    private fun signUpNetwork(){
        val requestSignUp = RequestSignUp(
            name = binding.nameEdit.text.toString(),
            id = binding.idEdit.text.toString(),
            password = binding.pwEdit.text.toString()
        )

        val call: Call<ResponseSignUp> = ServiceCreator.soptService.postSignUp(requestSignUp)

        call.enqueueUtil(
            onSuccess = {
                val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                intent.putExtra("edit_id", binding.idEdit.text.toString())
                intent.putExtra("edit_pw", binding.pwEdit.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            },
            onError = {
                when(it){
                    409 -> showToast("이미 존재하는 유저입니다.")
                    else -> showToast("회원가입에 실패하였습니다.")
                }
            }
        )
    }
}