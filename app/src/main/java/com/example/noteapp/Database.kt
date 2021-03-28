package com.example.noteapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context:Context):SQLiteOpenHelper(context,"db",null,1)
{
    override fun onCreate(db:SQLiteDatabase)
    {
        db.execSQL("Create Table IF NOT EXISTS Note(id INTEGER PRIMARY KEY AUTOINCREMENT,text String,created long)")
    }
    override fun onUpgrade(db:SQLiteDatabase?,oldVersion:Int,newVersion:Int){}
}