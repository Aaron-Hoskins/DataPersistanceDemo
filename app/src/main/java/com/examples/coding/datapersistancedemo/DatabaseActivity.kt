package com.examples.coding.datapersistancedemo

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
}
