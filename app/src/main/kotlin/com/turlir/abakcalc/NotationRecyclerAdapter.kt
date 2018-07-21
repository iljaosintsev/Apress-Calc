package com.turlir.abakcalc

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

class NotationRecyclerAdapter constructor(cnt: Context) : RecyclerView.Adapter<NotationRecyclerAdapter.Holder>() {

    private val mData: MutableList<Any> = ArrayList()
    private val mInflater = LayoutInflater.from(cnt)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = mInflater.inflate(R.layout.notation_item, parent, false)
        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount() = mData.size

    fun setItems(items: List<Any>) {
        mData.clear()
        mData.addAll(items)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(visual: Any) {
            (itemView as TextView).text = visual.toString()
        }
    }

}
