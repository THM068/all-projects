package com.ht.managemybills;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class LocalDateTest {

  @Test
  void localeTest() {
    LocalDate now = LocalDate.now();
    DayOfWeek dayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
    LocalDate firstDayOfWeek = now.with(dayOfWeek);
    System.out.println("First day of the week: " + firstDayOfWeek);

    var daysOfThisWeek = Stream.iterate(firstDayOfWeek, date -> date.plusDays(1))
        .limit(7)
        .toList();
    System.out.println("Days of this week: " + daysOfThisWeek);
  }
}
