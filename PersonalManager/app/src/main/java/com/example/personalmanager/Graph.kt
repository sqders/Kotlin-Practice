package com.example.personalmanager

import android.app.Application
import com.example.personalmanager.service.ListIteratorNoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Graph(private val app: Application) {
    val coroutineScope by lazy { CoroutineScope(Dispatchers.IO) }

    val dataManager by lazy { ListIteratorNoteRepository() }
}
class PersonalManagerApp(): Application(){
    val graph by lazy { Graph(this) }
    override fun onCreate() {
        super.onCreate()
    }
}