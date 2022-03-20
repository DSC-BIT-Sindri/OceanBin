package com.nipun.oceanbin.core

import android.content.Context
import android.content.Intent
import android.location.Address
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import com.google.android.gms.maps.model.LatLng
import com.nipun.oceanbin.feature_oceanbin.feature_search.model.SearchResultModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


/*
 * This function is an Path extension function, with help of this function
 * we can join two points by curve, so that we can avoid straight line joining
 */
fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x,
        from.y,
        abs(from.x + to.x) / 2f,
        abs(from.y + to.y) / 2f
    )
}

fun getMainScreenCurve(width: Int, height: Int): Path {
    val point1 = Offset(-80f, height * 0.07f)
    val point2 = Offset(width * .2f, height * 0.09f)
    val point3 = Offset(width * .5f, -(height*0f))
    val point4 = Offset(width * 0.8f, height * 0.09f)
    val point5 = Offset(width * 1.2f + 50f, height * 0.05f)
    val point6 = Offset(width * 1.2f + 50f, height * 1.2f + 100f)
    val point7 = Offset(-80f, height * 1.2f + 100f)

    return Path().apply {
        moveTo(point1.x, point1.y)
        standardQuadFromTo(point1, point2)
        standardQuadFromTo(point2, point3)
        standardQuadFromTo(point3, point4)
        standardQuadFromTo(point4, point5)
        lineTo(point6.x, point6.y)
        lineTo(point7.x, point7.y)
        close()
    }
}

fun getAppSplashPath(width: Int, height: Int): Path {
    // These points are the coordinate where 0,0 is our screens top left corner.
    val point1 = Offset(0f, height * 0.3f)
    val point2 = Offset(0.1f * width, height * 0.14f)
    val point3 = Offset(0.55f * width, height * 0.19f)
    val point4 = Offset(1.1f * width, height * 0.02f)
    val point5 = Offset(1.15f * width, height * 0.5f)
    val point5a = Offset(1.25f * width, height * 0.7f)
    val point6 = Offset(0.9f * width, height * 0.8f)
    val point7 = Offset(0.7f * width, height * 0.97f)
    val point8 = Offset(0.25f * width, height * 0.9f)
    val point9 = Offset(0.1f, height * 1f)
    val point10 = Offset(0f, height * 1f)

    // With the help of above coordinate we can draw a closed path
    return Path().apply {
        moveTo(point1.x, point1.y)
        standardQuadFromTo(point1, point2)
        standardQuadFromTo(point2, point3)
        standardQuadFromTo(point3, point4)
        standardQuadFromTo(point4, point5)
        lineTo(point5a.x, point5a.y)
        standardQuadFromTo(point5a, point6)
        standardQuadFromTo(point6, point7)
        standardQuadFromTo(point7, point8)
        standardQuadFromTo(point8, point9)
        lineTo(point10.x, point10.y)
        close()
    }
}

fun Context.openSettings() {

    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val uri = Uri.fromParts("package",packageName,null)
    intent.data = uri
    startActivity(intent)
}

fun Int.getTimeInString(): String {
    val sdf = SimpleDateFormat("HH:mm:ss dd MMMM yyyy",Locale.ENGLISH)
    return sdf.format(this*1000L).substring(0,5)
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Address.toSearchModel() : SearchResultModel{
    val latLng = LatLng(this.latitude,this.longitude)
    return SearchResultModel(
        latLng = latLng,
        feature = featureName,
        locality = locality,
        subAdmin = subAdminArea,
        addressLine = getAddressLine(0),
        admin = adminArea
    )
}
//
//fun AutocompletePrediction.toAutocompletModel() : AutoCompleteModel{
//    return AutoCompleteModel(
//        id = placeId,
//        dist = distanceMeters,
//        primaryText = getPrimaryText(null).toString(),
//        fullText = getFullText(null).toString()
//    )
//}
