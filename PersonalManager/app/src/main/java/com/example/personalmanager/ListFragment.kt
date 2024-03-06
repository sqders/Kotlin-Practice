package com.example.personalmanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.personalmanager.model.Note
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListFragment(val noteArrayList: List<Note> ): Fragment(){
    val note: Note = Note()
    lateinit var noteListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.name, "onCreate")

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v : View = inflater.inflate(R.layout.list_fragment, container, false)
        noteListView = v.findViewById(R.id.noteList)
        Log.d(this.javaClass.name, Json.encodeToString(noteArrayList))
        return v
    }

    override fun onStart() {
        super.onStart()
        noteListView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this.context, "Clicked item :"+" "+position,Toast.LENGTH_SHORT).show()
        }
    }
    private fun decodeNoteArrayList(stringList: List<String>):List<Note> =
        stringList.map{serializedString -> Json.decodeFromString(Note.serializer(),serializedString)}.toList()
}