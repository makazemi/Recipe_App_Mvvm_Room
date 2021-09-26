package com.makazemi.cooking.model


import android.os.Parcelable
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Food(
    val name:String,
    val recipe:String,
    val image:String,
    val category_key:Int,
    val category_name:String,
    @TypeConverters(ConvertersRawMaterial::class)
    val raw_material:List<RawMaterial>
) : Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id:Int=0

    override fun toString(): String {
        return "id=$id,name=$name"
    }
}

class ConvertersRawMaterial{
    @TypeConverter
    fun fromModelList(model: List<RawMaterial?>?): String? {
        val type = object : TypeToken<List<RawMaterial?>?>() {}.type
        return Gson().toJson(model, type)
    }

    @TypeConverter
    fun toModelList(modelString: String?): List<RawMaterial>? {
        val type = object : TypeToken<List<RawMaterial?>?>() {}.type
        return Gson().fromJson<List<RawMaterial>>(modelString, type)
    }
}
