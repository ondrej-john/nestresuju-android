package cz.nestresuju.common.extensions

import android.content.Context
import cz.nestresuju.R
import cz.nestresuju.common.extensions.DateTimeFormatPatterns.DAY_WITH_WEEKDAY_AND_YEAR
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

/**
 * Extensions for manipulation with date and time.
 */

object DateTimeFormatPatterns {

    const val DAY_WITH_WEEKDAY_AND_YEAR = "EEEE d. M. YYYY"
}

fun LocalDate.formatDay(context: Context): String = when {
    equals(LocalDate.now().minusDays(1)) -> context.getString(R.string.general_date_yesterday)
    equals(LocalDate.now()) -> context.getString(R.string.general_date_today)
    equals(LocalDate.now().plusDays(1)) -> context.getString(R.string.general_date_tomorrow)
    else -> format(DateTimeFormatter.ofPattern(DAY_WITH_WEEKDAY_AND_YEAR))
}