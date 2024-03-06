package com.example.personalmanager.service

import com.example.personalmanager.model.Note

interface DataManager {
    fun next() : Note
    fun previous() : Note
    fun add(newNote:Note)
    fun save(editNote:Note)
    fun destroy()
    fun getMaxId(): Int
    fun getCurrentIndex(): Int
    fun setCurrentIndex(newIndex:Int)
    fun getNoteList():List<Note>

}