package com.praktikum.fpbp2

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class TransaksiAdapter: RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>()  {

    companion object{
        var jumlah:Int = 0
        var listId = mutableListOf<Int>()
        var listNama = mutableListOf<String>()
        var listHarga = mutableListOf<Int>()
        var listFoto = mutableListOf<Bitmap>()
        var listJumlah = mutableListOf<Int>()

        var harga:Int = 0;
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiAdapter.TransaksiViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.card_transaction,
            parent, false)

        return TransaksiViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: TransaksiAdapter.TransaksiViewHolder, position: Int) {
        holder.idMenu.text = listId[position].toString()
        holder.namaMenu.text = listNama[position]
        holder.textHarga.text = listHarga[position].toString()
        holder.jumlah.text = listJumlah[position].toString()
        holder.foto.setImageBitmap(listFoto[position])

    }

    override fun getItemCount(): Int {
        return listId.size
    }

    inner class TransaksiViewHolder(v: View):RecyclerView.ViewHolder(v) {
        val foto:ImageView
        val idMenu:TextView
        val namaMenu:TextView
        val textHarga:TextView
        val jumlah:TextView
        val tambahQty:ImageView
        val kurangQty:ImageView
        val buttonHapus:Button
        val context = v.context

        init {
            foto = v.findViewById(R.id.imageTransaksi)
            idMenu = v.findViewById(R.id.textIdTransaksi)
            namaMenu = v.findViewById(R.id.textNamaTransaksi)
            textHarga = v.findViewById(R.id.textHargaTransaksi)
            jumlah = v.findViewById(R.id.textQtyTransaksi)
            tambahQty = v.findViewById(R.id.imageButtonPlus)
            kurangQty = v.findViewById(R.id.imageButtonMinus)
            buttonHapus = v.findViewById(R.id.buttonHapusTransaksi)

            tambahQty.setOnClickListener {
                val qty:Int = jumlah.text.toString().toInt()
                Toast.makeText(v.context,jumlah.text.toString(),Toast.LENGTH_SHORT).show()
                jumlah.text = (qty + 1).toString()
                harga += textHarga.text.toString().toInt()
                FragmentTransaction.txtOrder.text = harga.toString()
                FragmentTransaction.txtTotal.text = (harga + (harga * 0.10)).toString()
            }

            kurangQty.setOnClickListener {
                val qty:Int = jumlah.text.toString().toInt()
                if(qty > 1){
                    jumlah.text = (qty - 1).toString()
                    harga -= textHarga.text.toString().toInt()
                    FragmentTransaction.txtOrder.text = harga.toString()
                    FragmentTransaction.txtTotal.text = (harga + (harga * 0.10)).toString()
                }
            }
        }

    }

}