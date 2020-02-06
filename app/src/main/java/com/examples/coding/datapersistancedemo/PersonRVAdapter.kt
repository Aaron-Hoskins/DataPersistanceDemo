package com.examples.coding.datapersistancedemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.person_item.view.*

class PersonRVAdapter(var personList : ArrayList<Person>, val callback: PersonCallback)
    : RecyclerView.Adapter<PersonRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.populatePersonItem(personList[position])

    override fun getItemCount() = personList.size

    fun updateList(passedList : ArrayList<Person>) {
        personList = passedList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun populatePersonItem(person : Person) {
            itemView.tvFirstName.text = person.firstName
            itemView.tvLastName.text = person.lastName
            itemView.tvSsn.text = person.ssn
            itemView.setOnClickListener{ callback.passPerson(person)}
        }
    }

}