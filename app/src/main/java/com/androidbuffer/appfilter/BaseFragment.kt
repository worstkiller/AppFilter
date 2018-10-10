package com.androidbuffer.appfilter

import android.app.Fragment
import android.app.FragmentManager

/**
 * Created by AndroidBuffer on 7/2/18.
 */
open class BaseFragment : Fragment() {

    /**
     * change the view from base fragment
     */
    fun changeView(fragment: BaseFragment, isBackStack: Boolean) {
        (activity as BaseActivity).changeView(fragment, isBackStack)
    }

    /**
     * show the message with base activity context
     */
    fun showToast(message: String) {
        (activity as BaseActivity).showToast(message)
    }

    /**
     * show the message with base activity context
     */
    fun showToast(message: Int) {
        (activity as BaseActivity).showToast(message)
    }

    /**
     * set the message and show the progress
     */
    fun showPro(message: String) {
        (activity as BaseActivity).showProgress(message)
    }

    /**
     * hide the progress view
     */
    fun hidePro() {
        (activity as BaseActivity).hideProgress()
    }

    /**
     * pops the front fragment from screen and take to previous added
     */
    fun backPress() {
        (activity as BaseActivity).fragmentManager.popBackStack()
    }

    /**
     * opens a activity with back stack
     */
    fun <T> openActivity(javaClass: Class<T>) {
        (activity as BaseActivity).openActivity(javaClass)
    }

    /**
     * opens a activity with no back stack
     */
    fun <T> openNewActivity(javaClass: Class<T>) {
        (activity as BaseActivity).openNewActivity(javaClass)
    }
}