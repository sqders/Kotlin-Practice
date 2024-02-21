package com.example.personalmanager.service

import java.io.File

class FileService{
    fun read(fileName: String):String{
        val file = File(fileName)
        if(!file.exists())
            return "[]"
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