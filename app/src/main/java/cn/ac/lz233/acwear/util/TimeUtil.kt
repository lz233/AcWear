package cn.ac.lz233.acwear.util

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun getTime(unixTimestamp: Long,formats:String="HH:mm"):String{
    val simpleDateFormat=SimpleDateFormat(formats)
    val calendar=Calendar.getInstance()
    calendar.time=Date(unixTimestamp*1000)
    calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR)+8)
    return simpleDateFormat.format(calendar.time)
}