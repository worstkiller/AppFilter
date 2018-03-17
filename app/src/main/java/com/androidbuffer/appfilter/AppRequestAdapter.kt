package com.androidbuffer.appfilter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * Created by AndroidBuffer on 14/3/18.
 */
class AppRequestAdapter(listOfItems: ArrayList<AppModel>, OnItemListener: OnClickItem) : RecyclerView.Adapter<AppRequestAdapter.ViewHolder>() {

    val listModels = listOfItems
    val listener = OnItemListener

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val model = listModels[position]
        holder?.tvName?.text = model.name
        Picasso.with(holder?.itemView?.context).load(model.iconUrl).placeholder(R.drawable.ic_android_placeholder).into(holder?.ivAppIcon, object : Callback {
            override fun onSuccess() {
                holder?.ivStatus?.visibility = View.VISIBLE
                holder?.ivStatus?.setImageResource(R.drawable.ic_success)
                Log.d("TAG++", "Picasso : success")
            }

            override fun onError() {
                holder?.ivStatus?.visibility = View.VISIBLE
                holder?.ivStatus?.setImageResource(R.drawable.ic_failure)
                Log.d("TAG++", "Picasso : failed")
            }
        })
        if (holder?.pbLoader?.visibility == View.VISIBLE && model.iconUrl != null) {
            holder.pbLoader.visibility = View.GONE
        } else {
            holder?.pbLoader?.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        Log.e("TAG++", "the no of items are ${listModels.size}")
        return listModels.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_app_request_row, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView?.setOnClickListener({
                listener.onItemClick(adapterPosition)
                if (listModels[adapterPosition].iconUrl == null) {
                    ivStatus?.visibility = View.GONE
                    pbLoader?.visibility = View.VISIBLE
                }
            })
        }

        val tvName = itemView?.findViewById<TextView>(R.id.tvName)
        val ivAppIcon = itemView?.findViewById<ImageView>(R.id.ivAppIcon)
        val pbLoader = itemView?.findViewById<ProgressBar>(R.id.pbLoader)
        val ivStatus = itemView?.findViewById<ImageView>(R.id.ivStatus)
    }

    interface OnClickItem {
        fun onItemClick(position: Int)
    }
}