package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FourthActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("TODO APP")
        val tvTwo = findViewById<TextView>(R.id.tvTwo)
        val tvOne = findViewById<TextView>(R.id.tvOne)
        tvOne.text = intent.getStringExtra("main")
        tvTwo.text = intent.getStringExtra("date")

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun back(view: android.view.View) {
        onBackPressed()

    }

}