package com.example.personalmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ReportFragment.Companion.reportFragment

class NoteListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentNoteList)
        if(fragment == null){
            fragment = ListFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragmentNoteList,fragment).commit()
        }
    }
}