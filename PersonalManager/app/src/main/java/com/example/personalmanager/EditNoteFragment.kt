package com.example.personalmanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.personalmanager.model.Note
import com.example.personalmanager.service.DataManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EditNoteFragment:Fragment() {
    private lateinit var dataManager : DataManager
    private lateinit var note: Note
    private lateinit var titleTextView: EditText
    private lateinit var descriptionTextView: EditText

    companion object{
        fun newInstance(note:Note):EditNoteFragment{
            val fragment = EditNoteFragment()
            val args = Bundle()
            args.putString("title",note.title)
            args.putString("noteDescription",note.noteDescription)
            args.putInt("id",note.id)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val graph = (activity?.application as PersonalManagerApp).graph
        dataManager = graph.dataManager
        note = Note(
            title = arguments?.getString("title")!!,
            noteDescription =  arguments?.getString("noteDescription")!!,
            id = arguments?.getInt("id")!!
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v : View = inflater.inflate(R.layout.edit_activity, container, false)
        Log.d(this.javaClass.name, Json.encodeToString(note))
        titleTextView = v.findViewById(R.id.noteTitleText)
        descriptionTextView = v.findViewById(R.id.noteDescriptionText)
        titleTextView.setText(note.title)
        descriptionTextView.setText(note.noteDescription)

        val editButton: Button = v.findViewById(R.id.saveNoteButton)
        editButton.setOnClickListener(EditButtonOnClickListener())
        return v
    }
    inner class EditButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val note = Note(
                title = titleTextView.text.toString(),
                noteDescription = descriptionTextView.text.toString(),
                id = note.id
            )
            dataManager.save(note)
            activity?.finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        dataManager.destroy()
    }
}