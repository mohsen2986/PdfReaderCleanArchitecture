package com.example.core.domain

import sun.invoke.empty.Empty
import java.io.Serializable

data class Document(
    val url: String ,
    val name: String ,
    val size: Int ,
    val thumbnail: String
): Serializable{
    companion object{
        val EMPTY = Document("" , "" , 0 , "")
    }
}