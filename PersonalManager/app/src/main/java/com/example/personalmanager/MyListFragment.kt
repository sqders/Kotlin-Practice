package com.example.personalmanager


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personalmanager.model.Note
import com.example.personalmanager.service.DataManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class MyListFragment: Fragment(){
    lateinit var recyclerView:RecyclerView
    lateinit var dataManager:DataManager
    lateinit var noteArrayList: List<Note>
    lateinit var listener: OnItemClickListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.name, "onCreate")
        val graph = (activity?.application as PersonalManagerApp).graph
        dataManager = graph.dataManager
        noteArrayList = dataManager.getNoteList()
        Log.d(this.javaClass.name, Json.encodeToString(noteArrayList))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (context as? OnItemClickListener)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v : View = inflater.inflate(R.layout.list_fragment, container, false)
        recyclerView = v.findViewById<RecyclerView>(R.id.noteList).apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = NoteListAdapter()
        }
        Log.d(this.javaClass.name, Json.encodeToString(noteArrayList))
        return v
    }
    interface OnItemClickListener{
        fun onItemClick(note:Note)
    }

    inner class NoteListAdapter():RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>(){

        inner class NoteViewHolder(var textView: TextView) : RecyclerView.ViewHolder(textView){
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return NoteViewHolder(v as TextView)
        }

        override fun getItemCount(): Int {
            return noteArrayList.size
        }

        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
            holder.textView.text = noteArrayList[position].title
            holder.textView.setOnClickListener{listener.onItemClick(noteArrayList[position])}
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = NoteListAdapter()
        }
    }
    private fun decodeNoteArrayList(stringList: List<String>):List<Note> =
        stringList.map{serializedString -> Json.decodeFromString(Note.serializer(),serializedString)}.toList()
}