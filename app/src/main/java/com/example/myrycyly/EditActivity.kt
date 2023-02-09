package com.example.myrycyly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { AppDatabase(this) }
    private var siswaId : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupListener()
        setupView()
        siswaId = intent.getIntExtra("intent_id",0)
        Toast.makeText(this,siswaId.toString(),Toast.LENGTH_SHORT).show()
    }

    private fun setupListener() {
        btn_Simpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.siswaDao().addsiswa(
                    DataClass(
                        edit_nis.id.toString().toInt(),
                        edit_nama.text.toString(),
                        edit_kelas.text.toString()
                    )
                )
                finish()
            }
        }
    }
    fun setupView(){
        val intentType = intent.getIntExtra("intent_type",0)
        when(intentType){
            Constant.TYPE_READ -> {
                btn_Update.visibility = View.GONE
                tampilid()
            }
            Constant.TYPE_UPDATE -> {
                btn_Simpan.visibility = View.GONE
                btn_Update.visibility = View.GONE
                tampilid()
            }
            Constant.TYPE_CREATE -> {
                btn_Update.visibility = View.GONE
            }
        }
    }
   private fun tampilid(){
       siswaId = intent.getIntExtra("intent_id",0)
       CoroutineScope(Dispatchers.IO).launch {
           val dda = db.siswaDao().getid(siswaId)[0]
           val nis : String = dda.nis.toString()
           edit_nama.setText(dda.nama)
           edit_nis.setText(nis)
           edit_kelas.setText(dda.kelas)
       }
   }


}