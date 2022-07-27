package com.praktikum.fpbp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getSupportActionBar()?.hide()
        val txtEmail: EditText = findViewById(R.id.textEmail)
        val txtPassword: EditText = findViewById(R.id.textPassword)

        val btnLogin: Button = findViewById(R.id.buttonLogin)
        val btnReg :TextView  = findViewById(R.id.btnRegister)
        val masuk :TextView  = findViewById(R.id.txtMasuk)

        masuk.setOnClickListener{
            val  intentRegister = Intent(this@LoginActivity, LogoActivity::class.java)
            startActivity(intentRegister)
        }


        btnReg.setOnClickListener{
            val  intentRegister = Intent(this@LoginActivity, Register::class.java)
            startActivity(intentRegister)
        }

        btnLogin.setOnClickListener {
            val databaseHelper = DatabaseHelper(this)

            //check data
//            val data:String = databaseHelper.checkData("nizar@gmail.com")
//            Toast.makeText(this@LoginActivity,"Result " + data,
//                Toast.LENGTH_SHORT).show()
//            if(data== null){
//                databaseHelper.addAccount("nizar@gmail.com","nizar","nizar")
//            }
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            val result:Boolean = databaseHelper.checkLogin(email,password)



            if(result == true){
                Toast.makeText(this@LoginActivity,"Login Success",
                    Toast.LENGTH_SHORT).show()
                    val  intentLogin = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intentLogin)
            }else{
                Toast.makeText(this@LoginActivity,"Login Failed Coba Lagi",
                Toast.LENGTH_SHORT).show()

            }
        }

    }




}