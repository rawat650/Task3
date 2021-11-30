package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Entity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), Adapter.ClickInterface {

    var list = ArrayList<CardInfo>()
    lateinit var text1: TextView
    lateinit var recycler_view: RecyclerView
    lateinit var adapter: Adapter
    lateinit var database: DialogDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Room.databaseBuilder(
            applicationContext, DialogDatabase::class.java, "To_Do"
        ).build()
        initViews()
//        initAdapter()
        getData()
    }

    private fun initViews() {
        text1 = findViewById<TextView>(R.id.text1);
        text1.setOnClickListener {
            openDialog()
        }
    }

    private fun initAdapter() {
        // getData()
        recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = Adapter(list, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun add(item: android.view.MenuItem) {
        openDialog()
    }

    fun openDialog() {
        val View = View.inflate(this, R.layout.custom_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(View)
        val dialog = builder.create()
        val etDate = View.findViewById<EditText>(R.id.etDate)
        val etTitle = View.findViewById<EditText>(R.id.etTitle)
        val btnSave = View.findViewById<Button>(R.id.btnSave)

        etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                    etDate?.setText("$mDay/${mMonth + 1}/$mYear")

                },
                year,
                month,
                day
            )
            datePicker.show()
        }

        btnSave?.setOnClickListener {
            if (validate(etDate, etTitle)) {
                // value list
                list.add(CardInfo(etTitle?.text.toString(), etDate?.text.toString()))
//                adapter.addItems(CardInfo(etTitle?.text.toString(), etDate?.text.toString()))
                GlobalScope.launch {
                    database.dataDialog()
                        .insert(DialogData(0, etTitle.text.toString(), etDate.text.toString()))

                }


                if (list.isNotEmpty()) {
                    text1.visibility = android.view.View.GONE
                    recycler_view.visibility = android.view.View.VISIBLE
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    fun validate(etDate: EditText?, etTitle: EditText?): Boolean {
        if (etTitle?.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show()
            return false
        } else if (etDate?.text.toString().isEmpty()) {
            Toast.makeText(this, "Enter Date", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun OnClick(item: CardInfo) {
        val intent = Intent(this, FourthActivity::class.java)
        intent.putExtra("main", item.title)
        intent.putExtra("date", item.date)
        startActivity(intent)
    }

    fun getData() {
        Thread {
            database.dataDialog().getData().forEachIndexed { index, entity ->
                list.add(CardInfo(entity.title, entity.date))
//                Log.d("To_do", "${list.size}")
            }
            initAdapter()
            text1.visibility = android.view.View.GONE
            recycler_view.visibility = android.view.View.VISIBLE
        }.start()
    }
}


