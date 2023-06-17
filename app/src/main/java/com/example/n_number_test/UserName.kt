package com.example.n_number_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.n_number_test.databinding.ActivityUserNameBinding

class UserName : AppCompatActivity() {

    private lateinit var binding:ActivityUserNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener{
            val name = binding.userName.text.toString()
            if(name.isNotEmpty()){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Please enter user name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}