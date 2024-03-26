package com.example.personalmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fragment_activity)
        supportFragmentManager.beginTransaction().add(R.id.listNoteContainer,ListFragment()).commit()
    }
}