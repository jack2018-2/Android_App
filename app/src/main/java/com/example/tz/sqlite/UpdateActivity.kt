package com.example.tz.sqlite

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tz.R


class UpdateActivity : AppCompatActivity() {
    var title_input: EditText? = null
    var author_input: EditText? = null
    var pages_input: EditText? = null
    var delete_button: Button? = null
    var id: String? = null
    var title: String? = null
    var author: String? = null
    var pages: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title_input = findViewById(R.id.title_input2)
        author_input = findViewById(R.id.author_input2)
        pages_input = findViewById(R.id.pages_input2)
        delete_button = findViewById(R.id.delete_button)

        //First we call this
        andSetIntentData()

        //Set actionbar title after getAndSetIntentData method
        //val ab = supportActionBar
        //ab?.title = title
        delete_button?.setOnClickListener { confirmDialog() }
    }
    //Getting Data from Intent

    //Setting Intent Data
    private fun andSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
                    intent.hasExtra("author") && intent.hasExtra("pages")) { //Getting Data from Intent
            id = intent.getStringExtra("id")
            title = intent.getStringExtra("title")
            author = intent.getStringExtra("author")
            pages = intent.getStringExtra("pages")
            //Toast.makeText(this, "$id $title $author $pages", Toast.LENGTH_SHORT).show()

            //Setting Intent Data
            title_input?.setText(title)
            author_input?.setText(author)
            pages_input?.setText(pages)
            Log.d("stev", "$title $author $pages")
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete $title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton("Yes") { dialogInterface, i ->
            val myDB = MyDatabaseHelper(this@UpdateActivity)
            myDB.deleteOneRow(id!!)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface, i -> }
        builder.create().show()
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
                if (title_input?.text.toString().trim().isNotEmpty() &&
                        author_input?.text.toString().trim().isNotEmpty()
                        && pages_input?.text.toString().trim().isNotEmpty()
                ) {
                    val myDB = MyDatabaseHelper(this@UpdateActivity)
                    title = title_input?.text.toString().trim { it <= ' ' }
                    author = author_input?.text.toString().trim { it <= ' ' }
                    pages = pages_input?.text.toString().trim { it <= ' ' }
                    myDB.updateData(id!!, title, author, pages)
                    finish()
                    true
                } else {
                    Toast.makeText(this, "Not valid data!", Toast.LENGTH_SHORT).show()
                    false
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}