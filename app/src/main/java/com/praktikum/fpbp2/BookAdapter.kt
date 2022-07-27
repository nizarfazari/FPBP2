package com.praktikum.fpbp2

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.praktikum.fpbp2.model.BookModel

class BookAdapter(private val list:ArrayList<BookModel>):RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
  inner class BookViewHolder(v: View):RecyclerView.ViewHolder(v){
      val textId :TextView
      val textNama :TextView
      val textHarga :TextView
      val textDesc :TextView
      val imageMenu :ImageView

      init {
        textId = v.findViewById(R.id.id_buku)
          textNama = v.findViewById(R.id.nama_judul_buku)
          textHarga = v.findViewById(R.id.harga_buku)
          textDesc = v.findViewById(R.id.deskripsi_buku)
          imageMenu = v.findViewById(R.id.foto_buku)
      }
      fun bind(data: BookModel){
          val id:Int = data.idBuku
          val namaBuku:String = data.namaBuku
          val hargaBuku:Int = data.hargaBuku
          val descBuku:String = data.deskripsiBuku
          val gambar:Bitmap = data.image

          textId.text = id.toString()
          textNama.text = namaBuku
          textHarga.text = hargaBuku.toString()
          textDesc.text = descBuku
          imageMenu.setImageBitmap(gambar)
      }
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.card_book,parent,false)

        return BookViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(list[position])
    }

}