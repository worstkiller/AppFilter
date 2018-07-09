package com.androidbuffer.appfilter.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Environment
import android.os.Handler
import android.util.Log
import com.androidbuffer.appfilter.util.END_POINT
import com.androidbuffer.appfilter.util.PATTERN_THIRD
import com.androidbuffer.appfilter.util.PATTERN_THIRD_URL
import com.androidbuffer.appfilter.R
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
            val target = object : Target {
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
            }
            Picasso.with(context).load(imageUrl).into(target)

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
                val stream = BufferedInputStream(urlConnection.getInputStream())
                val bufferRead = BufferedReader(InputStreamReader(stream))
                val stringBuffer = StringBuffer()
                for (item in bufferRead.readLines()) {
                    stringBuffer.append(item)
                }
                return getDownloadUrl(stringBuffer.toString())
            } catch (exp: UnknownHostException) {
                Log.d("TAG++", ": Exception = ${exp.message}")
            } catch (exp: FileNotFoundException) {
                Log.d("TAG++", ": Exception = ${exp.message}")
            }
            return null
        }

        fun getDownloadUrl(response: String?): String? {
            if (response != null) {
                val patternMatcher = Pattern.compile(PATTERN_THIRD)
                val matcher = patternMatcher.matcher(response)
                if (matcher.find()) {
                    val response = matcher.group(0)
                    val urlPattern = Pattern.compile(PATTERN_THIRD_URL)
                    val urlMatcher = urlPattern.matcher(response)
                    if (urlMatcher.find()) {
                        val url = urlMatcher.group(0)
                        var newUrl: StringBuffer? = null
                        if (url.contains("=")) {
                            newUrl = StringBuffer(url.substring(0, url.indexOf("=")))
                            newUrl.append("=s512")
                        } else if (url.contains(" ")) {
                            newUrl = StringBuffer(url.substring(0, url.indexOf(" ")))
                            newUrl.append("=s512")
                        }
                        return newUrl.toString();
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