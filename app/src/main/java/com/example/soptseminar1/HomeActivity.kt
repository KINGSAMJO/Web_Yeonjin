package com.example.soptseminar1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.soptseminar1.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private var position = FOLLOWER
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTransactionEvent()
    }

    private fun initTransactionEvent(){
        val fragment1 = FollowerFragment()
        val fragment2 = RepositoryFragment()

        supportFragmentManager.beginTransaction().add(R.id.main_fragment, fragment1).commit()

        binding.btnFollower.setOnClickListener{
            val transaction = supportFragmentManager.beginTransaction()
            when (position) {
                REPOSITORY -> {
                    transaction.replace(R.id.main_fragment, fragment1)
                    position = FOLLOWER
                }
            }
            transaction.commit()
        }

        binding.btnRepo.setOnClickListener{
            val transaction = supportFragmentManager.beginTransaction()
            when (position) {
                FOLLOWER -> {
                    transaction.replace(R.id.main_fragment, fragment2)
                    position = REPOSITORY
                }
            }
            transaction.commit()
        }
    }
    companion object {
        const val FOLLOWER = 1
        const val REPOSITORY = 2
    }
}