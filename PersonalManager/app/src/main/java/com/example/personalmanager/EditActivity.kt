package com.example.personalmanager

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.personalmanager.model.Note
import com.example.personalmanager.service.DataManager
import com.example.personalmanager.service.ListIteratorNoteRepository

class EditActivity : AppCompatActivity() {
    private val graph by lazy {(this.application as PersonalManagerApp).graph}
    private lateinit var dataManager : DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        dataManager = graph.dataManager
        dataManager.setCurrentIndex(savedInstanceState?.getInt("count",0)?.minus(1) ?: 0)
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
                )
            )
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        dataManager.destroy()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("count",dataManager.getCurrentIndex().plus(1))
        dataManager.destroy()
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        dataManager.setCurrentIndex(savedInstanceState?.getInt("count",0) ?: 0)
        dataManager = ListIteratorNoteRepository()
    }
}
