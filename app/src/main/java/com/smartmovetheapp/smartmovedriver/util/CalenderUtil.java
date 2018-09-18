package com.smartmovetheapp.smartmovedriver.util;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CalenderUtil {

    public static long getStartOfDayTime(long timeOfDayInMs) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeOfDayInMs);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

        return calendar.getTimeInMillis();
    }

    public static long getEndOfDayTime(long timeOfDayInMs) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeOfDayInMs);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

        return calendar.getTimeInMillis();
    }

    public static long convertPickerValuesToLong(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return getStartOfDayTime(calendar.getTimeInMillis());
    }

    public static long plusDays(long timeInMs, int daysToAdd) {
        return changeDays(timeInMs, daysToAdd);
    }

    public static long minusDays(long timeInMs, int daysToMinus) {
        return changeDays(timeInMs, -daysToMinus);
    }

    private static long changeDays(long timeInMs, int daysToChange) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMs);
        calendar.add(Calendar.DAY_OF_MONTH, daysToChange);

        return calendar.getTimeInMillis();
    }

    public static String getDisplayDate(long dateTimeInMs) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTimeInMs);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        //LogUtil.d("TAG", "getDisplayDate: "+ String.format(Locale.getDefault(), "%02d/%02d/%02d", dayOfMonth, month, year));
        return String.format(Locale.getDefault(), "%02d/%02d/%02d", dayOfMonth, month, year);
    }

    public static String getDisplayDateInLongerFormat(long dateTimeInMs) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTimeInMs);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        return getMonthName(month) + " " + dayOfMonth + " " + year;
    }


    public static String getActivityTimerTime(long activityStartTimeInMs) {
        long difference = System.currentTimeMillis() - activityStartTimeInMs;
        long hours = TimeUnit.MILLISECONDS.toHours(difference);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(difference) / TimeUnit.HOURS.toMinutes(1);
        //TODO: THE ABOVE METHODS WERE RETURNING 0 FOR ANY DIFFERENCE.
        long diffMinutes = difference / (60 * 1000) % 60;
        long diffHours = difference / (60 * 60 * 1000) % 24;
        return String.format(Locale.getDefault(), "%02d:%02d", diffHours, diffMinutes);
    }

    public static String getActivityTimerTime(long activityStartTimeInMs, long activityEndTimeInMs) {
        long difference = activityEndTimeInMs - activityStartTimeInMs;
        long hours = TimeUnit.MILLISECONDS.toHours(difference);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(difference) / TimeUnit.HOURS.toMinutes(1);
        //TODO: THE ABOVE METHODS WERE RETURNING 0 FOR ANY DIFFERENCE.
        long diffMinutes = difference / (60 * 1000) % 60;
        long diffHours = difference / (60 * 60 * 1000) % 24;
        return String.format(Locale.getDefault(), "%02d:%02d", diffHours, diffMinutes);
    }

    public static String getActivityTimerTimeFromDifference(long timeDifference) {
        long diffMinutes = timeDifference / (60 * 1000) % 60;
        long diffHours = timeDifference / (60 * 60 * 1000) % 24;
        return String.format(Locale.getDefault(), "%02d:%02d", diffHours, diffMinutes);
    }

    public static int calculateAge(long birthDateInMs) {
        long ageInMs = System.currentTimeMillis() - birthDateInMs;
        Calendar birthCalendar = Calendar.getInstance();
        Calendar todaysCalendar = Calendar.getInstance();
        birthCalendar.setTimeInMillis(birthDateInMs);

        if (birthCalendar.after(todaysCalendar)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }
        int age = todaysCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        int todaysMonth = todaysCalendar.get(Calendar.MONTH);
        int birthMonth = birthCalendar.get(Calendar.MONTH);
        if (birthMonth > todaysMonth) {
            age--;
        } else if (todaysMonth == birthMonth) {
            if (birthCalendar.get(Calendar.DAY_OF_MONTH) > todaysCalendar.get(Calendar.DAY_OF_MONTH)) {
                age--;
            }
        }

        return age;
    }

    public static String calculateAccurateAge(long dobInLong) {
        int years = 0;
        int months = 0;
        int days = 0;
        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(dobInLong);
        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }
        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }

        if (years == 0) {
            return months + " months ";

        } else if (months == 0) {
            return years + " years ";
        } else {
            return years + " years " + months + " months ";
        }
    }

    public static String getMonthName(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }
        return "";
    }

    public static int convertToDays(Long duration) {
        return (int) TimeUnit.MILLISECONDS.toDays(duration);
    }

    public static Long ConvertDaysToLong(int days) {
        return TimeUnit.DAYS.toMillis(days);
    }

    public static int getMonthFromMillis(long dateInMs) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMs);
        return calendar.get(Calendar.MONTH);
    }

    public static int getMiniYearFromMillis(long dateInMs) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMs);
        String yearString = String.valueOf(calendar.get(Calendar.YEAR));

        int miniYear;
        try {
            miniYear = Integer.parseInt(yearString.substring(yearString.length() - 2, yearString.length()));
        } catch (NumberFormatException e) {
            miniYear = 0;
        }
        return miniYear;
    }

    public static String getStringDate(int dayOfMonth, int month, int year) {
        return String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
    }


    public static int[] getAgeByDob(Long dobInMs) {

        int ageYears;
        int ageMonths;
        int ageDays = 0;

        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(dobInMs);

        //create calendar object for current day
        Calendar now = Calendar.getInstance();

        //Get difference between years
        ageYears = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        //Get difference between months
        ageMonths = currMonth - birthMonth;

        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (ageMonths < 0) {
            ageYears--;
            ageMonths = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                ageMonths--;
        } else if (ageMonths == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            ageYears--;
            ageMonths = 11;
        }

        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            ageDays = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            ageDays = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            ageDays = 0;
            if (ageMonths == 12) {
                ageYears++;
                ageMonths = 0;
            }
        }

        return new int[]{ageYears, ageMonths};
    }

    public static String getDisplayTime(long timeInMs) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMs);

        int hour = calendar.get(Calendar.HOUR);
        if (hour == 0) {
            hour = 12;
        }

        String amOrPm = (calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";

        return String.format(Locale.getDefault(), "%02d:%02d %s",
                hour,
                calendar.get(Calendar.MINUTE),
                amOrPm);
    }

    public static String getDisplayDateTime(long timeInMs) {
        return String.format("%s - %s", getDisplayDate(timeInMs), getDisplayTime(timeInMs));
    }
}
