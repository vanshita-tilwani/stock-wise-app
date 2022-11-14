package model.utility;

import java.time.LocalDate;
import java.time.Period;

/**
 * Calendar used to determine the period to analyze the
 * time period.
 */
public enum CalendarScale {

  DAYS {
    @Override
    Period getPeriod() {
      return Period.ofDays(1);
    }
    LocalDate add(LocalDate date) {
      return date.plusDays(1);
    }
  },

  WEEKS {
    @Override
    Period getPeriod() {
      return Period.ofWeeks(1);
    }
    LocalDate add(LocalDate date) {
      return date.plusDays(7);
    }
  },
  MONTHS {
    @Override
    Period getPeriod() {
      return Period.ofMonths(1);
    }
    LocalDate add(LocalDate date) {
      return date.plusMonths(1);
    }
  },
  QUARTERLY {
    @Override
    Period getPeriod() {
      return Period.ofMonths(3);
    }
    LocalDate add(LocalDate date) {
      return date.plusMonths(3);
    }
  },

  YEARLY {
    @Override
    Period getPeriod() {
      return Period.ofYears(1);
    }
    LocalDate add(LocalDate date) {
      return date.plusYears(1);
    }
  };

  abstract Period getPeriod();
  abstract LocalDate add(LocalDate date);
}
