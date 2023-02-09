package com.example.myrycyly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_adapter.view.*

class Adapter (private val siswa : ArrayList<DataClass>,private val listener : onAdapterListener):
        RecyclerView.Adapter<Adapter.viewholder>() {
    class viewholder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val data = siswa[position]
        holder.view.textView.text = data.nama
        holder.view.edit.setOnClickListener {
            listener.onUpdate(data)
        }
        holder.view.hapus.setOnClickListener {
            listener.onDelete(data)
        }
        holder.view.textView.setOnClickListener {
            listener.onClick(data)
        }
    }

    override fun getItemCount(): Int = siswa.size

    fun setData(list: List<DataClass>) {
        siswa.clear()
        siswa.addAll(list)
        notifyDataSetChanged()
    }

    interface onAdapterListener {
        fun onClick (siswa : DataClass)
        fun onUpdate (siswa : DataClass)
        fun onDelete (siswa : DataClass)
    }
}
