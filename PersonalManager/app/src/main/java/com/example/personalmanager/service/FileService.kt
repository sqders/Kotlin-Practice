package com.example.personalmanager.service

import com.example.personalmanager.model.Note
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.Serializable

class FileService{
    fun read(fileName: String): String {
        val file = File(fileName)
        if(!file.exists()) {
            val json = Json
            return json.encodeToString(ArrayList<Note>())
        }
        val bufferedReader = file.bufferedReader()
        val text = bufferedReader.readLines()
        val textString = ""
        text.forEach(textString::plus)
        return textString
    }

    fun write(fileName: String, writeText:String){
        val file = File(fileName)
        if(!file.exists())
            file.createNewFile()
        file.writeText(writeText)
    }
}