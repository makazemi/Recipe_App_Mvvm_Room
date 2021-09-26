package com.makazemi.cooking.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class CategoryFood(
    val title:String,
    val image:String
) : Parcelable{

    @PrimaryKey(autoGenerate = true)
    var id:Int=0

    override fun toString(): String {
        return "id=$id,title=$title,image=$image"
    }
}
