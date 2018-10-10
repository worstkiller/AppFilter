package com.androidbuffer.appfilter.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.androidbuffer.appfilter.BaseFragment
import com.androidbuffer.appfilter.R

/**
 * Created by incred on 10/7/18.
 */
class AddAppFragment : BaseFragment() {

    private lateinit var fabButtonGo: FloatingActionButton
    private lateinit var etAppName: EditText
    private lateinit var etPackageName: EditText

    companion object {

        /**
         * get a instance of add app fragment
         */
        fun getInstance(): AddAppFragment {
            return AddAppFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_add_app, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (view == null) return
        fabButtonGo = view.findViewById(R.id.fabButtonGo)
        etAppName = view.findViewById(R.id.etAppName)
        etPackageName = view.findViewById(R.id.etPackageName)
        setClickListener()
    }

    private fun setClickListener() {
        fabButtonGo.setOnClickListener { view ->
            if (validateData()) {
                showToast("Data is valid")
            } else {
                showToast("Data is invalid")
            }
        }
    }

    private fun validateData(): Boolean {
        if (etAppName.text.isNullOrEmpty()) return false
        if (etPackageName.text.isNullOrEmpty()) return false
        return true
    }
}