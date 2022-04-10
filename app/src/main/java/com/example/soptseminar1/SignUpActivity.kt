package com.example.soptseminar1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.soptseminar1.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUpEnd.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            var getName = binding.nameEdit.text.toString()
            var getID = binding.idEdit.text.toString()
            var getPW= binding.pwEdit.text.toString()
            if(getName.length ==0 || getID.length == 0 || getPW.length == 0){
                Toast.makeText(this, "입력하지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
            }else{
                intent.putExtra("Id", binding.idEdit.text.toString())
                intent.putExtra("Password", binding.pwEdit.text.toString())
                setResult(RESULT_OK, intent)
                if(!isFinishing){
                    finish()
                }
            }
        }
    }
}