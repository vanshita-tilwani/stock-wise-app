package model.utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    Period period = Period.ZERO;
    // TODO :
    CalendarScale calendarScale = CalendarScale.YEARLY;
    LocalDate lastWorkingTo = getLastWorkingDay(to.plusDays(1));
    if (isBetweenScale(ChronoUnit.DAYS, from, lastWorkingTo, 1)) {
      period = Period.ofDays(1);
      calendarScale = CalendarScale.DAYS;
    } else if (isBetweenScale(ChronoUnit.WEEKS, from, lastWorkingTo, 1)) {
      period = Period.ofWeeks(1);
      calendarScale = CalendarScale.WEEKS;
    } else if (isBetweenScale(ChronoUnit.MONTHS, from, lastWorkingTo, 1)) {
      period = Period.ofMonths(1);
      calendarScale = CalendarScale.MONTHS;
    } else if (isBetweenScale(ChronoUnit.YEARS, from, lastWorkingTo, 4)) {
      period = Period.ofMonths(3);
      calendarScale = CalendarScale.QUARTERLY;
    } else if (isBetweenScale(ChronoUnit.YEARS, from, lastWorkingTo, 1)) {
      period = Period.ofYears(1);
      calendarScale = CalendarScale.YEARLY;
    }
    return initialize(calendarScale, from, to);
    /*return from.datesUntil(lastWorkingTo, period)
            .map(e -> getLastWorkingDay(e))
            .filter(e -> e.isAfter(from)
                    && e.getDayOfWeek() != DayOfWeek.SATURDAY
                    && e.getDayOfWeek() != DayOfWeek.SUNDAY)
            .collect(Collectors.toList());*/
  }

  private static boolean isBetweenScale(ChronoUnit chronoUnit, LocalDate from, LocalDate to, int multiplier) {
    long dates = chronoUnit.between(from, to) * multiplier;
    return dates <= MAX_SCALE && dates >= MIN_SCALE;
  }

  private static LocalDate getLastWorkingDay(LocalDate to) {
    if (to.getDayOfWeek() == DayOfWeek.SATURDAY || to.getDayOfWeek() == DayOfWeek.SUNDAY) {
      return to.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
    }
    return to;
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
    if (type != CalendarScale.DAYS) {
      from = getLastWorkingDay(type, from);
    }
    for (LocalDate date = from; date.isBefore(to); ) {
      dates.add(date);
      date = type.add(date);
      if (type != CalendarScale.DAYS) {
        date = getLastWorkingDay(type, date);
      }
    }
    return dates;
  }

  /**
   * Returns the last working day for the given date according to the scale.
   *
   * @param type the scale i.e. weekly, quarterly, monthly etc.
   * @param date the date for while the last working day needs to be determined.
   * @return the last working day for the specified date and scale.
   */
  private static LocalDate getLastWorkingDay(CalendarScale type, LocalDate date) {
    LocalDate lastDate = date;
    if (type == CalendarScale.MONTHS || type == CalendarScale.YEARLY) {
      lastDate = getLastWorkingDayForMonthAndYear(type, date);
    } else if (type == CalendarScale.WEEKS) {
      lastDate = getLastWorkingDayForWeek(date);
    }
    return lastDate;
  }

  /**
   * Returns the last working day for the given date for a month or a year.
   *
   * @param type the scale i.e. monthly or yearly.
   * @param date the date for while the last working day needs to be determined.
   * @return the last working day for the month/year for the specified date.
   */
  private static LocalDate getLastWorkingDayForMonthAndYear(CalendarScale type, LocalDate date) {

    LocalDate lastDate = type == CalendarScale.YEARLY ?
            date.with(TemporalAdjusters.lastDayOfYear()) :
            date.with(TemporalAdjusters.lastDayOfMonth());
    while (!isWorkingDay(lastDate)) {
      lastDate = lastDate.minusDays(1);
    }
    return lastDate;
  }

  /**
   * Returns the last working day for the week.
   *
   * @param date the date for while the last working day needs to be determined.
   * @return the last working day for the week for the specified date.
   */
  private static LocalDate getLastWorkingDayForWeek(LocalDate date) {
    while (!isFriday(date)) {
      date = date.plusDays(1);
    }
    return date;
  }

  /**
   * Checks if the date is on a working day.
   *
   * @param date the date which needs to be determined.
   * @return if the date falls on a working day
   */
  private static boolean isWorkingDay(LocalDate date) {
    DayOfWeek day = date.getDayOfWeek();
    return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
  }

  /**
   * Checks if the date falls on the Friday.
   *
   * @param date the date which needs to be determined.
   * @return if the date falls on the friday
   */
  private static boolean isFriday(LocalDate date) {
    DayOfWeek day = date.getDayOfWeek();
    return day == DayOfWeek.FRIDAY;
  }
}
