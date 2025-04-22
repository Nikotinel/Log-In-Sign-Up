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
import com.example.android_lessons.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>

    var userList: MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("Click", "User $userList")

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK) {

                var name: String? = result.data?.getStringExtra("name")
                var userName: String? = result.data?.getStringExtra("username")
                var password: String? = result.data?.getStringExtra("password")

                val key = result.data?.getBooleanExtra("key", false)

                if (key == true) {

                    addUser(name, userName, password)
                    showScreen(name, userName, password)

                    binding.buttonExit.setOnClickListener {
                        resetActivity()
                    }
                }

                else
                    compareResult(userName, password)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        binding.buttonSign.setOnClickListener {

            Log.d("Click", "Кнопка регистрация")
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra("key", true)
            setResult(RESULT_OK, intent)
            launcher.launch(intent)
        }

        binding.buttonLog.setOnClickListener {

            Log.d("Click", "Кнопка вход")
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra("key", false)
            setResult(RESULT_OK, intent)
            launcher.launch(intent)
        }
    }


    // Добавление пользователя в список
    fun addUser(name: String?, userName: String?, password: String?) {

        val user: User = User(name, userName, password)
        userList.add(user)

        Log.d("Click", "User $userList")
    }

    // Отображение данных о пользователе если он найден/создан
    fun showScreen(name: String?, userName: String?, password: String?) = with(binding) {

        Log.d("Click", "Получили true")

        userInfo.visibility = View.VISIBLE
        buttonExit.visibility = View.VISIBLE

        textName.text = name
        textLog.text = userName
        textPass.text = password

        buttonSign.visibility = View.GONE
        buttonLog.visibility = View.GONE
    }

    // Проверка авторизации
    fun compareResult(userName: String?, password: String?) = with(binding) {

        Log.d("Click", "Начало авторизации")

        val user: User? = userList.find { it.username == userName && it.password == password }

        if (user != null) {

            Log.d("Click", "Удачная авторизация")
            userInfo.visibility = View.VISIBLE
            buttonExit.visibility = View.VISIBLE

            textName.text = user.name
            textLog.text = user.username
            textPass.text = user.password

            buttonSign.visibility = View.GONE
            buttonLog.visibility = View.GONE

            buttonExit.setOnClickListener {
                resetActivity()
            }
        }

        else {

            userInfo.visibility = View.VISIBLE
            textName.text = "Пользователь не найден"

        }
    }

    // Очищаем экран
    fun resetActivity() = with(binding) {

        userInfo.visibility = View.GONE
        buttonExit.visibility = View.GONE

        textName.text = ""
        textLog.text = ""
        textPass.text = ""

        buttonSign.visibility = View.VISIBLE
        buttonLog.visibility = View.VISIBLE

    }
}

