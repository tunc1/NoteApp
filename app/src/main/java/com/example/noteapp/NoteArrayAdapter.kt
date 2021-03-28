package com.example.noteapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class NoteArrayAdapter(context: Context,notes:List<Note>):ArrayAdapter<Note>(context,0,notes)
{
    private val dateFormat:SimpleDateFormat=SimpleDateFormat("dd:MM:yyyy HH:mm",Locale.getDefault())
    override fun getView(position: Int, convertView: View?, parent: ViewGroup):View
    {
        val note=getItem(position)
        val view=LayoutInflater.from(context).inflate(R.layout.note_layout,parent,false)
        val textTextView=view.findViewById<TextView>(R.id.note_text)
        val createdTextView=view.findViewById<TextView>(R.id.created)
        var text=note!!.text
        if(text.contains("\n"))
            text=text.split("\n")[0]
        if(text.length>20)
            text=text.substring(0,20)+"..."
        textTextView.text=text
        createdTextView.text=dateFormat.format(note.created as Date)
        return view
    }
}