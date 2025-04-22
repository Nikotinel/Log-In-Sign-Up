package com.example.android_lessons

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_lessons.databinding.ActivityInputBinding
import kotlin.Boolean

class InputActivity : AppCompatActivity() {

    lateinit var binding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("Click", "Init Second Activity")

        val result: Boolean? = intent.getBooleanExtra("key", false)

        Log.d("Click", "Нажатие на кнопку $result")

        // Проверяем то, на какую кнопку нажали
        if (result == true) {

            // Кнопка Sign Up
            addUser()
        }

        else if (result == false) {

            // Кнопка LogIn
            binding.inputCreateName.visibility = View.GONE
            binding.inputCreateLog.hint = getString(R.string.inputLoginLog)
            binding.inputCreatePass.hint = getString(R.string.inputLoginPass)
            binding.buttonAdd.text = getString(R.string.buttonLog)

            logInUser()
        }
    }

    fun addUser() {

        lateinit var inputName: String
        lateinit var inputUserName: String
        lateinit var inputPass: String

        binding.buttonAdd.setOnClickListener {

            inputName = binding.inputCreateName.text.toString().trim()
            inputUserName = binding.inputCreateLog.text.toString().trim()
            inputPass = binding.inputCreatePass.text.toString().trim()

            intent.putExtra("name", inputName)
            intent.putExtra("username", inputUserName)
            intent.putExtra("password", inputPass)
            intent.putExtra("key", true)

            Log.d("Click", "Отправка данных входа")

            setResult(RESULT_OK, intent)
            finish()

        }
    }

    fun logInUser() {

        lateinit var inputUserName: String
        lateinit var inputPass: String

        binding.buttonAdd.setOnClickListener {

            inputUserName = binding.inputCreateLog.text.toString().trim()
            inputPass = binding.inputCreatePass.text.toString().trim()

            intent.putExtra("username", inputUserName)
            intent.putExtra("password", inputPass)
            intent.putExtra("key", false)

            Log.d("Click", "Отправка данных создания аккаунта")

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

