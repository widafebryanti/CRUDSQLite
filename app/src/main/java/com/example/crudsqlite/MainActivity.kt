package com.example.crudsqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private  lateinit var  adpmatkul: AdapterMataKuliah
    private lateinit var dataMatkul: ArrayList<MataKuliah>
    private lateinit var  lvMatkul: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvMatkul = findViewById(R.id.lvMatKul)
        linTidakAda = findViewById(R.id.linTidakAda)

        dataMatkul = ArrayList()
        adpmatkul = AdapterMataKuliah(this@MainActivity, dataMatkul)

        lvMatkul.adapter = adpmatkul

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent (this@MainActivity, EntryActivity::class.java )
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) refresh()
    }

    private  fun refresh() {
        val db = DBHelper(this@MainActivity)
        val data = db.tampil()
        repeat(dataMatkul.size) { dataMatkul.removeFirst()}
        if(data.count>0) {
            while (data.moveToNext()) {
                val matkul = MataKuliah(
                    data.getString(0),
                    data.getString(1),
                    data.getInt(2),
                    data.getString(3),
                )
                adpmatkul.add(matkul)
                adpmatkul.notifyDataSetChanged()
            }
            lvMatkul.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE

        } else {
            lvMatkul.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}