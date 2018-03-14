package com.androidbuffer.appfilter

import android.os.AsyncTask
import android.util.Log

/**
 * Created by AndroidBuffer on 14/3/18.
 */
class DownloadManager{

    companion object {

        fun getImage(packageName:String){
            val url = END_POINT.format(packageName)
            Log.d("TAG",url)
            DownLoadAsync().execute(url)
        }
    }

    class DownLoadAsync:AsyncTask<String,Unit,String>(){
        override fun doInBackground(vararg p0: String?): String {
            return ""
        }

    }
}