package com.example.soptseminar1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.soptseminar1.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    fun <ResponseSignIn> Call<ResponseSignIn>.enqueueUtil(
        onSuccess: (ResponseSignIn) -> Unit,
        onError: ((stateCode: Int) -> Unit)? = null
    ) {
        this.enqueue(object : Callback<ResponseSignIn>{
            override fun onResponse(
                call: Call<ResponseSignIn>,
                response: Response<ResponseSignIn>
            ) {
                if(response.isSuccessful){
                    onSuccess.invoke(response.body() ?: return)
                } else {
                    onError?.invoke(response.code())
                }
            }

            override fun onFailure(call: Call<ResponseSignIn>, t: Throwable) {
                Log.d("NetwortkTest", "error:$t")
            }
        })
    }

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

        initLoginEvent()
        signUp()
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
                Toast.makeText(this@SignInActivity, "${data.email}님 반갑습니다!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                if(!isFinishing){
                    finish()
                }
            },
            onError = {
                when(it){
                    404 -> Toast.makeText(this@SignInActivity, "이메일에 해당하는 사용자 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                    409 -> Toast.makeText(this@SignInActivity, "비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this@SignInActivity, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show()
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