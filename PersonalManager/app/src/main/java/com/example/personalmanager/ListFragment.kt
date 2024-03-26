package com.example.personalmanager

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.personalmanager.model.Note
import com.example.personalmanager.service.DataManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.streams.toList


class ListFragment: Fragment(){
    lateinit var dataManager:DataManager
    lateinit var noteArrayList: ArrayList<Note>
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
        var v : View = inflater.inflate(R.layout.list_content, container, false)
        val graph = (activity?.application as PersonalManagerApp).graph
        dataManager = graph.dataManager
        noteListView = v.findViewById(R.id.list)
        noteListView.adapter = ArrayAdapter(requireActivity(),R.layout.activity_list_item,noteArrayList.stream().map{it.title}.toArray())
        noteListView.onItemClickListener = OnItemClickListenerImpl()
        Log.d(this.javaClass.name, Json.encodeToString(noteArrayList))
        return v
    }

    inner class OnItemClickListenerImpl : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Toast.makeText(context, "Clicked item : "+position,Toast.LENGTH_SHORT).show()
            val intent = Intent(activity,EditActivity::class.java)
            intent.putExtra("title",noteArrayList.get(id.toInt()).title)
            intent.putExtra("id",noteArrayList.get(id.toInt()).id)
            intent.putExtra("noteDescription",noteArrayList.get(id.toInt()).noteDescription)
            startActivity(intent)
        }
    }

    private fun decodeNoteArrayList(stringList: List<String>):List<Note> =
        stringList.map{serializedString -> Json.decodeFromString(Note.serializer(),serializedString)}.toList()
}