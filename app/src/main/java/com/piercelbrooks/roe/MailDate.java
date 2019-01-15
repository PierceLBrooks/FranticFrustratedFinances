
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.support.annotation.Nullable;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;

public class MailDate {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public MailDate() {
        year = -1;
        month = -1;
        day = -1;
        hour = -1;
        minute = -1;
        second = -1;
    }

    public MailDate(@Nullable MailDate other) {
        this();
        if (other != null) {
            year = other.getYear();
            month = other.getMonth();
            day = other.getDay();
            hour = other.getHour();
            minute = other.getMinute();
            second = other.getSecond();
        }
    }

    public MailDate(@Nullable Date date) {
        this();
        if (date != null) {
            year = date.getYear();
            month = date.getMonth();
            day = date.getDay();
            hour = date.getHours();
            minute = date.getMinutes();
            second = date.getSeconds();
        }
    }

    public MailDate(@Nullable Message message) {
        Date date = null;
        if (message != null) {
            try {
                date = message.getReceivedDate();
            } catch (MessagingException e) {
                e.printStackTrace();
                date = null;
            }
            if (date == null) {
                try {
                    date = message.getSentDate();
                } catch (MessagingException e) {
                    e.printStackTrace();
                    date = null;
                }
            }
        }
        if (date != null) {
            year = date.getYear();
            month = date.getMonth();
            day = date.getDay();
            hour = date.getHours();
            minute = date.getMinutes();
            second = date.getSeconds();
        } else {
            year = -1;
            month = -1;
            day = -1;
            hour = -1;
            minute = -1;
            second = -1;
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
}
