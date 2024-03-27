package com.example.personalmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personalmanager.model.Note

class NoteListActivity : AppCompatActivity(),MyListFragment.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentNoteList,MyListFragment()).commit()
    }

    override fun onItemClick(note: Note) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentDetail,EditNoteFragment.newInstance(note)).commit()
    }
}