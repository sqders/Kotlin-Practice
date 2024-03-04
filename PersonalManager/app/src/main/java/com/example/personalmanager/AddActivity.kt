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

class AddActivity : AppCompatActivity() {
    private val graph by lazy { (this.application as PersonalManagerApp).graph }
    private lateinit var dataManager: DataManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)
        dataManager = graph.dataManager
        dataManager.setCurrentIndex(dataManager.getMaxId().plus(1))
        val titleTextView: EditText = findViewById(R.id.noteTitleTextToAdd)
        var descriptionTextView: EditText = findViewById(R.id.noteDescriptionTextToAdd)
        titleTextView.setText(String.format("%s №%d",getString(R.string.note_default_title),dataManager.getCurrentIndex()) )
        descriptionTextView.setText(getString(R.string.note_description))
        val addButton: Button = this.findViewById(R.id.addNoteButton)
        addButton.setOnClickListener(AddButtonOnClickListener())
    }

    inner class AddButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            var titleTextView: EditText = findViewById(R.id.noteTitleTextToAdd)
            var descriptionTextView: EditText = findViewById(R.id.noteDescriptionTextToAdd)
            dataManager.add(
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

}
