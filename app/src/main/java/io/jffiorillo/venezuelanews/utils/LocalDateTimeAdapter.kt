package io.jffiorillo.venezuelanews.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class LocalDateTimeAdapter : JsonAdapter<LocalDateTime>() {
  override fun fromJson(reader: JsonReader?): LocalDateTime? {
    val dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.getDefault())
    var date: LocalDateTime? = null
    val nextString = reader?.nextString()
    if (!nextString.isNullOrEmpty()) {
      try {
        date = LocalDateTime.parse(nextString, dateFormat)
      } catch (e: ParseException) {
        Timber.e(e, "Error parsing message date")
      }

    }
    return date
  }

  override fun toJson(writer: JsonWriter?, date: LocalDateTime?) {
    writer?.value(SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(date))
  }


  companion object {
    private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
  }
}