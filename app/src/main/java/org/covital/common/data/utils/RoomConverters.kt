package org.covital.common.data.utils

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.covital.common.domain.model.Gender
import org.covital.common.domain.model.HeightSystem
import org.covital.common.domain.model.SkinTone
import org.covital.common.domain.model.WeightSystem
import org.threeten.bp.Instant

class RoomConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return if (value == null) null else Instant.ofEpochMilli(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? {
        return date?.toEpochMilli()
    }

    @TypeConverter
    fun fromSkinTone(value: Int?): SkinTone? {
        return if (value == null) null else SkinTone.fromId(value)
    }

    @TypeConverter
    fun dateToSkinTone(skinTone: SkinTone?): Int? {
        return skinTone?.id
    }

    @TypeConverter
    fun fromGender(value: Int?): Gender? {
        return if (value == null) null else Gender.fromId(value)
    }

    @TypeConverter
    fun dateToGender(gender: Gender?): Int? {
        return gender?.ordinal
    }

    @TypeConverter
    fun fromWeightSystem(value: Int?): WeightSystem? {
        return if (value == null) null else WeightSystem.fromId(value)
    }

    @TypeConverter
    fun dateToWeightSystem(system: WeightSystem?): Int? {
        return system?.ordinal
    }

    @TypeConverter
    fun fromHeightSystem(value: Int?): HeightSystem? {
        return if (value == null) null else HeightSystem.fromId(value)
    }

    @TypeConverter
    fun dateToHeightSystem(system: HeightSystem?): Int? {
        return system?.ordinal
    }

    @TypeConverter
    fun fromListOfStrings(value: List<String>?): String? {
        return value?.joinToString("|")
    }

    @TypeConverter
    fun toListOfStrings(value: String?): List<String>? {
        return value?.split("|")
    }

    @TypeConverter
    fun fromSetOfStrings(value: Set<String>?): String? {
        return value?.joinToString("|")
    }

    @TypeConverter
    fun toSetOfStrings(value: String?): Set<String>? {
        return value?.split("|")?.toSet()
    }

    @TypeConverter
    fun fromMapToJson(content: Map<String, String>?): String? {
        if (content == null) {
            return null
        }
        return try {
            val type = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
            Moshi.Builder().build()
                .adapter<Map<String, String>>(type)
                .lenient()
                .toJson(content)
        } catch (ex: Throwable) {
            null
        }
    }

    @TypeConverter
    fun fromJsonToMap(value: String?): Map<String, String>? {
        if (value?.isNotBlank() != true) {
            return null
        }
        return try {
            val type = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
            Moshi.Builder()
                .build()
                .adapter<Map<String, String>>(type)
                .lenient()
                .fromJson(value) as? Map<String, String>
        } catch (ex: Throwable) {
            null
        }
    }
}
