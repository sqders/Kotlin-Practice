package com.example.personalmanager

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.personalmanager.model.Note
import com.example.personalmanager.service.DataManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    private val graph by lazy { (this.application as PersonalManagerApp).graph }
    private lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("MainActivityDebug", "onCreate")

        super.onCreate(savedInstanceState)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.main_activity)
            Toast.makeText(applicationContext, "ORIENTATION_PORTRAIT", Toast.LENGTH_SHORT).show()
        } else {
            setContentView(R.layout.main_activity_album)
            Toast.makeText(applicationContext, "ORIENTATION_LANDSCAPE", Toast.LENGTH_SHORT).show()
            var fragment = supportFragmentManager.findFragmentById(R.id.fragmentNoteList)
            if(fragment == null){
                fragment = ListFragment()
                supportFragmentManager.beginTransaction().add(R.id.fragmentNoteList,fragment).commit()
            }
        }
        dataManager = graph.dataManager
        dataManager.setCurrentIndex(savedInstanceState?.getInt("count", 0)?.minus(1) ?: 0)
        val nextButton: ImageButton = this.findViewById(R.id.nextNoteButton)
        val previousButton: ImageButton = this.findViewById(R.id.previousNoteButton)
        val addButton: Button = this.findViewById(R.id.addNoteButtonToActivity)
        val editButton: Button = this.findViewById(R.id.editNoteButtonToActivity)
        val lastButton: Button = this.findViewById(R.id.showLastNoteButton)

        nextButton.setOnClickListener(NextButtonOnClickListener())
        previousButton.setOnClickListener(PreviousButtonOnClickListener())
        addButton.setOnClickListener(AddButtonOnClickListener())
        editButton.setOnClickListener(EditButtonOnClickListener())
        lastButton.setOnClickListener(LastButtonOnClickListener())


        val titleTextView: TextView = findViewById(R.id.noteTitleView)
        val descriptionTextView: TextView = findViewById(R.id.noteDescriptionView)
        val nextNote: Note = dataManager.next()
        titleTextView.text = nextNote.title
        descriptionTextView.text = nextNote.noteDescription
    }

    inner class NextButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val titleTextView: TextView = findViewById(R.id.noteTitleView)
            val descriptionTextView: TextView = findViewById(R.id.noteDescriptionView)
            val nextNote: Note = dataManager.next()
            titleTextView.text = nextNote.title
            descriptionTextView.text = nextNote.noteDescription
            Toast.makeText(applicationContext, Json.encodeToString(nextNote), Toast.LENGTH_SHORT)
                .show()
        }
    }

    inner class PreviousButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val titleTextView: TextView = findViewById(R.id.noteTitleView)
            val descriptionTextView: TextView = findViewById(R.id.noteDescriptionView)
            val nextNote: Note = dataManager.previous()
            titleTextView.text = nextNote.title
            descriptionTextView.text = nextNote.noteDescription
            Toast.makeText(applicationContext, Json.encodeToString(nextNote), Toast.LENGTH_SHORT)
                .show()
        }
    }

    inner class AddButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }
    }

    inner class EditButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            if (dataManager.getMaxId() != -1) {
                val intent = Intent(this@MainActivity, EditActivity::class.java)
                startActivity(intent)
            }
        }
    }

    inner class LastButtonOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val titleTextView: TextView = findViewById(R.id.noteTitleView)
            val descriptionTextView: TextView = findViewById(R.id.noteDescriptionView)
            dataManager.setCurrentIndex(0)
            val nextNote: Note = dataManager.previous()
            titleTextView.text = nextNote.title
            descriptionTextView.text = nextNote.noteDescription
            Toast.makeText(applicationContext, Json.encodeToString(nextNote), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onStart() {
        Log.v("MainActivityDebug", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.v("MainActivityDebug", "onResume")
        super.onResume()
        val titleTextView: TextView = findViewById(R.id.noteTitleView)
        val descriptionTextView: TextView = findViewById(R.id.noteDescriptionView)
        dataManager.setCurrentIndex(dataManager.getCurrentIndex().minus(1))
        val nextNote: Note = dataManager.next()
        titleTextView.text = nextNote.title
        descriptionTextView.text = nextNote.noteDescription
    }

    override fun onPause() {
        Log.v("MainActivityDebug", "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.v("MainActivityDebug", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.v("MainActivityDebug", "onDestroy")
        super.onDestroy()
        dataManager.destroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.v("MainActivityDebug", "onSaveInstanceState")
        super.onSaveInstanceState(outState)
        outState.putInt("count", dataManager.getCurrentIndex())
        dataManager.destroy()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.v("MainActivityDebug", "onRestoreInstanceState")
        super.onRestoreInstanceState(savedInstanceState)
    }
}
