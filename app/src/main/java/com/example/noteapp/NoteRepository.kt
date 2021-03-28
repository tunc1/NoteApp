package com.example.noteapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import java.util.*
import kotlin.collections.ArrayList

class NoteRepository(context:Context)
{
    private val database:Database=Database(context)
    fun findAll():List<Note>
    {
        val cursor:Cursor=database.writableDatabase.rawQuery("select * from Note Order By created DESC",null)
        val notes=ArrayList<Note>()
        while(cursor.moveToNext())
        {
            val id=cursor.getInt(0)
            val text=cursor.getString(1)
            val created=Date(cursor.getLong(2))
            notes.add(Note(id,text,created))
        }
        cursor.close()
        return notes
    }
    fun findById(id:Int):Note
    {
        val cursor:Cursor=database.writableDatabase.rawQuery("select * from Note where id=?",arrayOf(id.toString()))
        val note=Note()
        note.id=id
        while(cursor.moveToNext())
        {
            note.text=cursor.getString(1)
            note.created=Date(cursor.getLong(2))
        }
        cursor.close()
        return note
    }
    fun deleteById(id:Int):Boolean
    {
        return database.writableDatabase.delete("Note","id=?",arrayOf(id.toString()))>=0
    }
    fun save(note:Note):Boolean
    {
        val values=ContentValues()
        values.put("text",note.text)
        values.put("created",Date().time)
        return database.writableDatabase.insert("Note",null,values)>=0
    }
    fun update(note:Note):Boolean
    {
        val values=ContentValues()
        values.put("text",note.text)
        values.put("created",note.created?.time)
        return database.writableDatabase.update("Note",values,"id=?",arrayOf(note.id.toString()))>=0
    }
}