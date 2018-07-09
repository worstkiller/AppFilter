package com.androidbuffer.appfilter.util

/**
 * Created by AndroidBuffer on 14/3/18.
 */

const val END_POINT = "https://play.google.com/store/apps/details?id=%s"
const val PATTERN_THIRD = "<img\\s+(.*?)src=\\\"https:\\/\\/\\S.*?\\\"\\s+(.*?)alt=\\\"Cover art\\\"(.*?)>"
const val PATTERN_THIRD_URL = "https:\\/\\/\\S.*?(=?)(\\S.*?)\"\\s"