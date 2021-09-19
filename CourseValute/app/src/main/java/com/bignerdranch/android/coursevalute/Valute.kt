package com.bignerdranch.android.coursevalute

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListJson (@SerializedName("Date") var Date : String = "",
                     @SerializedName("PreviousDate") var PreviousDate : String = "",
                     @SerializedName("PreviousURL") var PreviousURL : String = "",
                     @SerializedName("Timestamp") var Timestamp : String = "",
                     @SerializedName("Valute") var Valute : Map<String, Valute>
): Serializable
@Entity(tableName = "valute-list")
data class Valute (@PrimaryKey @SerializedName("ID") var id: String,
                   @SerializedName("NumCode")val numCode: String,
                   @SerializedName("CharCode")val charCode: String,
                   @SerializedName("Nominal")val nominal: Int,
                   @SerializedName("Name")val name: String,
                   @SerializedName("Value")val value: Double,
                   @SerializedName("Previous")val previous: Double
): Serializable