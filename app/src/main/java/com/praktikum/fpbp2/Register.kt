package com.praktikum.fpbp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val txtEmail: EditText = findViewById(R.id.txtEmail)
        val txtUsername: EditText = findViewById(R.id.textUsername)
        val txtPassword: EditText = findViewById(R.id.txtPassword)

        val btnRegister: Button = findViewById(R.id.buttonRegister)


        btnRegister.setOnClickListener{
            val databaseHelper = DatabaseHelper(this)
            val  email:String = txtEmail.text.toString().trim()
            val username = txtUsername.text.toString().trim()
            val password = txtPassword.text.toString().trim()
            databaseHelper.addAccount(email,username,password)
            val intentLogin = Intent(this@Register,LoginActivity::class.java)
            startActivity(intentLogin)
//            val data:String = databaseHelper.checkData(email)
//
//            if(data == null){
//
//
//
//            }else{
//                Toast.makeText(this@Register,"Register failed. "+ "Your email already registered",Toast.LENGTH_SHORT).show()
//            }
        }
    }


}