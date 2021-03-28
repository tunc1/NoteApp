package com.example.noteapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import java.util.*

class NoteEdit:AppCompatActivity()
{
    private var editText:EditText?=null
    private var noteRepository:NoteRepository?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)
        val saveButton=findViewById<Button>(R.id.save)
        editText=findViewById(R.id.editText)
        noteRepository=NoteRepository(applicationContext)
        if(isEditing())
        {
            val id=intent.getIntExtra("id",-1)
            val note=noteRepository!!.findById(id)
            editText!!.setText(note.text)
        }
        saveButton.setOnClickListener{
            val note=Note()
            note.created=Date()
            note.text=editText!!.text.toString().trim()
            if(isEditing())
            {
                note.id=intent.getIntExtra("id",-1)
                if(noteRepository!!.update(note))
                {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                else
                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
            }
            else
            {
                if(noteRepository!!.save(note))
                {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                else
                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun isEditing():Boolean
    {
        return intent.getIntExtra("operation",-1)==MainActivity.editFlag
    }
}