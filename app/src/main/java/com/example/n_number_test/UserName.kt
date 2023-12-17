package com.example.n_number_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.n_number_test.databinding.ActivityUserNameBinding

class UserName : AppCompatActivity() {

    private lateinit var binding:ActivityUserNameBinding
    private var userName:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val languageSpinner: Spinner = findViewById(R.id.languageSpinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.game_language,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter
        languageSpinner.prompt = "Select Language"

        val levelSpinner: Spinner = findViewById(R.id.levelSpinner)
        val adapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.game_levels,
            android.R.layout.simple_spinner_item
        )

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        levelSpinner.adapter = adapter2
        levelSpinner.prompt = "Select Level"

        binding.start.setOnClickListener{
            userName = binding.userName.text.toString()
            if(userName.isNotEmpty() && languageSpinner.selectedItem.toString() != "Select Language" && levelSpinner.selectedItem.toString() != "Select Level"){
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name", userName)
                intent.putExtra("language", languageSpinner.selectedItem.toString())
                intent.putExtra("level", levelSpinner.selectedItem.toString())
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
}