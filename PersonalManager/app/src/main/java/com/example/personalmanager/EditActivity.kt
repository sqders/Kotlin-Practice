package com.example.personalmanager

import android.app.Activity
import android.content.Intent
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

class EditActivity : AppCompatActivity(),EditNoteFragment.FinishActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_fragment_activity)
        val fragment = EditNoteFragment()
        val args = Bundle()
        args.putString("title",intent.getStringExtra("title"))
        args.putString("noteDescription",intent.getStringExtra("noteDescription"))
        args.putInt("id",intent.getIntExtra("id",0))
        args.putBoolean("checked",intent.getBooleanExtra("checked",false))
        fragment.arguments = args
        supportFragmentManager.beginTransaction().add(R.id.editNoteContainer,fragment).commit()
    }

    override fun finishActivity() {
        finish()
    }
}
