package com.example.personalmanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.personalmanager.model.Note
import com.example.personalmanager.service.DataManager
import com.example.personalmanager.service.ListIteratorNoteRepository

class MainActivity : AppCompatActivity() {
    val dataManager: DataManager = ListIteratorNoteRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val nextButton : Button = this.findViewById(R.id.nextNoteButton)
        val previousButton : Button = this.findViewById(R.id.previousNoteButton)
        val addButton : Button = this.findViewById(R.id.addNoteButton)
        val editButton : Button = this.findViewById(R.id.editNoteButton)

        nextButton.setOnClickListener(NextButtonOnClickListener())
        previousButton.setOnClickListener(PreviousButtonOnClickListener())
        addButton.setOnClickListener(AddButtonOnClickListener())
        editButton.setOnClickListener(EditButtonOnClickListener())
    }
    inner class NextButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            var titleTextView :TextView = findViewById(R.id.noteTitleText)
            var descriptionTextView : TextView = findViewById(R.id.noteDescriptionText)
            val nextNote : Note = dataManager.next()
            titleTextView.text = nextNote.title
            descriptionTextView.text = nextNote.noteDescription
        }
    }
    inner class PreviousButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            var titleTextView :TextView = findViewById(R.id.noteTitleText)
            var descriptionTextView : TextView = findViewById(R.id.noteDescriptionText)
            val nextNote : Note = dataManager.previous()
            titleTextView.text = nextNote.title
            descriptionTextView.text = nextNote.noteDescription
        }
    }
    inner class AddButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            var titleTextView :TextView = findViewById(R.id.noteTitleText)
            var descriptionTextView : TextView = findViewById(R.id.noteDescriptionText)
            dataManager.add(
                Note(title = titleTextView.text.toString(),
                noteDescription = descriptionTextView.text.toString())
            )
        }
    }
    inner class EditButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            var titleTextView :TextView = findViewById(R.id.noteTitleText)
            var descriptionTextView : TextView = findViewById(R.id.noteDescriptionText)
            dataManager.save(
                Note(title = titleTextView.text.toString(),
                    noteDescription = descriptionTextView.text.toString())
            )
        }
    }

    override fun onDestroy(){
        super.onDestroy()

    }
}
