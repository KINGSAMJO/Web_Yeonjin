package com.example.soptseminar1.presentation.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.soptseminar1.data.api.SOPTSharedPreferences
import com.example.soptseminar1.data.api.ServiceCreator
import com.example.soptseminar1.data.model.request.RequestSignIn
import com.example.soptseminar1.data.model.response.ResponseSignIn
import com.example.soptseminar1.databinding.ActivityMainBinding
import com.example.soptseminar1.util.enqueueUtil
import com.example.soptseminar1.presentation.home.HomeActivity
import com.example.soptseminar1.presentation.signup.SignUpActivity
import com.example.soptseminar1.util.showToast
import retrofit2.Call

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val edit_id = it.data?.getStringExtra("edit_id")?:""
            val edit_pw = it.data?.getStringExtra("edit_pw")?:""
            binding.idEdit.setText(edit_id)
            binding.pwEdit.setText(edit_pw)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickEvent()
        isAutoLogin()
        initLoginEvent()
        signUp()
    }

    private fun initClickEvent(){
        binding.btnLogincheck.setOnClickListener {
            binding.btnLogincheck.isSelected = !binding.btnLogincheck.isSelected
            SOPTSharedPreferences.setAutoLogin(this@SignInActivity, binding.btnLogincheck.isSelected)
        }
    }

    private fun isAutoLogin(){
        if(SOPTSharedPreferences.getAutoLogin(this@SignInActivity)){
            showToast("자동 로그인 되었습니다")
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
            finish()
        }
    }

    private fun initLoginEvent(){
        binding.btnLogin.setOnClickListener{
            loginNetwork()
        }
    }

    private fun loginNetwork(){
        val requestSignIn = RequestSignIn(
            id = binding.idEdit.text.toString(),
            password = binding.pwEdit.text.toString()
        )

        val call: Call<ResponseSignIn> = ServiceCreator.soptService.postLogin(requestSignIn)

        call.enqueueUtil(
            onSuccess = {
                val data = it.data
                showToast("${data.email}님 반갑습니다!")
                startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                if(!isFinishing){
                    finish()
                }
            },
            onError = {
                when(it){
                    404 -> showToast("이메일에 해당하는 사용자 정보가 없습니다.")
                    409 -> showToast("비밀번호가 올바르지 않습니다.")
                    else -> showToast("로그인에 실패하셨습니다.")
                }
            }
        )
    }

    private fun signUp(){
        binding.btnSignup.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }

}