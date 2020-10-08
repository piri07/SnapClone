package com.example.snapclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class chooseUserActivity : AppCompatActivity() {

    var ChooseUserListView : ListView? = null
    var emails : ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)
        ChooseUserListView = findViewById(R.id.ChooseUserListView)

        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,emails)
        ChooseUserListView?.adapter=adapter

        FirebaseDatabase.getInstance().getReference().child("users").addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val email = p0?.child("email")?.value as String
                emails.add(email)
                adapter.notifyDataSetChanged()
            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}

        })
        ChooseUserListView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

        }

    }
}
