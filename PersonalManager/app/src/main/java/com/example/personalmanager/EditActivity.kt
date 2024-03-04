package com.example.personalmanager

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personalmanager.model.Note
import com.example.personalmanager.service.DataManager
import com.example.personalmanager.service.ListIteratorNoteRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EditActivity : AppCompatActivity() {
    private val graph by lazy {(this.application as PersonalManagerApp).graph}
    private lateinit var dataManager : DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        dataManager = graph.dataManager
        dataManager.setCurrentIndex(dataManager.getCurrentIndex().minus(1))
        var note = dataManager.next()
        val titleTextView: EditText = findViewById(R.id.noteTitleText)
        val descriptionTextView: EditText = findViewById(R.id.noteDescriptionText)
        titleTextView.setText(note.title)
        descriptionTextView.setText(note.noteDescription)

        val editButton: Button = findViewById(R.id.saveNoteButton)
        editButton.setOnClickListener(EditButtonOnClickListener())
    }
    inner class EditButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            var titleTextView: TextView = findViewById(R.id.noteTitleText)
            var descriptionTextView: TextView = findViewById(R.id.noteDescriptionText)
            dataManager.save(
                Note(
                    title = titleTextView.text.toString(),
                    noteDescription = descriptionTextView.text.toString(),
                    id = dataManager.getCurrentIndex()
                ).also { Toast.makeText(applicationContext, Json.encodeToString(it), Toast.LENGTH_LONG).show() }
            )
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        dataManager.destroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

}
