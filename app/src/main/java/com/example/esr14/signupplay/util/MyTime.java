package com.example.esr14.signupplay.util;

import java.util.ArrayList;
import java.util.List;

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

    public static int getTimeDifference(int startTime, int endTime){

        if (endTime > startTime){
            return endTime - startTime;
        }
        return -1;
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

    private static int[] timeLeft(int hour, int minute, int hourCurrent, int minuteCurrent) {
        if (MyTime.timeToInt(hourCurrent, minuteCurrent) > MyTime.timeToInt(hour, minute)) {
            time = MyTime.intToHourMinute(MyTime.timeToInt(hourCurrent, minuteCurrent) - MyTime.timeToInt(hour, minute));
            return time;
        }

        return null;
    }

    public static List<Integer> getNextBusTime(int n, int hourCurrent, int minuteCurrent, List<Integer> schedule) {
        List<Integer> nextBus = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < schedule.size(); i++) {
            if (timeToInt(hourCurrent, minuteCurrent) < schedule.get(i) && count < n) {
                nextBus.add(schedule.get(i));
                count++;
            }
        }
        System.out.println(nextBus.get(0));
        System.out.println(nextBus.get(1));
        System.out.println(nextBus.get(2));
        return nextBus;
    }

}
