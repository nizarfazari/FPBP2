package com.praktikum.fpbp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.hide()
        val fragment = BookFragment()

        //default fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_book,fragment)
            commit()
        }

    }




}