package com.example.languagechange.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.languagechange.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var backPressedTime: Long = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initWidget()
    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(this@MainActivity, "한 번 더 누르면 종료", Toast.LENGTH_SHORT).show()
        } else if(System.currentTimeMillis() <= backPressedTime + 2000) {
            exitProcess(0)
        }
    }

    private fun initWidget() {
        binding.btnLanguageChangeSettings.setOnClickListener {
            moveToLanguageChangeActivity()
        }
    }

    private fun moveToLanguageChangeActivity() {
        val intent = Intent(this, LanguageChangeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }
}