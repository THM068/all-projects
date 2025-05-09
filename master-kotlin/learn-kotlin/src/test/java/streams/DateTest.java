package streams;

import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import static java.lang.System.*;

public class DateTest {

    @Test
    public void dateTime() {
        Instant instant = Instant.now();
        Date date = new Date();
        out.println(String.format("instant value %s date value %s",instant, date));
    }

    @Test
    public void localDateTest() {
        LocalDate localDate = LocalDate.now();
        out.println("Time now is "+ localDate);
        LocalDateTime localDateTime = localDate.atTime(6, 15);
        out.println("Localtime is "+ localDateTime);
        out.println("Localtime now is " + localDate.atTime(LocalTime.now()));

        String stringDate = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String stringDateddMMyyyy = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        out.println("The string date is "+ stringDate);
        out.println("The string date with a pattern is "+ stringDateddMMyyyy);

    }

    @Test
    public void convertDateToLocalDate() {
        Date now = new Date();
        LocalDate localDate = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        String stringDate = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        out.println(stringDate);

        Date newDate = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Test
    public void t() throws Exception {
        for(int i =0; i < 11; i++) {
            Thread.sleep(1500);
            out.println(i);
        }
    }

    @Test
    public void period() {
        LocalDate startDate = LocalDate.of(2014,Month.JANUARY, 30);
        LocalDate endDate = LocalDate.of(2017, Month.JANUARY, 30);

        Period period = Period.between(startDate, endDate);

        out.println(period.isNegative());
        out.println(period.getYears());
        out.println(period.getMonths());
        out.println(period.getDays());

        Period p1 = Period.of(1,1,1);

        LocalDate ld = LocalDate.of(2015, 1, 1);
        Period p2 = Period.of(2, 13,1);

        LocalDate newDate = ld.plus(p2);
        out.println(newDate);

        String pattern = "";

    }

    @Test
    public void localeTest() {
        Locale locale = Locale.getDefault();
        out.println(locale);
        out.println(locale.getCountry());
        out.println(locale.getLanguage());
        out.println(locale.getDisplayCountry());
        out.println(locale.getDisplayCountry(Locale.GERMAN));
        new Locale.Builder().build();
    }

    @Test
    public void localeTest2() {
        Locale us = new Locale("en", "GB");
        Locale fr = new Locale("fr", "FR");
        printProperties(us);
        out.println("----");
        printProperties(fr);
    }

    private void printProperties(Locale locale) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Zoo", locale);
        out.println(resourceBundle.getString("hello"));
        out.println(resourceBundle.getString("open"));

        Set<String> keys = resourceBundle.keySet();

        keys.stream().map( k -> " " + resourceBundle.getString(k)).forEach(out::println);
    }

    @Test
    public void numberFormatTest() {
        int attendeesPerYear = 3_200_000;
        int attendeesPerMonth = attendeesPerYear / 12;

        NumberFormat us = NumberFormat.getNumberInstance(Locale.US);
        out.println(us.format(attendeesPerYear));

        NumberFormat germany = NumberFormat.getInstance(Locale.CANADA_FRENCH);
        out.println(germany.format(attendeesPerYear));
    }


}

class Person {

    public static final  class  PersonBuilder {

        public  PersonBuilder setName() {
            return this;
        }
    }
}