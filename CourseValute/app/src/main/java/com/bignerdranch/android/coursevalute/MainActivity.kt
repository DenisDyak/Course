package com.bignerdranch.android.coursevalute

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val LIST_VAL = "valute_list"

class MainActivity : AppCompatActivity() {

    private lateinit var mService: RetrofitServices
    private lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ValuteAdapter
    lateinit var recyclerList: RecyclerView
    lateinit var valuteList: ListJson
    private lateinit var convertButton: Button
    private lateinit var reload: ImageButton
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerList = findViewById(R.id.recyclerView)
        convertButton = findViewById(R.id.convertButton)
        reload = findViewById(R.id.reload_button)

        mService = Common.retrofitService
        recyclerList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerList.layoutManager = layoutManager

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "valute-list.db"
        ).build()

        GlobalScope.launch {
            if (db.Daoz().getCount() == 0) {
                getAllValuteList()
                delay(3000)
                db.Daoz().insert(valuteList.Valute.values.toList())
            } else {
                val x: List<Valute> = db.Daoz().getValute()
                valuteList = ListJson(Valute = (x.map { it.charCode to it }.toMap()))
                adapter = ValuteAdapter(valuteList.Valute.values.toList())
                adapter.notifyDataSetChanged()
                recyclerList.adapter = adapter
            }
        }

        reloadTime()

        reload.setOnClickListener {
            reload()
        }

        convertButton.setOnClickListener {
            val intent = Intent(this, Converter::class.java)
            intent.putExtra(LIST_VAL, valuteList)
            startActivity(intent)
        }
    }

    private fun reloadTime(){
        Handler(Looper.getMainLooper()).postDelayed({
            reload()
        }, 60000)
    }

    private fun reload(){
        GlobalScope.launch {
            getAllValuteList()
            delay(3000)
            db.Daoz().update(valuteList.Valute.values.toList())
        }
    }

    private fun getAllValuteList() {
        mService.getValuteList().enqueue(object : Callback<ListJson> {
            override fun onFailure(call: Call<ListJson>, t: Throwable) {
            }

            override fun onResponse(call: Call<ListJson>, response: Response<ListJson>) {
                valuteList = response.body()!!
                adapter = ValuteAdapter(valuteList.Valute.values.toList())
                adapter.notifyDataSetChanged()
                recyclerList.adapter = adapter
            }
        })
    }
}