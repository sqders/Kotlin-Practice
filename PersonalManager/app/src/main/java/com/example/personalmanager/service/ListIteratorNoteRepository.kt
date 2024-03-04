package com.example.personalmanager.service

import android.content.Context
import android.util.Log
import com.example.personalmanager.model.Note
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListIteratorNoteRepository(val context: Context) : DataManager {
    private var iterator: Int = 0

    @Serializable
    private val noteList: ArrayList<Note>
    private val fileName = "temp_note_file.txt"

    init {
        val fileService = FileService(context)
        val json = Json
        noteList = json.decodeFromString(
            runBlocking { fileService.read(fileName) }
        )

    }

    override fun next(): Note {
        Log.v("NoteRepositoryDebug", "next $Note")
        iterator += 1
        if(noteList.size <= 0) {
            iterator = 0
          return Note()
        }
        else if (noteList.size <= iterator) {
            iterator = 0
        }
        val debugNote = noteList[iterator]
        Log.v("NoteRepositoryDebug", "next $debugNote")
        return noteList[iterator]
    }

    override fun previous(): Note {
        Log.v("NoteRepositoryDebug", "previous $Note")
        iterator -= 1

        if(noteList.size == 0) {
            return Note()
        }
        else if (iterator < 0) {
            iterator = this.noteList.last().id
        }
        var debugNote = noteList[iterator]
        Log.v("NoteRepositoryDebug", "previous $debugNote")
        return noteList[iterator]
    }

    override fun add(newNote: Note) {
        Log.v("NoteRepositoryDebug", "Add $newNote")
        noteList.add(newNote)
    }

    override fun save(editNote: Note) {
        Log.v("NoteRepositoryDebug", "Save $editNote")
        noteList[iterator] = editNote
    }

    override fun destroy() {
        dump()
    }

    private fun dump() {
        val fileService = FileService(context)
        val json = Json
        runBlocking { fileService.write(fileName, json.encodeToString(noteList)) }
    }

    override fun getCurrentIndex(): Int = iterator
    override fun setCurrentIndex(newIndex: Int) {
        iterator = newIndex
    }

    override fun getMaxId(): Int = noteList.size - 1
}