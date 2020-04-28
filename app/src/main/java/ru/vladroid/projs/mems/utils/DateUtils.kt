package ru.vladroid.projs.mems.utils

import android.content.res.Resources
import ru.vladroid.projs.mems.R
import java.util.*

class DateUtils {

    companion object {
        fun parseDateTimeStamp(time: Long, resources: Resources): String {
            val memDate = secToDays(time)
            val now = secToDays(Date().time / 1000)

            val dif = now - memDate
            val difLastNum = dif % 10
            val difTwoLastNum = dif % 100
            if (dif == 0)
                return resources.getString(R.string.date_today)
            else if (dif == 1)
                return resources.getString(R.string.date_yesterday)
            if (difTwoLastNum in 11..19)
                return resources.getString(R.string.date_many3, dif)
            return when (difLastNum) {
                1 -> resources.getString(R.string.date_many1, dif)
                2, 3, 4 -> resources.getString(R.string.date_many2, dif)
                else -> resources.getString(R.string.date_many3, dif)
            }
        }

        private fun secToDays(sec: Long) = (sec / (60 * 60 * 24)).toInt()
    }
}