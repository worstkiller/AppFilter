package com.androidbuffer.raspian.widgets

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.androidbuffer.appfilter.R

/**
 * Created by AndroidBuffer on 11/4/18.
 */
class ProgressDialog(context: Context) : AlertDialog(context) {

    private var tvMessage: TextView
    private var progressView: ProgressBar

    private val view: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.widget_progress_view, null, false)
    }

    init {
        progressView = view.findViewById(R.id.pvLoader)
        tvMessage = view.findViewById(R.id.tvMessages)
        //default message
        tvMessage.setText("Please wait while loading...")
        setView(view)
    }

    /**
     * set the message for progress
     * @param messsag
     */
    fun setMessages(messsag: String) {
        tvMessage.setText(messsag)
    }

}