package com.example.noteapp

import java.util.*

class Note
{
    var id:Int=0
    var text:String=""
    var created:Date?=null
    constructor(id:Int,text:String,created:Date)
    {
        this.id=id
        this.text=text
        this.created=created
    }
    constructor()
}