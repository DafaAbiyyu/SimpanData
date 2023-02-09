package com.example.myrycyly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { AppDatabase(this) }
    lateinit var siswaAdapter : Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pindah()
        setRecyclrView()
    }
    private fun pindah() {
        buttonCreate.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }
    fun loadSiswa(){
        val sis = db.siswaDao().tampilsemua()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("MainActivity","dbResponse$sis")
            withContext(Dispatchers.Main){
                siswaAdapter.setData(sis)
            }
        }
    }
    override fun onStart() {
        super.onStart()
       loadSiswa()
    }

    private fun setRecyclrView(){
        siswaAdapter = Adapter(arrayListOf(),object : Adapter.onAdapterListener{
            override fun onClick(siswa: DataClass) {
                intentEdit(siswa.nis,Constant.TYPE_READ)
            }

            override fun onUpdate(siswa: DataClass) {
               intentEdit(siswa.nis,Constant.TYPE_UPDATE)
            }

            override fun onDelete(siswa: DataClass) {
                DeleteDialog(siswa)
                loadSiswa()
            }

        })
        listdata.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = siswaAdapter
        }
    }
   fun intentEdit(siswaId: Int,intentType: Int){
       startActivity(
           Intent(applicationContext,EditActivity::class.java)
               .putExtra("intent_id",siswaId)
               .putExtra("intent_type",intentType)
       )
   }
    private fun DeleteDialog(guu: DataClass){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin dek mau hapus ${guu.nama}?")
            setNegativeButton("Batal"){dialogInterface, i ->
                dialogInterface.dismiss()
            setPositiveButton("Hapus"){dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.siswaDao().delete(guu)
                    loadSiswa()
                }
            }
            }
            alertDialog.show()
        }
    }
}