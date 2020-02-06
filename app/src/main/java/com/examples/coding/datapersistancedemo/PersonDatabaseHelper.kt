package com.examples.coding.datapersistancedemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class PersonDatabaseHelper(context : Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.execSQL(CREATE_PERSON_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, previousVersion: Int, newVersion: Int) {
        onCreate(sqLiteDatabase)
    }

    fun insertPersonIntoDatabase(person: Person){
        val database = writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_FIRST_NAME, person.firstName)
        contentValues.put(COL_LAST_NAME, person.lastName)
        contentValues.put(COL_SSN, person.ssn)

        database.insert(TABLE_NAME, null, contentValues)
        database.close()

    }

    fun getOnePersonFromDatabase(ssn : String) : Person? {
        val database = readableDatabase
        var person : Person? = null
        val cursor = database
            .rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL_SSN = '$ssn'",
                null)

        if(cursor.moveToFirst()) {
            val firstName = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))
            val lastName = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME))
            val ssn = cursor.getString(cursor.getColumnIndex(COL_SSN))
            person = Person(firstName, lastName, ssn)
        }
        cursor.close()
        database.close()
        return person
    }

    fun getAllPeopleFromDatabase() : ArrayList<Person> {
        val database = readableDatabase
        var personList : ArrayList<Person> = ArrayList<Person>()
        val cursor = database
            .rawQuery("SELECT * FROM $TABLE_NAME",
                null)

        if(cursor.moveToFirst()) {
            do {
                val firstName = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))
                val lastName = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME))
                val ssn = cursor.getString(cursor.getColumnIndex(COL_SSN))
                val person = Person(firstName, lastName, ssn)
                personList.add(person)
            } while(cursor.moveToNext())
        }

        cursor.close()
        database.close()
        return personList
    }

    fun updatePersonInDatabase(person : Person) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_FIRST_NAME, person.firstName)
        contentValues.put(COL_LAST_NAME, person.lastName)
        contentValues.put(COL_SSN, person.ssn)

        database.update(TABLE_NAME, contentValues, "$COL_SSN = ?", arrayOf(person.ssn))
        database.close()
    }

    fun removePersonFromDatabase(ssn : String) {
        val database = writableDatabase
        database.delete(TABLE_NAME, "$COL_SSN = ?", arrayOf(ssn))
        database.close()
    }
}