package com.androidbuffer.appfilter

/**
 * Created by AndroidBuffer on 14/3/18.
 */

const val END_POINT = "https://play.google.com/store/apps/details?id=%s"
const val START_INDEX_PATTERN = "<img class=\"cover-image\" src=\"//"
const val END_INDEX_PATTERN = "w300-rw\""
const val REQUIRED_IMAGE_SIZE = "w512-rw"
const val URL_PROTOCOL = "http://"
const val END_PATTERN = "alt=\"Cover art\""
const val PATTERN_CODE = "<img class=\\\"cover-image\\\" src=\\\"\\/\\/\\S.*rw\\\"\\salt=\\\"Cover\\sart\\\""