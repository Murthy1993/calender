package com.mobiapp4u.pc.calendar.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mobiapp4u.pc.calendar.R
import com.mobiapp4u.pc.calendar.model.CalenderModel


class CalenderAdapter(
    var context:Context,
    var data: MutableList<CalenderModel>
): RecyclerView.Adapter<CalenderAdapter.CalViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): CalViewHolder {
        var inflater = LayoutInflater.from(context)
        var itemV = inflater.inflate(R.layout.events_item,viewGroup,false)
        return CalViewHolder(itemV)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(vh: CalViewHolder, pos: Int) {
//        vh.event.text = data.get(pos).desc
//        vh.day.text = data.get(pos).day
//        vh.date.text = data.get(pos).date
//        vh.time.text = data.get(pos).time
    }

    class  CalViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        lateinit var day:TextView
        lateinit var  date:TextView
        lateinit var time:TextView
        lateinit var event:TextView
        init {
            day = itemView.findViewById(R.id.day_month)
            date = itemView.findViewById(R.id.date_num)
            time = itemView.findViewById(R.id.time)
            event = itemView.findViewById(R.id.event_desc)
        }
    }
}