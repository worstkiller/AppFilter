package com.androidbuffer.appfilter

import android.app.FragmentManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.androidbuffer.appfilter.widget.Toaster
import com.androidbuffer.raspian.widgets.ProgressDialog


/**
 * Created by AndroidBuffer on 30/1/18.
 */
open class BaseActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)
    }

    /**
     * set the background color of the entire activity
     */
    private fun backGroundColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * @see Class<T>
     * @param javaClass
     * @return Unit
     */
    internal fun <T> openNewActivity(javaClass: Class<T>) {
        val intent = Intent(this, javaClass)
        startActivity(intent)
        finish()
    }

    /**
     * @see Class<T>
     * @param javaClass
     * @return Unit
     */
    internal fun <T> openActivity(javaClass: Class<T>) {
        val intent = Intent(this, javaClass)
        startActivity(intent)
    }

    /**
     * @see Class<FragmentManager>
     * @param containerId
     * @param fragment
     * @param isBackStack
     */
    open fun replaceFragment(containerId: Int, fragment: BaseFragment, isBackStack: Boolean) {
        if (isBackStack) {
            fragmentManager.replaceFragmentWithBackStack(containerId, fragment)
        } else {
            fragmentManager.replaceFragment(containerId, fragment)
        }
    }

    /**
     * replaces the fragment using fragment manager extension function, with no back stack
     * @param containerId
     * @param fragment
     */
    private fun FragmentManager.replaceFragment(containerId: Int, fragment: BaseFragment) {
        beginTransaction().replace(containerId, fragment).commit()
    }

    /**
     * replaces the fragment using fragment manager extension function, with back stack
     * @param containerId
     * @param fragment
     */
    private fun FragmentManager.replaceFragmentWithBackStack(containerId: Int, fragment: BaseFragment) {
        beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(fragment::class.java.canonicalName)
                .commit()
    }

    /*
    for polymorphism only
     */
    open fun changeView(fragment: BaseFragment, isBackStack: Boolean) = Unit

    /**
     * show the message passed as @see String
     * @param message
     */
    fun showToast(message: String) {
        Toaster.makeToast(BaseActivity@ this,message)
    }

    /**
     * show the message passed as @see Int
     * @param message
     */
    fun showToast(message: Int) {
        Toaster.makeToast(BaseActivity@ this,message)
    }

    /**
     * set the message and show the progress
     * @param message
     */
    fun showProgress(message: String) {
        progressDialog.setMessages(message)
        progressDialog.show()
    }

    /**
     * hide the progress view
     */
    fun hideProgress() {
        progressDialog.hide()
    }
}