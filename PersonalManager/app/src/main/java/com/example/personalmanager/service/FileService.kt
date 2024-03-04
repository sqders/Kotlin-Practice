package com.example.personalmanager.service

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import android.content.ContentValues

class FileService(private val context: Context) {

    suspend fun write(fileName: String, writeText: String) {
        withContext(Dispatchers.IO) {
            val uri = createFile(fileName, "text/plain")
            uri?.let { uri ->
                try {
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        outputStream.write(writeText.toByteArray())
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    suspend fun read(fileName: String): String {
        return withContext(Dispatchers.IO) {
            val uri = getFileUri(fileName)
            uri?.let { uri ->
                try {
                    context.contentResolver.openInputStream(uri)?.use { inputStream ->
                        return@withContext inputStream.bufferedReader().use { reader ->
                            reader.readText()
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    return@withContext "[]"
                }
            }
            return@withContext "[]"
        }
    }

    private fun createFile(fileName: String, mimeType: String): Uri? {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
        }
        return context.contentResolver.insert(MediaStore.Files.getContentUri("external"), values)
    }

    private fun getFileUri(fileName: String): Uri? {
        val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fileName)
        val cursor = context.contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            null,
            selection,
            selectionArgs,
            null
        )
        cursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                val uriIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                return Uri.withAppendedPath(
                    MediaStore.Files.getContentUri("external"),
                    cursor.getString(uriIndex)
                )
            }
        }
        return null
    }
}
