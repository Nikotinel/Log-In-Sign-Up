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

    var name: String? = null
    var userName: String? = null
    var password: String? = null

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

                name = result.data?.getStringExtra("name")
                userName = result.data?.getStringExtra("username")
                password = result.data?.getStringExtra("password")

                val key = result.data?.getBooleanExtra("key", false)

                if (key == true) {

                    addUser(name, userName, password)
                    showScreen(name, userName, password)

                    binding.buttonExit.setOnClickListener {
                        resetActivity()
                    }
                }

                else
                    comrasionResult(userName, password)
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

    fun addUser(name: String?, userName: String?, password: String?) {

        val user: User = User(name, userName, password)
        userList.add(user)

        Log.d("Click", "User $userList")
    }

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

    fun comrasionResult(userName: String?, password: String?) = with(binding) {

        Log.d("Click", "Начало авторизации")

        if (userList.size == 0) {

            userInfo.visibility = View.VISIBLE
            textName.text = "Сначала добавьте пользователя"
        }

        else {

            userList.forEach {

                if (it.username == userName && it.password == password) {

                    Log.d("Click", "Удачная авторизация")
                    userInfo.visibility = View.VISIBLE
                    buttonExit.visibility = View.VISIBLE

                    textName.text = it.name
                    textLog.text = it.username
                    textPass.text = it.password

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
        }

    }

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

