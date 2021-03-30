package com.example.tz

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.tz.sqlite.MyDatabaseHelper

class AddNewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_new, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_button -> {
                //TODO: add submit logic
                var title_input = findViewById<EditText>(R.id.title_input)
                var author_input = findViewById<EditText>(R.id.author_input)
                var pages_input = findViewById<EditText>(R.id.pages_input)

                val myDB = MyDatabaseHelper(this@AddNewActivity)
                myDB.addBook(title_input.text.toString().trim { it <= ' ' },
                        author_input.text.toString().trim { it <= ' ' },
                        Integer.valueOf(pages_input.text.toString().trim { it <= ' ' }))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}