package com.example.personalmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ReportFragment.Companion.reportFragment

class NoteListActivity : AppCompatActivity() {
    private val graph by lazy { (this.application as PersonalManagerApp).graph }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        var fragment = supportFragmentManager.findFragmentById(R.id.fragmentNoteList)
        if(fragment == null){
            fragment = ListFragment(graph.dataManager.getNoteList())
            supportFragmentManager.beginTransaction().add(R.id.fragmentNoteList,fragment).commit()
        }
    }
}