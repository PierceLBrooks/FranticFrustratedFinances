
// Author: Pierce Brooks

package com.piercelbrooks.f3;

public class DateTime
{
    private static final String TAG = "F3-DateTime";

    private int day;
    private int month;
    private int year;

    public DateTime()
    {
        this(-1, -1, -1);
    }

    public DateTime(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
