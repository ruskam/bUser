package com.example.esr14.signupplay.util;

/**
 * Created by Rustam Kamberov on 17/04/16.
 */
public class MyTime {

    private int hour;
    private int minute;
    private static int[] time = new int[2];

    public MyTime() {

    }

    public MyTime(int hour, int minute) {
        this.hour = hour;

    }

    public static int timeToInt(int hour, int minute) {
        int intTime = (hour * 3600) + (minute * 60);

        return intTime;
    }

    public static int[] intToHourMinute(int intTime) {

        int hour = intTime / 3600;
        int leaves = intTime - (hour * 3600);
        int minute = leaves / 60;
        time[0] = hour;
        time[1] = minute;
        return time;
    }

    public static int[] timeLeft(int hour, int minute, int hourCurrent, int minuteCurrent) {
        if (MyTime.timeToInt(hourCurrent, minuteCurrent) > MyTime.timeToInt(hour, minute)) {
            time = MyTime.intToHourMinute(MyTime.timeToInt(hourCurrent, minuteCurrent) - MyTime.timeToInt(hour, minute));
            return time;
        }

        return null;
    }

}
