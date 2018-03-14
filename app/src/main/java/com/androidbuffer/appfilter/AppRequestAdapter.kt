package com.androidbuffer.appfilter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

/**
 * Created by AndroidBuffer on 14/3/18.
 */
class AppRequestAdapter(listOfItems:ArrayList<AppModel>,OnItemListener:OnClickItem): RecyclerView.Adapter<AppRequestAdapter.ViewHolder>() {

    val listModels = listOfItems
    val listener = OnItemListener

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val model  = listModels.get(position)
        holder?.tvName?.text=model.name
        Picasso.with(holder?.itemView?.context).load(model.iconUrl).placeholder(R.drawable.ic_android_placeholder).into(holder?.ivAppIcon)
    }

    override fun getItemCount(): Int {
        Log.e("TAG++","the no of items are ${listModels.size}")
        return listModels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_app_request_row,parent,false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView?.setOnClickListener(View.OnClickListener {
               listener.onItemClick(adapterPosition)
            })
        }
        val tvName  = itemView?.findViewById<TextView>(R.id.tvName)
        val ivAppIcon = itemView?.findViewById<ImageView>(R.id.ivAppIcon)
    }

    interface OnClickItem{
        fun onItemClick(position:Int)
    }
}