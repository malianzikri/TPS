package com.edii.tools;

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Tanggalan {

    public String UNIXNUMBER() {
        String MONTH = null;
        String unixday = null;
        String YEAR = null;
        String HOUR = null;
        String MINUTE = null;
        String SECOND = null;
        String MILLISECOND = null;
        String UNIXNUMBER = null;
        try {
            //Thread.currentThread().sleep(1000);
            MONTH = MONTH();
            unixday = DATE();
            YEAR = YEAR();
            HOUR = HOURDAY();
            MINUTE = MINUTE();
            SECOND = SECOND();
            MILLISECOND = MILLISECOND();
            UNIXNUMBER = MONTH + "" + unixday + "" + YEAR + "" + HOUR + "" + MINUTE + "" + SECOND + "" + MILLISECOND;
        } catch (Exception e) {
            UNIXNUMBER = "";
        } finally {
            MONTH = null;
            unixday = null;
            YEAR = null;
            HOUR = null;
            MINUTE = null;
            SECOND = null;
            MILLISECOND = null;
            return UNIXNUMBER;
        }
    }

    public String MONTH() {
        Calendar calendarjava = null;
        String _mm_ = null;
        int mm = 0;
        try {
            calendarjava = new GregorianCalendar();
            mm = calendarjava.get(Calendar.MONTH) + 1;
            _mm_ = new Integer(mm).toString();
            if (_mm_.length() == 1) {
                _mm_ = "0" + _mm_;
            }

        } catch (Exception e) {
            _mm_ = "";
        } finally {
            calendarjava = null;
            mm = 0;
            return _mm_;
        }
    }

    public String DATE() {
        Calendar calendarjava = null;
        String _dd_ = null;
        int dd = 0;
        try {
            calendarjava = new GregorianCalendar();
            dd = calendarjava.get(Calendar.DATE);
            _dd_ = new Integer(dd).toString();
            if (_dd_.length() == 1) {
                _dd_ = "0" + _dd_;
            }
        } catch (Exception e) {
            _dd_ = "";
        } finally {
            calendarjava = null;
            dd = 0;
            return _dd_;
        }
    }

    public String YEAR() {
        Calendar calendarjava = null;
        String _yy_ = null;
        int yy = 0;
        try {
            calendarjava = new GregorianCalendar();
            yy = calendarjava.get(Calendar.YEAR);
            _yy_ = new Integer(yy).toString();
        } catch (Exception e) {
            _yy_ = "";
        } finally {
            calendarjava = null;
            yy = 0;
            return _yy_;
        }
    }

    public String HOURDAY() {
        Calendar calendarjava = null;
        String _hour_ = null;
        int hour = 0;
        try {
            calendarjava = new GregorianCalendar();
            hour = calendarjava.get(Calendar.HOUR_OF_DAY);
            _hour_ = new Integer(hour).toString();
            if (_hour_.length() == 1) {
                _hour_ = "0" + _hour_;
            }
        } catch (Exception e) {
            _hour_ = "";
        } finally {
            calendarjava = null;
            hour = 0;
            return _hour_;
        }
    }

    public String MINUTE() {
        Calendar calendarjava = null;
        String _minute_ = null;
        int minute = 0;
        try {
            calendarjava = new GregorianCalendar();
            minute = calendarjava.get(Calendar.MINUTE);
            _minute_ = new Integer(minute).toString();
            if (_minute_.length() == 1) {
                _minute_ = "0" + _minute_;
            }
        } catch (Exception e) {
            _minute_ = "";
        } finally {
            calendarjava = null;
            minute = 0;
            return _minute_;
        }
    }

    public String SECOND() {
        Calendar calendarjava = null;
        String _second_ = null;
        int second = 0;
        try {
            calendarjava = new GregorianCalendar();
            second = calendarjava.get(Calendar.SECOND);
            _second_ = new Integer(second).toString();
            if (_second_.length() == 1) {
                _second_ = "0" + _second_;
            }
        } catch (Exception e) {
            _second_ = "";
        } finally {
            calendarjava = null;
            second = 0;
            return _second_;
        }
    }

    public String MILLISECOND() {
        Calendar calendarjava = null;
        String _milisecond_ = null;
        int milisecond = 0;
        try {
            calendarjava = new GregorianCalendar();
            milisecond = calendarjava.get(Calendar.MILLISECOND);
            _milisecond_ = new Integer(milisecond).toString();
            if (_milisecond_.length() == 1) {
                _milisecond_ = "0" + _milisecond_;
            }
        } catch (Exception e) {
            _milisecond_ = "";
        } finally {
            calendarjava = null;
            milisecond = 0;
            return _milisecond_;
        }
    }

    public String getNow(String param) {
        return new SimpleDateFormat(param).format(new Date());
    }
}
