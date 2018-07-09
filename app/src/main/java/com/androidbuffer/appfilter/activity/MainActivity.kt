package com.androidbuffer.appfilter.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.io.InputStream
import org.xmlpull.v1.XmlPullParser
import android.util.Xml
import android.view.Menu
import android.view.MenuItem
import com.androidbuffer.appfilter.model.AppModel
import com.androidbuffer.appfilter.helpers.DownloadManager
import com.androidbuffer.appfilter.R
import com.androidbuffer.appfilter.adapter.AppRequestAdapter
import com.squareup.picasso.Target

class MainActivity : AppCompatActivity(), AppRequestAdapter.OnClickItem {

    private val REQUEST_CODE_PERMISSION: Int = 102
    private val REQUEST_CODE: Int = 101
    private lateinit var rvAppRequestList: RecyclerView
    private var listOfItems = ArrayList<AppModel>()
    private var appAdapter: AppRequestAdapter? = null
    private lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        manageStartOption()
    }

    private fun manageStartOption() {
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            openDefaultDialog()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION)
        }
    }

    private fun initViews() {
        rvAppRequestList = findViewById(R.id.rvAppRequestList)
        appAdapter = AppRequestAdapter(listOfItems, this)
        rvAppRequestList.layoutManager = LinearLayoutManager(this)
        rvAppRequestList.adapter = appAdapter
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(
                toolbar)
    }

    private fun hasPermission(reaD_EXTERNAL_STORAGE: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, reaD_EXTERNAL_STORAGE)
    }

    private fun openDefaultDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_default, null, false)
        val dialog = AlertDialog.Builder(this).setView(view).setCancelable(false).create()
        view.findViewById<TextView>(R.id.tvDialogAppFilter).setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            chooseAppsFromAppFilter()
        })
        view.findViewById<TextView>(R.id.tvDialogChooseApps).setOnClickListener {
            View.OnClickListener {
                chooseAppsFromSystem()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun chooseAppsFromAppFilter() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        val intentChooser = Intent.createChooser(intent, "Pick your App Filter file")
        startActivityForResult(intentChooser, REQUEST_CODE)
    }

    private fun chooseAppsFromSystem() {
        Toast.makeText(this, "This feature is coming soon. Cheers!!!", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                val streamInput = contentResolver.openInputStream(uri)
                listOfItems.addAll(getParsedXmlList(streamInput))
                appAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openDefaultDialog()
        }
    }

    private fun getParsedXmlList(`in`: InputStream): ArrayList<AppModel> {
        var listOfItems = ArrayList<AppModel>()
        try {
            val parser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(`in`, null)
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.eventType != XmlPullParser.START_TAG) {
                    continue
                }
                val name = parser.name
                if (name.equals("item")) {
                    val title = parser.getAttributeValue(null, "component")
                    val name = parser.getAttributeValue(null, "drawable")
                    if (title.contains("ComponentInfo")) {
                        try {
                            val packageName = title.substring(title.indexOf("{") + 1, title.indexOf("/"))
                            val modelItem = AppModel(name, null, packageName)
                            listOfItems.add(modelItem)
                            Log.e("TAG++", ": PackageName = ${packageName}")
                        } catch (exp: StringIndexOutOfBoundsException) {
                            exp.printStackTrace()
                        }
                    }
                }
                parser.next()
            }
            return listOfItems
        } finally {
            `in`.close()
        }
    }

    override fun onItemClick(position: Int) {
        DownloadManager.getImage(listOfItems.get(position).packageName, object : DownloadManager.OnResponseFromPlay {
            override fun onResponse(imageUrl: String?) {
                Log.e("TAG++", ": Url = ${imageUrl}")
                if (imageUrl != null) {
                    DownloadManager.saveImageToFolder(this@MainActivity, listOfItems[position].name, imageUrl, object : DownloadManager.OnSaveFileListener {
                        override fun onSave(uri: String, target: Target) {
                            listOfItems.get(position).iconUrl = imageUrl
                            appAdapter?.notifyItemChanged(position)
                            //for strong reference to target
                            toolbar.setTag(target)
                        }
                    })
                } else {
                    listOfItems.get(position).iconUrl = imageUrl
                    appAdapter?.notifyItemChanged(position)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId== R.id.appfilter){
            chooseAppsFromAppFilter()
        }
        return super.onOptionsItemSelected(item)
    }
}
