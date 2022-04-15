package com.example.soptseminar1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.soptseminar1.databinding.ActivityMainBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val id = it.data?.getStringExtra("Id")?:""
                val password = it.data?.getStringExtra("Password")?:""
                binding.idEdit.setText(id)
                binding.pwEdit.setText(password)
            }
        }

        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            val getID = binding.idEdit.text.toString()
            val getPW = binding.pwEdit.text.toString()
            if(getID.isEmpty() || getPW.isEmpty()){
                Toast.makeText(this, "아이디/비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
            }else {
                startActivity(intent)
            }
        }

        binding.btnSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            activityResultLauncher.launch(intent)
        }

    }
}