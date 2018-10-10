package com.androidbuffer.appfilter.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.androidbuffer.appfilter.BaseActivity
import com.androidbuffer.appfilter.R
import com.androidbuffer.appfilter.fragment.AddAppFragment
import com.androidbuffer.appfilter.fragment.AppListFragment
import kotlinx.android.synthetic.main.activity_dashboard.view.*

/**
 * Created by incred on 9/7/18.
 */
class DashboardActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
//        replaceFragment(R.id.containerDashboard, AddAppFragment.getInstance(), isBackStack = false)
        replaceFragment(R.id.containerDashboard, AppListFragment.getInstance(), isBackStack = false)
    }

}