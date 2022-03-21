package com.nipun.oceanbin.core

import android.content.Context
import android.database.Cursor
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


object Constant {
    const val RATIONAL_KEY = "rational_key"
    const val Count_Key = "count_key"
    const val Max_Count = 3
    val Has_Location = "Has_location"

    const val API_KEY = "16f891a081653d2c21b4ec4e6ebaae3a"
    const val WEATHER_KEY = "weather_key"

    const val LAT = "lat"
    const val LON = "lon"
    const val NEWS = "news"

}