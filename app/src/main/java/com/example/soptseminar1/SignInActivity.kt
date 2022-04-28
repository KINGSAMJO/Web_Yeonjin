package com.example.soptseminar1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.soptseminar1.databinding.ActivityMainBinding

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

        login()
        signUp()

    }

    private fun login(){
        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            val id = binding.idEdit.text.toString()
            val pw = binding.pwEdit.text.toString()
            if(id.isEmpty() || pw.isEmpty()){
                Toast.makeText(this, "아이디/비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
            }else {
                startActivity(intent)
            }
        }
    }

    private fun signUp(){
        binding.btnSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }

}