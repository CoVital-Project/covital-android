package org.covital.common.domain.model

import androidx.annotation.ColorRes
import org.covital.R

enum class SkinTone(val id: Int, @ColorRes val colorRes: Int) {
    Lightest(0, R.color.skin_tone_0),
    Lighter(1, R.color.skin_tone_1),
    Light(2, R.color.skin_tone_2),
    Medium(3, R.color.skin_tone_3),
    Dark(4, R.color.skin_tone_4),
    Darker(5, R.color.skin_tone_5),
    Darkest(6, R.color.skin_tone_6);

    companion object {
        @JvmStatic fun fromId(id: Int): SkinTone {
            return values().firstOrNull { it.id == id } ?: Medium
        }
    }
}
