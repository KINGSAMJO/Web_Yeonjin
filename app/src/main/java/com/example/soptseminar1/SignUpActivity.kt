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
            val getName = binding.nameEdit.text.toString()
            val getID = binding.idEdit.text.toString()
            val getPW= binding.pwEdit.text.toString()
            if(getName.isEmpty() || getID.isEmpty() || getPW.isEmpty()){
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