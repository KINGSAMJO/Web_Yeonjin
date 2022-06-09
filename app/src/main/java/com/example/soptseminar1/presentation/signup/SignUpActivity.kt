package com.example.soptseminar1.presentation.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.soptseminar1.presentation.signin.SignInActivity
import com.example.soptseminar1.data.api.ServiceCreator
import com.example.soptseminar1.data.model.request.RequestSignUp
import com.example.soptseminar1.data.model.response.ResponseSignUp
import com.example.soptseminar1.databinding.ActivitySignUpBinding
import com.example.soptseminar1.enqueueUtil
import retrofit2.Call

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                    409 -> Toast.makeText(this@SignUpActivity, "이미 존재하는 유저입니다.", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this@SignUpActivity, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}