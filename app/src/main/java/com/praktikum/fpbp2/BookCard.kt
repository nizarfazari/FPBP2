package com.praktikum.fpbp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class BookCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_book)

        val btn_add : Button = findViewById(R.id.AddBook)

        btn_add.setOnClickListener{
            val  AddMenuActivity = Intent(this@BookCard, AddMenuActivity::class.java)
            startActivity(AddMenuActivity)
        }
    }


}