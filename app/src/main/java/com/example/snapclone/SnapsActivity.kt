package com.example.snapclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class SnapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snaps)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.snaps,menu)





        return super.onCreateOptionsMenu(menu)
    }
}
