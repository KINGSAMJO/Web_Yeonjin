package com.example.soptseminar1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.soptseminar1.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initEvent()

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

        call.enqueue(object : Callback<ResponseSignUp>{
            override fun onResponse(
                call: Call<ResponseSignUp>,
                response: Response<ResponseSignUp>
            ) {
                if(response.isSuccessful){
                    when(response.code()){
                        201 -> Toast.makeText(this@SignUpActivity,"회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                } else {
                    when(response.code()){
                        409 -> Toast.makeText(this@SignUpActivity, "이미 존재하는 유저입니다.", Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this@SignUpActivity, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseSignUp>, t: Throwable) {
                Log.e("NetworkTest", "error:$t")
            }
        })
    }

    /*private fun signUpEnd(){
        binding.btnSignupend.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            val name = binding.nameEdit.text.toString()
            val id = binding.idEdit.text.toString()
            val pw = binding.pwEdit.text.toString()
            if(name.isEmpty() || id.isEmpty() || pw.isEmpty()){
                Toast.makeText(this, "입력하지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
            }else{
                intent.putExtra("edit_id", binding.idEdit.text.toString())
                intent.putExtra("edit_pw", binding.pwEdit.text.toString())
                setResult(RESULT_OK, intent)
                if(!isFinishing){
                    finish()
                }
            }
        }
    }*/
}