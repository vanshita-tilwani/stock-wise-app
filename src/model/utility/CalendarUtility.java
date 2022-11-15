package model.utility;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Calendar utility used to perform utility methods on calendar, period of time such as
 * get all working days for a period of time.
 */
public class CalendarUtility {

  private static final int MAX_SCALE = 30;
  private static final int MIN_SCALE = 5;

  /**
   * Returns equally spaced dates from the start and end range of the period.
   *
   * @param from the start date of the time range.
   * @param to   the end date of the time range.
   * @return the equally spaced periods of time i.e. monthly, daily, weekly, etc.
   */
  public static List<LocalDate> getWorkingDays(LocalDate from, LocalDate to) {
    CalendarScale calendarScale = CalendarScale.YEARLY;
    if (isBetweenScale(ChronoUnit.DAYS, from, to, 1)) {
      calendarScale = CalendarScale.DAYS;
    } else if (isBetweenScale(ChronoUnit.WEEKS, from, to, 1)) {
      calendarScale = CalendarScale.WEEKS;
    } else if (isBetweenScale(ChronoUnit.MONTHS, from, to, 1)) {
      calendarScale = CalendarScale.MONTHS;
    } else if (isBetweenScale(ChronoUnit.YEARS, from, to, 4)) {
      calendarScale = CalendarScale.QUARTERLY;
    } else if (isBetweenScale(ChronoUnit.YEARS, from, to, 1)) {
      calendarScale = CalendarScale.YEARLY;
    }
    return initialize(calendarScale, from, to);
  }

  private static boolean isBetweenScale(ChronoUnit chronoUnit, LocalDate from, LocalDate to,
                                        int multiplier) {
    long dates = chronoUnit.between(from, to) * multiplier;
    return dates <= MAX_SCALE && dates >= MIN_SCALE;
  }

  /**
   * Initialize the set of dates in equally spaced interval.
   *
   * @param type the type of the scale.
   * @param from the start date of the period.
   * @param to   the end date of the period.
   * @return the list of dates using the scale from start date to end.
   */
  private static List<LocalDate> initialize(CalendarScale type, LocalDate from, LocalDate to) {

    List<LocalDate> dates = new ArrayList<>();
    for (LocalDate date = type.getLastWorkingDay(from); to.isAfter(date); ) {
      dates.add(date);
      date = type.add(date);
      date = type.getLastWorkingDay(date);
    }
    if(!dates.contains(to)) {
      dates.add(to);
    }
    return dates;
  }


}
