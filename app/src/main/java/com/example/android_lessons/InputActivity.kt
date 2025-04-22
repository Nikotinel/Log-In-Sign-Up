package com.example.android_lessons

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_lessons.databinding.ActivityInputBinding
import com.example.android_lessons.databinding.ActivityMainBinding
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
        showScreen(result)
    }

    override fun onStart() {
        super.onStart()

        val result: Boolean? = intent.getBooleanExtra("key", false)
        loginOrSignup(result)
    }

    fun showScreen(result: Boolean?) {

        if (result == false) {

            binding.inputCreateName.visibility = View.GONE
            binding.inputCreateLog.hint = getString(R.string.inputLoginLog)
            binding.inputCreatePass.hint = getString(R.string.inputLoginPass)
            binding.buttonAdd.text = getString(R.string.buttonLog)
        }
    }

    fun loginOrSignup (result: Boolean?) {

        val intent = Intent()

        lateinit var inputName: String
        lateinit var inputUserName: String
        lateinit var inputPass: String

        if (result == false) {

            binding.buttonAdd.setOnClickListener {

                inputUserName = binding.inputCreateLog.text.toString().trim()
                inputPass = binding.inputCreatePass.text.toString().trim()

                intent.putExtra("username", inputUserName)
                intent.putExtra("password", inputPass)
                intent.putExtra("key", false)

                Log.d("Click", "Отправка данных входа")

                setResult(RESULT_OK, intent)
                finish()

            }
        }

        else {

            binding.buttonAdd.setOnClickListener {

                inputName = binding.inputCreateName.text.toString().trim()
                inputUserName = binding.inputCreateLog.text.toString().trim()
                inputPass = binding.inputCreatePass.text.toString().trim()

                intent.putExtra("name", inputName)
                intent.putExtra("username", inputUserName)
                intent.putExtra("password", inputPass)
                intent.putExtra("key", true)

                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}

