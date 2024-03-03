package com.example.personalmanager

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.personalmanager.model.Note
import com.example.personalmanager.service.DataManager
import com.example.personalmanager.service.ListIteratorNoteRepository
import kotlinx.serialization.Serializable

class MainActivity : AppCompatActivity() {
    private val graph by lazy {(this.application as PersonalManagerApp).graph}
    private lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        dataManager = graph.dataManager
        val nextButton: ImageButton = this.findViewById(R.id.nextNoteButton)
        val previousButton: ImageButton = this.findViewById(R.id.previousNoteButton)
        val addButton: Button = this.findViewById(R.id.addNoteButtonToActivity)
        val editButton: Button = this.findViewById(R.id.editNoteButtonToActivity)

        nextButton.setOnClickListener(NextButtonOnClickListener())
        previousButton.setOnClickListener(PreviousButtonOnClickListener())
        addButton.setOnClickListener(AddButtonOnClickListener())
        editButton.setOnClickListener(EditButtonOnClickListener())
    }

    inner class NextButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val titleTextView: TextView = findViewById(R.id.noteTitleView)
            val descriptionTextView: TextView = findViewById(R.id.noteDescriptionView)
            val nextNote: Note = dataManager.next()
            titleTextView.text = nextNote.title
            descriptionTextView.text = nextNote.noteDescription
        }
    }

    inner class PreviousButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            var titleTextView: TextView = findViewById(R.id.noteTitleView)
            var descriptionTextView: TextView = findViewById(R.id.noteDescriptionView)
            val nextNote: Note = dataManager.previous()
            titleTextView.text = nextNote.title
            descriptionTextView.text = nextNote.noteDescription
        }
    }

    inner class AddButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val intent = Intent(this@MainActivity,AddActivity::class.java)
            startActivity(intent)
        }
    }

    inner class EditButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val intent = Intent(this@MainActivity,EditActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataManager.destroy()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("count",dataManager.getCurrentIndex())
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
