package model.utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Calendar used to determine the period to analyze the
 * time period.
 */
public enum CalendarScale {

  DAYS {
    LocalDate add(LocalDate date) {
      return date.plusDays(1);
    }

    LocalDate getLastWorkingDay(LocalDate date) {
      LocalDate nextWorkingDay = date;
      while (!isWorkingDay(nextWorkingDay)) {
        nextWorkingDay = nextWorkingDay.plusDays(1);
      }
      return nextWorkingDay;
    }
  },

  WEEKS {
    LocalDate add(LocalDate date) {
      return date.plusDays(7);
    }

    LocalDate getLastWorkingDay(LocalDate date) {
      while (!isFriday(date)) {
        date = date.plusDays(1);
      }
      return date;
    }
  },
  MONTHS {
    LocalDate add(LocalDate date) {
      return date.plusMonths(1);
    }
    LocalDate getLastWorkingDay(LocalDate date) {
      LocalDate lastDate = date.with(TemporalAdjusters.lastDayOfMonth());
      return backToLastWorkingDay(lastDate);
    }
  },
  QUARTERLY {
    LocalDate add(LocalDate date) {
      return date.plusMonths(3);
    }
    LocalDate getLastWorkingDay(LocalDate date) {
      LocalDate lastDate = date.with(TemporalAdjusters.lastDayOfMonth());
      return backToLastWorkingDay(lastDate);
    }
  },

  YEARLY {
    LocalDate add(LocalDate date) {
      return date.plusYears(1);
    }
    LocalDate getLastWorkingDay(LocalDate date) {
      LocalDate lastDate = date.with(TemporalAdjusters.lastDayOfYear());
      return backToLastWorkingDay(lastDate);
    }
  };

  abstract LocalDate add(LocalDate date);
  abstract LocalDate getLastWorkingDay(LocalDate date);

  private static boolean isWorkingDay(LocalDate date) {
    DayOfWeek day = date.getDayOfWeek();
    return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
  }

  private static boolean isFriday(LocalDate date) {
    DayOfWeek day = date.getDayOfWeek();
    return day == DayOfWeek.FRIDAY;
  }

  private static LocalDate backToLastWorkingDay(LocalDate date) {
    LocalDate lastDate = date;
    while (!isWorkingDay(lastDate)) {
      lastDate = lastDate.minusDays(1);
    }
    return lastDate;
  }
}
