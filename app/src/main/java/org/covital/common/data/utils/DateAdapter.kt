package org.covital.common.data.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter

class DateAdapter {

    companion object {

        /**
         * Cleans up date time strings so [OffsetDateTime] can parse them.
         */
        @JvmStatic fun tryParsing(value: String): Instant? = try {
            val cleaned = value.replace("+0000", "Z").replace("+00:00", "Z")
            OffsetDateTime.parse(cleaned, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                .atZoneSameInstant(ZoneOffset.UTC)
                .toInstant()
        } catch (ex: Throwable) {
            null
        }
    }

    /**
     * We pad output strings with .000Z if they are short because [Instant.toString]
     * does not write milliseconds unless they are non-zero.
     */
    @ToJson fun toJson(value: Instant): String {
        val str = value.toString()
        return when (str.length) {
            20 -> str.substring(0, 19) + ".000Z"
            23 -> str
            else -> str // hope for the best?
        }
    }

    @FromJson fun fromJson(value: String): Instant? {
        return tryParsing(value)
    }
}
