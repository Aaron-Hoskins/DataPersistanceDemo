package com.examples.coding.datapersistancedemo

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import java.lang.UnsupportedOperationException

const val PERSON = 100
const val PERSON_ITEM = 101
class PersonProvider : ContentProvider() {
    val personDatabaseHelper by lazy {PersonDatabaseHelper(context!!)}
    val uriMatcher by lazy {buildUriMatcher()}

    fun buildUriMatcher() : UriMatcher{
        val returnMatcher = UriMatcher(UriMatcher.NO_MATCH)
        returnMatcher.addURI(CONTENT_AUTHORITY, PATH_PERSON, PERSON)
        returnMatcher.addURI(CONTENT_AUTHORITY, "$PATH_PERSON/#", PERSON_ITEM)
        return returnMatcher
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val database = personDatabaseHelper.writableDatabase
        val rowsDeleted = database.delete(TABLE_NAME, "$COL_SSN = ?", selectionArgs)
        if(rowsDeleted > 0){
            context?.contentResolver?.notifyChange(uri, null)
        }
        return rowsDeleted
    }

    override fun getType(uri: Uri): String? {
        return when(uriMatcher.match(uri)) {
            PERSON -> PersonEntry.CONTENT_TYPE
            PERSON_ITEM -> PersonEntry.CONTENT_ITEM_TYPE
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val database = personDatabaseHelper.writableDatabase
        val id = database.insert(TABLE_NAME, null, values)

        return when {
            id > 0 -> completeInsert(id, uri)
            else -> throw UnsupportedOperationException("Unable to insert row into $uri")
        }

    }

    private fun completeInsert(id : Long, uri: Uri) :Uri? {
        context?.contentResolver?.notifyChange(uri, null)
        return PersonEntry.buildPersonUri(id)
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val database = personDatabaseHelper.writableDatabase
        var returnCursor : Cursor? = null

        when(uriMatcher.match(uri)){
            PERSON -> {
                returnCursor = database.query(
                    TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder)
            }
            PERSON_ITEM -> {
                val id = ContentUris.parseId(uri).toString()
                returnCursor = database.query(
                    TABLE_NAME,
                    projection,
                    "$COL_SSN = ?",
                    arrayOf(id),
                    null,
                    null,
                    sortOrder
                )
            }
        }
        return returnCursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val databaseHelper = personDatabaseHelper.writableDatabase
        val rowsAffected = databaseHelper.update(TABLE_NAME, values, "$COL_SSN = ?", selectionArgs)
        if(rowsAffected > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return rowsAffected
    }
}
