package com.example.personalmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.personalmanager.model.Note

class ListFragment: Fragment(){
    val note: Note = Note()
    lateinit var noteList : ListView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v : View = inflater.inflate(R.layout.list_fragment, container, false)
        noteList = v.findViewById(R.id.noteList)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        noteList.setOnClickListener(View.OnClickListener {
            fun onClick(view: ListView){

            }
        })
    }
}