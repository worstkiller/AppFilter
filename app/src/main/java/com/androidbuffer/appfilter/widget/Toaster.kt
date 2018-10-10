package com.androidbuffer.appfilter.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.androidbuffer.appfilter.R

/**
 * Created by incred on 10/7/18.
 */
class Toaster {

    companion object {

        fun makeToast(context: Context, message: String, length: Int = Toast.LENGTH_SHORT) {
            val viewLocal: View by lazy {
                LayoutInflater.from(context).inflate(R.layout.widget_toast, null, false)
            }
            val toast = Toast(context)
            toast.view = viewLocal
            toast.view.findViewById<TextView>(android.R.id.message).text = message
            toast.duration = length
            toast.show()
        }

        fun makeToast(context: Context, message: Int, length: Int = Toast.LENGTH_SHORT) {
            val viewLocal: View by lazy {
                LayoutInflater.from(context).inflate(R.layout.widget_toast, null, false)
            }
            val toast = Toast(context)
            toast.view = viewLocal
            toast.view.findViewById<TextView>(android.R.id.message).text = context.getString(message)
            toast.duration = length
            toast.show()
        }
    }
}