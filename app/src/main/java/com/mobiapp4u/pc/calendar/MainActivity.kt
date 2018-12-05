package com.mobiapp4u.pc.calendar

import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import com.mobiapp4u.pc.calendar.Adapter.CalenderAdapter
import com.mobiapp4u.pc.calendar.dabase.Database
import com.mobiapp4u.pc.calendar.model.CalenderModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {
    var cal = mutableListOf<CalenderModel>()
    var adapter:CalenderAdapter?=null
   lateinit var dBase:SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        var actvity = Database(this@MainActivity)
        var c =  actvity.dBase!!.query("event",null,null, null,
            null,null,"day_month,year_month")
        var sAdapter11 = SimpleCursorAdapter(this@MainActivity,R.layout.events_item,c,
            arrayOf("date","day_week","day_month","time","description"),
            intArrayOf(R.id.dateOfItem,R.id.day_month,R.id.date_num,R.id.time,R.id.event_desc) )
        lstView.adapter = sAdapter11


        calendarView.setOnDateChangedListener(object: OnDateSelectedListener{
            override fun onDateSelected(p0: MaterialCalendarView, p1: CalendarDay, p2: Boolean) {
              
                val dt = ""+p1.day +" - "+p1.month+" - "+p1.year
                 var dt1 = dt.trim()

//                Toast.makeText(this@MainActivity,"Selected date is: $dt1",Toast.LENGTH_SHORT).show()
                var actvity = Database(this@MainActivity)
                var c =  actvity.dBase!!.query("event",null,"date=?", arrayOf(dt1),null,null,null)
                var sAdapter = SimpleCursorAdapter(this@MainActivity,R.layout.events_item,c, arrayOf("date","day_week","day_month","time","description"),
                    intArrayOf(R.id.dateOfItem,R.id.day_month,R.id.date_num,R.id.time,R.id.event_desc) )
                    loadData(sAdapter)

                 lstView.setOnItemLongClickListener(object : AdapterView.OnItemLongClickListener{
                    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                        var alert = AlertDialog.Builder(this@MainActivity)
                        alert.setTitle("Do you want to delete event??")
                        alert.setPositiveButton("Yes",object : DialogInterface.OnClickListener{
                            override fun onClick(dialog: DialogInterface?, which: Int) {

                                var status =  actvity!!.dBase!!.delete("event","date=?", arrayOf(dt1))
                                if(status == 0){
                                    Toast.makeText(this@MainActivity,
                                        "Data Deletion is Failed",
                                        Toast.LENGTH_LONG).show()

                                }else{
                                    Toast.makeText(this@MainActivity,
                                        "Data Deletion is Success",
                                        Toast.LENGTH_LONG).show()
                                }
                                var actvity1 = Database(this@MainActivity)
                                var c1 =  actvity1.dBase!!.query("event",null,"date=?", arrayOf(dt1),null,null,null)
                                var sAdapter1 = SimpleCursorAdapter(this@MainActivity,R.layout.events_item,c1, arrayOf("day_week","day_month","time","description"),
                                    intArrayOf(R.id.day_month,R.id.date_num,R.id.time,R.id.event_desc) )
                                     loadData(sAdapter1)
                            }

                        })
                        alert.setNegativeButton("No",object :DialogInterface.OnClickListener{
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                dialog!!.dismiss()
                            }
                        })
                        alert.show()
                        return true
                    }


                })
            }

        })


        fab.setOnClickListener { view ->
            val intent = Intent(this@MainActivity,SaveEvent::class.java)
            startActivity(intent)

        }
    }

    private fun loadData(sAdapter: SimpleCursorAdapter) {
        sAdapter.notifyDataSetChanged()
        lstView.adapter = sAdapter
    }

}
