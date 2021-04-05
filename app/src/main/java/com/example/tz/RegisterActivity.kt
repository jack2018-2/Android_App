package com.example.tz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tz.ui.ui.login.LoginActivity

//TODO: fill register activity

class RegisterActivity : AppCompatActivity() {

    private var dataValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val register = findViewById<Button>(R.id.register_user)
        val username = findViewById<EditText>(R.id.register_username)
        val password = findViewById<EditText>(R.id.register_password)

        register.isEnabled = true
        register.setOnClickListener(View.OnClickListener {
            registerDataChanged(username.text.toString(), password.text.toString())
            if (dataValid){
                finish()
                startActivity(Intent(this, LoginActivity::class.java))}
        })
    }

    private fun registerDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            Toast.makeText(
                applicationContext,
                R.string.invalid_username,
                Toast.LENGTH_LONG
            ).show()
        } else if (!isPasswordValid(password)) {
            Toast.makeText(
                applicationContext,
                R.string.invalid_password,
                Toast.LENGTH_LONG
            ).show()
        } else {
            dataValid = true
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}