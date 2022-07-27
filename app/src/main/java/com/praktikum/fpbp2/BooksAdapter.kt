package com.praktikum.fpbp2

import com.praktikum.fpbp2.model.BookModel


import android.graphics.Bitmap
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView


class BooksAdapter(private val list: ArrayList<BookModel>) : RecyclerView.Adapter<BooksAdapter.MenuViewHolder>(){

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.card_transaction, parent, false)

        return MenuViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        holder.bind(list[position])

    }

    inner class MenuViewHolder(v:View):RecyclerView.ViewHolder(v) {
        val textId : TextView
        val textNama: TextView
        val textHarga : TextView
        val imageMenu : ImageView
        val buttonShop : Button
        val context = v.context

        init {
            textId = v.findViewById(R.id.textIdTransaksi)
            textNama = v.findViewById(R.id.textNamaTransaksi)
            textHarga = v.findViewById(R.id.textHargaTransaksi)
            imageMenu = v.findViewById(R.id.imageTransaksi)
            buttonShop = v.findViewById(R.id.buttonTambah)

            buttonShop.setOnClickListener {
                TransaksiAdapter.jumlah = TransaksiAdapter.listId.count()

                val jumlahData = TransaksiAdapter.jumlah
                if(jumlahData == 0){
                    TransaksiAdapter.listId += textId.text.toString().toInt()
                    TransaksiAdapter.listNama += textNama.text.toString()
                    TransaksiAdapter.listHarga += textHarga.text.toString().toInt()
                    TransaksiAdapter.listFoto += imageMenu.drawable.toBitmap(80,80,null)
                    TransaksiAdapter.listJumlah += 1
                    TransaksiAdapter.harga = TransaksiAdapter.harga + textHarga.text.toString().toInt()
                    Toast.makeText(v.context,"Purchase Order Success", Toast.LENGTH_SHORT).show()
                }else{
                    //cek data
                    val cek = TransaksiAdapter.listId.find { data -> textId.text.toString().toInt().equals(data) }

                    if(cek == null){
                        TransaksiAdapter.listId += textId.text.toString().toInt()
                        TransaksiAdapter.listNama += textNama.text.toString()
                        TransaksiAdapter.listHarga += textHarga.text.toString().toInt()
                        TransaksiAdapter.listFoto += imageMenu.drawable.toBitmap(80,80,null)
                        TransaksiAdapter.listJumlah += 1
                        TransaksiAdapter.harga = TransaksiAdapter.harga + textHarga.text.toString().toInt()
                        Toast.makeText(v.context,"Purchase Order Success", Toast.LENGTH_SHORT).show()
                    }else{
                        var indek : Int = TransaksiAdapter.listId.indexOf(textId.text.toString().toInt())
                        val qty:Int = TransaksiAdapter.listJumlah[indek] + 1
                        TransaksiAdapter.listJumlah.set(indek,qty)
                        TransaksiAdapter.harga = TransaksiAdapter.harga + TransaksiAdapter.listHarga[indek]
                        Toast.makeText(v.context,"Purchase Order Success", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        fun bind(data: BookModel){
            val id:Int = data.idBuku
            val nama:String = data.namaBuku
            val harga:Int = data.hargaBuku
            val gambar: Bitmap = data.image

            textId.text = id.toString()
            textNama.text = nama
            textHarga.text = harga.toString()
            imageMenu.setImageBitmap(gambar)
        }
    }


}