package com.examples.coding.datapersistancedemo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_database.*

class DatabaseActivity : AppCompatActivity(), PersonCallback {
    val databaseHelper by lazy{ PersonDatabaseHelper(this)}
    val adapter by lazy {PersonRVAdapter(databaseHelper.getAllPeopleFromDatabase(), this)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)
        initRecyclerView()
    }

    override fun passPerson(person: Person) {
        etFirstName.setText(person.firstName)
        etLastName.setText(person.lastName)
        etSsn.setText(person.ssn)
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.btnAddPersonToList -> {
                val firstName = etFirstName.text.toString()
                val lastName = etLastName.text.toString()
                val ssn = etSsn.text.toString()
                databaseHelper.insertPersonIntoDatabase(Person(firstName, lastName, ssn))
            }
            R.id.btnUpdatePersonToList -> {
                val firstName = etFirstName.text.toString()
                val lastName = etLastName.text.toString()
                val ssn = etSsn.text.toString()
                databaseHelper.updatePersonInDatabase(Person(firstName, lastName, ssn))
            }
            R.id.btnDeletePersonToList -> {
                val ssn = etSsn.text.toString()
                databaseHelper.removePersonFromDatabase(ssn)
            }
        }
        adapter.updateList(databaseHelper.getAllPeopleFromDatabase())
    }

    private fun initRecyclerView() {
        rvPersonList.layoutManager = LinearLayoutManager(this)
        rvPersonList.adapter = adapter
    }

    fun onContentProviderClicked(view: View) {
        val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
        val personList = ArrayList<Person>()
        if(cursor!!.moveToFirst()) {
            do {
                val firstName = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME)) + " CONTENT"
                val lastName = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME))+ " CONTENT"
                val ssn = cursor.getString(cursor.getColumnIndex(COL_SSN))+ " CONTENT"
                val person = Person(firstName, lastName, ssn)
                personList.add(person)
            } while(cursor.moveToNext())
        }

        cursor.close()
        adapter.updateList(personList)
    }
}
