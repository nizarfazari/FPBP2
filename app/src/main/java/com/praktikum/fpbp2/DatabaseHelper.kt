package com.praktikum.fpbp2

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.praktikum.fpbp2.model.BookModel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class DatabaseHelper(var context:Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {



    companion object {
        private val DATABASE_NAME = "books"
        private val DATABASE_VERSION = 1
        //table name
        private val TABLE_AKUN = "akun"
        //column table
        private val COLUMN_USERNAME = "username"
        private val COLUMN_EMAIL = "email"
        private val COLUMN_PASSWORD = "password"

    }

    private val CREATE_AKUN_TABLE = ("CREATE TABLE "+ TABLE_AKUN + "(" + COLUMN_EMAIL + " TEXT PRIMARY KEY, "+ COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT)")

    //drop table akun sql query
    private val DROP_ACOUNT_TABLE = "DROP TABLE IF EXISTS $TABLE_AKUN"


    private val TABLE_BOOK = "book"


    private val COLUMN_ID_BOOK = "idBuku"
    private val COLUMN_NAMA_BOOK = "namaBuku"
    private val COLUMN_PRICE_BOOK = "hargaBuku"
    private val COLUMN_DESC_BOOK = "deskripsiBuku"
    private val COLUMN_IMAGE = "photo"




    private val CREATE_BOOK_TABLE = ("CREATE TABLE "+ TABLE_BOOK + "(" + COLUMN_ID_BOOK + " INT PRIMARY KEY, "+ COLUMN_NAMA_BOOK + " TEXT, " +COLUMN_PRICE_BOOK + " INT, " + COLUMN_DESC_BOOK + " TEXT, " + COLUMN_IMAGE + " BLOB)")

    private val DROP_BOOK_TABLE = "DROP TABLE IF EXISTS $TABLE_BOOK"


    private val TABLE_TRANS = "transaksi"
    private val COLUMN_ID_TRANS = "idTransaksi"
    private val COLUMN_TGL = "tanggal"

    //table detail transaksi
    private val TABLE_DET_TRANSACTION = "detailTrans"
    //column table detail trans
    private val COLUMN_ID_DET_TRX = "idDetailTrx"
    private val COLUMN_ID_TRX = "idTransaksi"
    private val COLUMN_ID_PESAN = "idBuku"
    private val COLUMN_HARGA_PESAN = "harga"
    private val COLUMN_JUMLAH = "jumlah"



    private val CREATE_TRANSACTION_TABLE = ("CREATE TABLE " + TABLE_TRANS + "("
            + COLUMN_ID_TRANS + " INT PRIMARY KEY, "+ COLUMN_TGL +" TEXT)")


    private val DROP_TRANSACTION_TABLE = "DROP TABLE IF EXISTS $TABLE_TRANS"


    private val CREATE_DET_TRANS_TABLE = ("CREATE TABLE " + TABLE_DET_TRANSACTION + "("
            + COLUMN_ID_DET_TRX + " INT PRIMARY KEY, "+ COLUMN_ID_TRX +" INT, "
            + COLUMN_ID_PESAN + " INT, "+ COLUMN_HARGA_PESAN + " INT, "
            + COLUMN_JUMLAH + " INT)")


    private val DROP_DET_TRANS_TABLE = "DROP TABLE IF EXISTS $TABLE_DET_TRANSACTION"

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(CREATE_AKUN_TABLE)
        p0?.execSQL(CREATE_BOOK_TABLE)
        p0?.execSQL(CREATE_TRANSACTION_TABLE)
        p0?.execSQL(CREATE_DET_TRANS_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DROP_ACOUNT_TABLE)
        p0?.execSQL(DROP_BOOK_TABLE)
        p0?.execSQL(DROP_TRANSACTION_TABLE)
        p0?.execSQL(DROP_DET_TRANS_TABLE)
        onCreate(p0)
    }

    //add new transaksi
    @SuppressLint("Range")
    fun addTransaction(){
        val dbInsert = this.writableDatabase
        val dbSelect = this.readableDatabase
        //declare var
        var lastIdTrans = 0
        var lastIdDetail = 0
        var newIdTrans = 0
        var newIdDetail = 0
        val values = ContentValues()
        //get last idTransaksi
        val cursorTrans: Cursor = dbSelect.rawQuery(
            "SELECT  * FROM $TABLE_TRANS", null)

        val cursorDetail: Cursor = dbSelect.rawQuery(
            "SELECT *  FROM $TABLE_DET_TRANSACTION", null)

        if (cursorTrans.moveToLast()) {
            lastIdTrans = cursorTrans.getInt(0) //to get id, 0 is the column index
        }

        if (cursorDetail.moveToLast()) {
            lastIdDetail = cursorDetail.getInt(0) //to get id, 0 is the column index
        }


        //set data
        newIdTrans = lastIdTrans + 1
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val tanggal = sdf.format(Date())
        //insert data transaksi
        values.put(COLUMN_ID_TRANS, newIdTrans)
        values.put(COLUMN_TGL, tanggal)
        val result = dbInsert.insert(TABLE_TRANS,null, values)
        //show message
        if (result==(0).toLong()){
            Toast.makeText(context, "Add transaction Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Add transaction Success",Toast.LENGTH_SHORT).show()
        }

        newIdDetail = lastIdDetail + 1
        var i = 0
        val values2 = ContentValues()
        while(i < TransaksiAdapter.listId.count()){
            values2.put(COLUMN_ID_DET_TRX, newIdDetail)
            values2.put(COLUMN_ID_TRX, newIdTrans)
            values2.put(COLUMN_ID_PESAN, TransaksiAdapter.listId[i])
            values2.put(COLUMN_HARGA_PESAN, TransaksiAdapter.listHarga[i])
            values2.put(COLUMN_JUMLAH, TransaksiAdapter.listJumlah[i])
            val result2 = dbInsert.insert(TABLE_DET_TRANSACTION,null, values2)
            //show message
            if (result2==(0).toLong()){
                Toast.makeText(context, "Add detail Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Add detail Success",Toast.LENGTH_SHORT).show()
            }
            newIdDetail += 1
            i+=1
        }
        dbSelect.close()
        dbInsert.close()
    }




    @SuppressLint("Range")
    fun checkData(email: String): String {
        val columns = arrayOf(COLUMN_USERNAME)
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL = ?"

        val selectionArgs = arrayOf(email)
        var name:String = ""

        val cursor = db.query(TABLE_AKUN,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if(cursor.moveToFirst()){
           name = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
        }
        cursor.close()
        db.close()
        return name
    }




    fun addAccount(email: String,username:String,password: String){
     val db = this.readableDatabase

        val values = ContentValues()
        values.put(COLUMN_EMAIL , email)
        values.put(COLUMN_USERNAME , username)
        values.put(COLUMN_PASSWORD , password)



        val result = db.insert(TABLE_AKUN,null ,values)
        if(result == (0).toLong()){
            Toast.makeText(context,"Register Failed", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"Register Success, "+ "please login using your new account ",Toast.LENGTH_SHORT).show()
        }

    }

    fun checkLogin (email:String,password:String):Boolean{
            val colums = arrayOf(COLUMN_USERNAME)
        val db = this.readableDatabase

        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"

        val selectionArgs = arrayOf(email,password)

        val cursor = db.query(TABLE_AKUN,
            colums,
            selection,
            selectionArgs,
            null,
            null,
            null
            )

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if(cursorCount > 0)
            return  true
        else
            return  false
    }


    fun AddMenu(book:BookModel){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_BOOK, book.idBuku)
        values.put(COLUMN_NAMA_BOOK,book.namaBuku)
        values.put(COLUMN_PRICE_BOOK,book.hargaBuku)
        values.put(COLUMN_DESC_BOOK,book.deskripsiBuku)

        val byteOutputStream = ByteArrayOutputStream()
        val imageIntByte:ByteArray
        book.image.compress(Bitmap.CompressFormat.JPEG,100,byteOutputStream)
        imageIntByte = byteOutputStream.toByteArray()
        values.put(COLUMN_IMAGE,imageIntByte)

        val result = db.insert(TABLE_BOOK,null,values)

        if(result == (0).toLong()){
            Toast.makeText(context,"ADD menu Failed",LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"ADD menu Success",LENGTH_SHORT).show()
        }
        db.close()


    }



@SuppressLint("Range")
fun showMenu():ArrayList<BookModel>{
    val listModel = ArrayList<BookModel>()
    val db = this.readableDatabase
    var cursor:Cursor?=null

    try {
        cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOK,null)
    }catch (se:SQLiteException){
        db.execSQL(CREATE_BOOK_TABLE)
        return ArrayList()
    }

    var idBuku:Int
    var namaBuku:String
    var hargaBuku:Int
    var descBuku:String
    var imageArray:ByteArray
    var imageBmp:Bitmap

    if(cursor.moveToFirst()){
        do{
            idBuku = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_BOOK))
            namaBuku = cursor.getString(cursor.getColumnIndex(COLUMN_NAMA_BOOK))
            hargaBuku = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE_BOOK))
            descBuku = cursor.getString(cursor.getColumnIndex(COLUMN_DESC_BOOK))


            imageArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE))
val byteArrayInputStream = ByteArrayInputStream(imageArray)
            imageBmp = BitmapFactory.decodeStream(byteArrayInputStream)
            val model = BookModel(idBuku = idBuku,namaBuku = namaBuku,hargaBuku = hargaBuku,deskripsiBuku = descBuku,image = imageBmp)
            listModel.add(model)
        }while (cursor.moveToNext())
    }
    return listModel
}





}