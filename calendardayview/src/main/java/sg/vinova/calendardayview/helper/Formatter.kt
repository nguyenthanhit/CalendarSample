package sg.vinova.calendardayview.helper

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.text.SimpleDateFormat
import java.util.*

/* *
 *  Created by JAY on 01/08/2018
 */

object Formatter {
    private const val PATTERN_TIME_12 = "hh:mm a"
    private const val PATTERN_TIME_24 = "HH:MM"

    @SuppressLint("SimpleDateFormat")
    fun convertHours(context: Context, dateTime: DateTime): String = dateTime.toString(getHourPattern(context))

    fun getHourPattern(context: Context) =  PATTERN_TIME_12

    fun getDateTimeFromTS(ts: Int) = DateTime(ts * 1000L, DateTimeZone.getDefault())

}