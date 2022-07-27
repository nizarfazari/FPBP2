package com.praktikum.fpbp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)
        getSupportActionBar()?.hide()

        val btnReg : TextView = findViewById(R.id.btnMasuk)


        btnReg.setOnClickListener{
            val  intentRegister = Intent(this@LogoActivity, LoginActivity::class.java)
            startActivity(intentRegister)
        }
    }
}