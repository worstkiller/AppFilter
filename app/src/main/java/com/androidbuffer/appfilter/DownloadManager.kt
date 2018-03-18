package com.androidbuffer.appfilter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.os.Handler
import android.os.PatternMatcher
import android.util.Log
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.*
import java.net.URL
import java.net.UnknownHostException
import java.util.regex.Pattern
import javax.net.ssl.HttpsURLConnection

/**
 * Created by AndroidBuffer on 14/3/18.
 */
class DownloadManager {

    companion object {

        fun saveImageToFolder(context: Context, name: String?, imageUrl: String?, listener: OnSaveFileListener) {
            Picasso.with(context).load(imageUrl).into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    Log.d("TAG++", "Picasso: onPreparedLoad")
                }

                override fun onBitmapFailed(errorDrawable: Drawable?) {
                    Log.d("TAG++", "Picasso: onBitmapFailed")
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    Handler().post({
                        val parentFolder = File(Environment.getExternalStorageDirectory().path.plus("/").plus(context.getString(R.string.app_name)))
                        if (!parentFolder.exists()) {
                            parentFolder.mkdirs()
                        }
                        val newImageFile = File(parentFolder, name?.plus(".png"))
                        if (!newImageFile.exists()) {
                            newImageFile.createNewFile()
                        }
                        val outStream = FileOutputStream(newImageFile)
                        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                        outStream.flush()
                        outStream.close()
                        listener.onSave(newImageFile.path, this)
                        Log.d("TAG++", "File: ".plus(newImageFile.path))
                    })
                    Log.d("TAG++", "Picasso: onBitmapLoaded")
                }
            })
        }

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
                urlConnection.connect()
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
                } else {
                    val secondPattern = Pattern.compile(PATTERN_SECOND_CODE)
                    val secondMatcher = secondPattern.matcher(response)
                    if (secondMatcher.find()) {
                        return matcher.group().replace(PATTERN_SECOND_REPLACE, PATTERN_SECOND_REQUIRED_SIZE)
                    } else {
                        return null
                    }
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

    interface OnSaveFileListener {
        fun onSave(path: String, target: Target)
    }

    interface OnResponseFromPlay {
        fun onResponse(imageUrl: String?)
    }
}