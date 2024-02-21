package com.example.personalmanager.service

import com.example.personalmanager.model.Note
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListIteratorNoteRepository : DataManager {
    private var iterator: Int = 0

    @Serializable
    private val noteList: ArrayList<Note>
    private val fileName = "temp_note_file.txt"

    constructor() {
        val fileService = FileService()
        val json = Json
        noteList = json.decodeFromString(fileService.read(fileName))
    }

    override fun next(): Note {
        iterator += 1
        if (noteList.size < iterator) {
            iterator = 0
        }
        return noteList[iterator]
    }

    override fun previous(): Note {
        iterator -= 1
        if (iterator < 0) {
            iterator = this.noteList.last().id
        }
        return noteList[iterator]
    }

    override fun add(newNote: Note) {
        newNote.id = noteList.size
        noteList.add(newNote)
    }

    override fun save(editNote: Note) {
        noteList[iterator] = editNote
    }

    override fun destroy() {
        dump()
    }

    private fun dump() {
        val fileService = FileService()
        val json = Json
        fileService.write(fileName, json.encodeToString(noteList))
    }
}