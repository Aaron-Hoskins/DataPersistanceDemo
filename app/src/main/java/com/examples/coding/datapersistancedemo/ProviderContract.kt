package com.examples.coding.datapersistancedemo

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

const val CONTENT_AUTHORITY = "com.examples.coding.datapersistancedemo"
val BASE_CONTENT_ID = Uri.parse("content://${CONTENT_AUTHORITY}")
const val PATH_PERSON = "person"
val CONTENT_URI = BASE_CONTENT_ID.buildUpon().appendPath(PATH_PERSON).build()

class PersonEntry : BaseColumns{
    //Static Object in Kotlin
    companion object{
        val CONTENT_TYPE = "vnd.android.cursor.dir/${CONTENT_URI}/${PATH_PERSON}"
        val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/${CONTENT_URI}/${PATH_PERSON}"
        fun buildPersonUri(id : Long)  = ContentUris.withAppendedId(CONTENT_URI, id)
    }

}