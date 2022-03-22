package com.nipun.oceanbin.feature_oceanbin.feature_map.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.nipun.oceanbin.core.toTimeStamp
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class DropDownData(
    val localDate: LocalDate = LocalDate.now(),
    val showToUser: String = "",
    val timeMillis : Long = 32400L,
    val latitude : Double = 0.0,
    val longitude : Double = 0.0,
    val location : String = "",
    val time : String = ""
){
    fun getTimeStamp() : Long{
        return  localDate.toTimeStamp()+timeMillis-19800L
    }
}
