package com.androidbuffer.appfilter

import android.os.AsyncTask
import android.os.PatternMatcher
import android.util.Log
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.UnknownHostException
import java.util.regex.Pattern
import javax.net.ssl.HttpsURLConnection

/**
 * Created by AndroidBuffer on 14/3/18.
 */
class DownloadManager {

    companion object {

        fun getImage(packageName: String, listener: OnResponseFromPlay) {
            val url = END_POINT.format(packageName)
            Log.e("TAG++", url)
            DownLoadAsync(listener).execute(url)
        }
    }

    class DownLoadAsync(val listener: OnResponseFromPlay) : AsyncTask<String, Unit, String?>() {

        override fun doInBackground(vararg p0: String?): String? {
            try {
                val url = URL(p0[0])
                val urlConnection = url.openConnection() as HttpsURLConnection
                val stream = BufferedInputStream(urlConnection.getInputStream())
                val bufferRead = BufferedReader(InputStreamReader(stream))
                val stringBuffer = StringBuffer()
                for (item in bufferRead.readLines()) {
                    stringBuffer.append(item)
                }
                return getDownloadUrl(stringBuffer.toString())
            } catch (exp: UnknownHostException) {
                Log.d("TAG++", ": Exception = ${exp.message}")
            }
            return null
        }

        fun getDownloadUrl(response: String?): String? {
            if (response != null) {
                val patternMatcher = Pattern.compile(PATTERN_CODE)
                val matcher = patternMatcher.matcher(response)
                if (matcher.find()) {
                    var matchedResponse = matcher.group()
                    matchedResponse = matchedResponse.replace(START_INDEX_PATTERN, "")
                            .replace(END_INDEX_PATTERN, REQUIRED_IMAGE_SIZE)
                            .replace(END_PATTERN, "")
                            .trim()
                    return URL_PROTOCOL + matchedResponse
                }
            } else {
                return null
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            listener.onResponse(result)
        }
    }

    interface OnResponseFromPlay {
        fun onResponse(imageUrl: String?)
    }
}