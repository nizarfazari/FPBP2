package com.praktikum.fpbp2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.praktikum.fpbp2.model.BookModel

class AddMenuActivity() : AppCompatActivity() {


    lateinit var image:ImageView
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    private fun pickImageGalerry(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            image.setImageURI(data?.data)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        getSupportActionBar()?.hide()


        image = findViewById(R.id.imageBook)
        val idBuku: EditText = findViewById(R.id.idBook)
        val namaBuku: EditText = findViewById(R.id.namaBook)
        val hargaBuku: EditText = findViewById(R.id.hargaBook)
        val deskripsiBuku: EditText = findViewById(R.id.descBook)
        val btnAddImage: Button = findViewById(R.id.buttonTambahImage)
        val btnSave: Button = findViewById(R.id.buttonTambah)

        btnAddImage.setOnClickListener{
            pickImageGalerry()
        }

    btnSave.setOnClickListener{
        val databaseHelper = DatabaseHelper(this)

        val idBuku:Int = idBuku.text.toString().toInt()
        val namaBuku = namaBuku.text.toString().trim()
        val hargaBuku:Int = hargaBuku.text.toString().toInt()
        val deskripsiBuku = deskripsiBuku.text.toString().trim()
        val bitmapDrawable:BitmapDrawable = image.drawable as BitmapDrawable
        val bitmap:Bitmap = bitmapDrawable.bitmap

        val bookModel = BookModel(idBuku,namaBuku,hargaBuku,deskripsiBuku,bitmap)
        databaseHelper.AddMenu(bookModel)


    }

    }






}


