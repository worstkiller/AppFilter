package com.androidbuffer.appfilter.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.androidbuffer.appfilter.R
import com.androidbuffer.appfilter.model.AppListModel

/**
 * Created by incred on 10/7/18.
 */
class AdapterAppList(appList: ArrayList<AppListModel>) : RecyclerView.Adapter<AdapterAppList.ViewHolder>() {

    val localList = appList

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_app_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return localList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNameOfApp: TextView
        val tvPackageNamee: TextView

        init {
            tvNameOfApp = itemView.findViewById(R.id.tvAppListName)
            tvPackageNamee = itemView.findViewById(R.id.tvAppListPackage)
        }
    }
}