import org.junit.Assert;
import org.junit.Test;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class DateTimeTest {

    static final DateTime homework = new DateTime();

    @Test
    public void testFridays13() {
        List<String> list = homework.fridays13();
        Assert.assertEquals("Oct 2000", list.get(0));
        Assert.assertEquals("Apr 2001", list.get(1));
        Assert.assertEquals("Oct 2006", list.get(10));
        Assert.assertEquals("Jun 2008", list.get(13));
        Assert.assertEquals("Nov 2020", list.get(36));
    }

    @Test
    public void testEndOnSundays() {
        List<YearMonth> yearMonths = homework.endOnSundays();
        Assert.assertTrue(yearMonths.contains(YearMonth.of(2002, 6)));
        Assert.assertTrue(yearMonths.contains(YearMonth.of(2006, 12)));
        Assert.assertTrue(yearMonths.contains(YearMonth.of(2013, 6)));
        Assert.assertTrue(yearMonths.contains(YearMonth.of(2017, 4)));
    }


    @Test
    public void testBirthdaysOnSaturdays() {
        List<Year> years = homework.birthdaysOnSaturdays(LocalDate.of(1995, 7, 1));
        Assert.assertEquals("1995", years.get(0).toString());
        Assert.assertEquals("2000", years.get(1).toString());
        Assert.assertEquals("2006", years.get(2).toString());
        Assert.assertEquals("2017", years.get(3).toString());
    }

    @Test
    public void testDaysNotWith24Hours() {
        List<MonthDay> monthDays = homework.daysNotWith24Hours(Year.of(2008), ZoneId.systemDefault());
        Assert.assertTrue(monthDays.contains(MonthDay.of(3,30)));
        Assert.assertTrue(monthDays.contains(MonthDay.of(10,26)));
        Assert.assertEquals(2, monthDays.size());
    }

    @Test
    public void zonesAlwaysClockShift() {
        ArrayList<ZoneId> zoneIds = new ArrayList<>();
        zoneIds.add(ZoneId.of("Europe/Kiev"));
        zoneIds.add(ZoneId.of("Asia/Tel_Aviv"));
        zoneIds.add(ZoneId.of("Africa/Banjul"));
        zoneIds.add(ZoneId.of("Africa/Luanda"));
        List<ZoneId> alwaysShift = homework.zonesAlwaysClockShift(zoneIds);
        Assert.assertTrue(alwaysShift.isEmpty());
    }

    @Test
    public void testZonesNeverClockShift() {
        ArrayList<ZoneId> zoneIds = new ArrayList<>();
        zoneIds.add(ZoneId.of("Europe/Kiev"));
        zoneIds.add(ZoneId.of("Asia/Tel_Aviv"));
        zoneIds.add(ZoneId.of("Africa/Banjul"));
        zoneIds.add(ZoneId.of("Africa/Luanda"));
        List<ZoneId> neverShift = homework.zonesNeverClockShift(zoneIds);
        Assert.assertTrue(neverShift.contains(ZoneId.of("Africa/Banjul")));
        Assert.assertTrue(neverShift.contains(ZoneId.of("Africa/Luanda")));
        Assert.assertEquals(2, neverShift.size());
    }

    @Test
    public void zonesChangedClockShiftRules() {
        ArrayList<ZoneId> zoneIds = new ArrayList<>();
        zoneIds.add(ZoneId.of("Europe/Kiev"));
        zoneIds.add(ZoneId.of("Asia/Tel_Aviv"));
        zoneIds.add(ZoneId.of("Africa/Banjul"));
        zoneIds.add(ZoneId.of("Africa/Luanda"));
        List<ZoneId> sometimesShift = homework.zonesChangedClockShiftRules(zoneIds);
        Assert.assertTrue(sometimesShift.contains(ZoneId.of("Europe/Kiev")));
        Assert.assertTrue(sometimesShift.contains(ZoneId.of("Asia/Tel_Aviv")));
        Assert.assertEquals(2, sometimesShift.size());
    }
}
