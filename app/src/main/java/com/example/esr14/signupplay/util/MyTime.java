package com.example.esr14.signupplay.util;

/**
 * Created by Rustam Kamberov on 17/04/16.
 */
public class MyTime {

    public MyTime(){

    }

    public static int timeToInt(int hour, int minute){
        int intTime = (hour * 3600) + (minute * 60);

        return intTime;
    }

    public static int [] intToHourMinute(int intTime){
        int[] time = new int[2];
        int hour = intTime / 3600;
        int leaves = intTime - (hour * 3600);
        int minute = leaves / 60;
        time[0] = hour;
        time[1] = minute;
        return time;
    }
}
