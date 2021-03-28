package com.example.noteapp

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate

class MainActivity:AppCompatActivity()
{
    companion object
    {
        const val editFlag=0
        const val newFlag=1
    }
    private var noteArrayAdapter:NoteArrayAdapter?=null
    private var listView:ListView?=null
    private var requestCode=0
    private var noteRepository:NoteRepository?=null
    override fun onCreate(savedInstanceState:Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        val addNewButton=findViewById<Button>(R.id.addNew)
        listView=findViewById(R.id.notes)
        noteRepository=NoteRepository(applicationContext)
        listView!!.onItemClickListener=AdapterView.OnItemClickListener()
        {
            parent: AdapterView<*>, _: View, i: Int, _: Long ->
            val intent=Intent(applicationContext,NoteEdit::class.java)
            intent.putExtra("id",noteArrayAdapter!!.getItem(i)!!.id)
            intent.putExtra("operation",editFlag)
            startActivityForResult(intent,requestCode)
        }
        addNewButton.setOnClickListener{
            val intent=Intent(applicationContext,NoteEdit::class.java)
            intent.putExtra("operation",newFlag)
            startActivityForResult(intent,requestCode)
        }
        registerForContextMenu(listView)
        listNotes()
    }
    private fun listNotes()
    {
        noteArrayAdapter=NoteArrayAdapter(this,noteRepository!!.findAll())
        listView!!.adapter=noteArrayAdapter
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?)
    {
        super.onCreateContextMenu(menu, v, menuInfo)
        if(v==listView)
        {
            menuInflater.inflate(R.menu.context_menu,menu)
            menu!!.setHeaderTitle("Select the Action")
        }
    }
    override fun onContextItemSelected(item: MenuItem): Boolean
    {
        if(item.itemId==R.id.delete)
        {
            val menuInfo=item.menuInfo as AdapterView.AdapterContextMenuInfo
            val position=menuInfo.position
            val note=noteArrayAdapter!!.getItem(position)
            if(noteRepository!!.deleteById(note!!.id))
            {
                Toast.makeText(applicationContext,"Deleted",Toast.LENGTH_SHORT).show()
                listNotes()
            }
            else
                Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
            return true
        }
        else if(item.itemId==R.id.copy)
        {
            val menuInfo=item.menuInfo as AdapterView.AdapterContextMenuInfo
            val position=menuInfo.position
            val note=noteArrayAdapter!!.getItem(position)
            val clipboardManager=getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData=ClipData.newPlainText("text",note!!.text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(applicationContext,"Copied",Toast.LENGTH_SHORT).show()
            return true
        }
        else
            return super.onContextItemSelected(item)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==this.requestCode)
        {
            if(resultCode==Activity.RESULT_OK)
            {
                listNotes()
                Toast.makeText(applicationContext,"Successful",Toast.LENGTH_SHORT).show()
            }
        }
    }
}