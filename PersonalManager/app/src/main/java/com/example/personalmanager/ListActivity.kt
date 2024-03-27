package com.example.personalmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personalmanager.model.Note

class ListActivity : AppCompatActivity(),MyListFragment.OnItemClickListener {
    lateinit var fragment: MyListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fragment_activity)
        fragment = MyListFragment()
        supportFragmentManager.beginTransaction().add(R.id.listNoteContainer,fragment).commit()
    }

    override fun onItemClick(note:Note) {
        val intent = Intent(this,EditActivity::class.java)
        intent.putExtra("title",note.title)
        intent.putExtra("id",note.id)
        intent.putExtra("noteDescription",note.noteDescription)
        intent.putExtra("checked",note.checked)
        startActivity(intent)
    }
}