package com.example.crudsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        val modeEdit = intent.hasExtra("kode") && intent.hasExtra("nama") &&
                intent.hasExtra("sks") && intent.hasExtra("sifat")

        title = if (modeEdit) "Edit Data Mata Kuliah" else " Entri Data Mata Kuliah"

        val etKdMataKuliah = findViewById<EditText>(R.id.etKdMatkul)
        val etNmMataKuliah = findViewById<EditText>(R.id.etNmMatkul)
        val spnSks = findViewById<Spinner>(R.id.spinsks)
        val rdWajib = findViewById<RadioButton>(R.id.rdwajib)
        val rdPilihan = findViewById<RadioButton>(R.id.rdpilihan)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        val sks = arrayOf(2,3,4,6)
        val adpsks = ArrayAdapter(
            this@EntryActivity,
            android.R.layout.simple_spinner_dropdown_item, sks
        )
        spnSks.adapter= adpsks

        if (modeEdit) {
            val kode = intent.getStringExtra("kode")
            val nama = intent.getStringExtra("nama")
            val nilaisks = intent.getIntExtra("sks", 0)
            val sifat = intent.getStringExtra("sifat")

            etKdMataKuliah.setText(kode)
            etNmMataKuliah.setText(nama)
            spnSks.setSelection(sks.indexOf(nilaisks))
            if (sifat == "wajib") rdWajib.isChecked = true else rdPilihan.isChecked = true
        }
        etKdMataKuliah.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if ("${etKdMataKuliah.text}".isNotEmpty()&& "${etNmMataKuliah.text}".isNotEmpty()
                &&(rdWajib.isChecked || rdPilihan.isChecked)){
                val db = DBHelper(this@EntryActivity)
                db.kdMatkul = "${etKdMataKuliah.text}"
                db.nmMatkul = "${etNmMataKuliah.text}"
                db.sks = spnSks.selectedItem as Int

                db.sifat = if (rdWajib.isChecked) "Wajib" else "Pilihan"
                if (if(!modeEdit)db.simpan() else db.ubah("${etKdMataKuliah.text}")) {
                    Toast.makeText(
                        this@EntryActivity,
                        "Data Mata Kuliah Berhasil Disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()

                } else
                    Toast.makeText(
                        this@EntryActivity,
                        "Data Mata Kuliah Gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()


            }else
                Toast.makeText(
                    this@EntryActivity,
                    "Data Mata Kuliah Tidak Boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()

        }
    }
}