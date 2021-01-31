import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;


public class DateTime {

    public List<String> fridays13(){
        LocalDate start = LocalDate.of(2000,1,1);
        LocalDate end = LocalDate.now();

        List<String> fridays13 = new ArrayList<>();

        while (start.isBefore(end)){
            if (start.getDayOfWeek().equals(DayOfWeek.FRIDAY) && start.getDayOfMonth() == 13){
                String date = start.format(DateTimeFormatter.ofPattern("MMM yyyy"));
                fridays13.add(date);
            }
            start = start.plusDays(1);
        }
        return fridays13;
    }

    public List<YearMonth> endOnSundays(){
        LocalDate start = LocalDate.of(2000,1,1);
        LocalDate end = LocalDate.now();

        List<YearMonth> endOnSundays = new ArrayList<>();

        while (start.isBefore(end)){
            TemporalAdjuster temporalAdjuster = TemporalAdjusters.lastDayOfMonth();
            LocalDate lastDayOfMonth = start.with(temporalAdjuster);
            if (lastDayOfMonth.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                YearMonth yearMonth = YearMonth.from(start);
                endOnSundays.add(yearMonth);
            }
            start = start.plusMonths(1);
        }
        return endOnSundays;
    }

    public List<Year> birthdaysOnSaturdays(LocalDate birthday){
        LocalDate start = birthday;
        LocalDate end = LocalDate.now();

        List<Year> birthdaysOnSaturdays = new ArrayList<>();

        while (start.isBefore(end)){
            if (start.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                Year year= Year.from(start);
                birthdaysOnSaturdays.add(year);
            }
            start = start.plusYears(1);
        }
        return birthdaysOnSaturdays;
    }

    public List<MonthDay> daysNotWith24Hours(Year year, ZoneId zoneId) {
        List<MonthDay> daysNotWith24Hours = new ArrayList<>();
        ZonedDateTime currentDay = ZonedDateTime.of(
                LocalDateTime.of(year.get(ChronoField.YEAR), 1, 1, 0, 0), zoneId);
        ZonedDateTime nextDay = currentDay.plusDays(1);
        long daysInYear = ChronoUnit.DAYS.between(currentDay, currentDay.plusYears(1));
        for (int day = 1; day < daysInYear; day++) {
            if (ChronoUnit.HOURS.between(currentDay, nextDay) != 24) {
                daysNotWith24Hours.add(MonthDay.from(currentDay));
            }
            currentDay = nextDay;
            nextDay = nextDay.plusDays(1);
        }
        return daysNotWith24Hours;
    }

    public List<ZoneId> zonesAlwaysClockShift(List<ZoneId> zones) {
        LocalDateTime start = LocalDateTime.of(1900,1,1,0,0);
        List<ZoneId> zonesAlwaysClockShift = new ArrayList<>();

        for (ZoneId zone : zones) {
            boolean isShifted = true;
            while (start.isBefore(LocalDateTime.now())){
                List<MonthDay> monthDays = daysNotWith24Hours(Year.of(start.getYear()), zone);
                if (monthDays.isEmpty()) {
                    isShifted = false;
                    break;
                }
                start = start.plusYears(1);
            }
            if (isShifted) {
                zonesAlwaysClockShift.add(zone);
            }
        }
        return zonesAlwaysClockShift;
    }

    public List<ZoneId> zonesNeverClockShift(List<ZoneId> zones) {
        LocalDateTime start = LocalDateTime.of(1900,1,1,0,0);
        List<ZoneId> zonesNeverClockShift = new ArrayList<>();

        for (ZoneId zone : zones) {
            boolean isShifted = false;
            while (start.isBefore(LocalDateTime.now())){
                List<MonthDay> monthDays = daysNotWith24Hours(Year.of(start.getYear()), zone);
                if (!monthDays.isEmpty()) {
                    isShifted = true;
                    break;
                }
                start = start.plusYears(1);
            }
            if (!isShifted) {
                zonesNeverClockShift.add(zone);
            }
        }
        return zonesNeverClockShift;
    }

    public List<ZoneId> zonesChangedClockShiftRules(List<ZoneId> zones) {
        LocalDateTime start = LocalDateTime.of(1900,1,1,0,0);
        List<ZoneId> zonesChangedClockShiftRules = new ArrayList<>();

        for (ZoneId zone : zones) {
            boolean isShifted = false;
            boolean isNotShifted = false;
            while (start.isBefore(LocalDateTime.now())){
                List<MonthDay> monthDays = daysNotWith24Hours(Year.of(start.getYear()), zone);
                if (!monthDays.isEmpty()) {
                    isShifted = true;
                }
                if (monthDays.isEmpty()) {
                    isNotShifted = true;
                }
                if (isShifted && isNotShifted) {
                    zonesChangedClockShiftRules.add(zone);
                    break;
                }
                start = start.plusYears(1);
            }
        }
        return zonesChangedClockShiftRules;
    }
}
