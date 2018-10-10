package com.androidbuffer.appfilter.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidbuffer.appfilter.BaseFragment
import com.androidbuffer.appfilter.R
import com.androidbuffer.appfilter.adapter.AdapterAppList
import com.androidbuffer.appfilter.model.AppListModel

/**
 * Created by incred on 10/7/18.
 */
class AppListFragment : BaseFragment() {

    private lateinit var rvAppList: RecyclerView
    private lateinit var adapterAppList: AdapterAppList
    private val appList = ArrayList<AppListModel>()

    companion object {

        fun getInstance():AppListFragment{
            return AppListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_app_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (view == null) return
        rvAppList = view.findViewById(R.id.rvAppList)
        adapterAppList = AdapterAppList(getDummyList())
        rvAppList.layoutManager = LinearLayoutManager(activity)
        rvAppList.adapter = adapterAppList
    }

    private fun getDummyList(): ArrayList<AppListModel> {
        val list = ArrayList<AppListModel>()
        for (i in 0..10) {
            list.add(AppListModel("WhatsApp", "com.android.whatsapp"))
        }
        return list
    }
}