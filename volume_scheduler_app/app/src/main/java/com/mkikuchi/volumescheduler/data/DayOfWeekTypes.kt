package com.mkikuchi.volumescheduler.data

import androidx.annotation.IntDef

class DayOfWeekTypes {
    @IntDef(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY)
    @Retention(AnnotationRetention.SOURCE)
    annotation class DayOfWeekType

    companion object {
        const val SUNDAY = 0
        const val MONDAY = 1
        const val TUESDAY = 2
        const val WEDNESDAY = 3
        const val THURSDAY = 4
        const val FRIDAY = 5
        const val SATURDAY = 6
    }
}