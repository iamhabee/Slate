package com.habee.slate.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getCurrentTimeStamp(){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm-yyyy");
            String currentDate = simpleDateFormat.format(new Date());
            return currentDate;
        }catch (Exception e){
            return null;
        }
    }
    public static String getMonthFromNumber(String number){
        switch (number){
            case "01":{
                return "Jan";
            }
            case "02":{
                return "Feb";
            }
            case "03":{
                return "Mar";
            }
            case "04":{
                return "Apr";
            }
            case "05":{
                return "May";
            }
            case "06":{
                return "Jun";
            }
            case "07":{
                return "Jul";
            }
            case "08":{
                return "Aug";
            }
            case "09":{
                return "Sep";
            }
            case "10":{
                return "Oct";
            }
            case "11":{
                return "Nov";
            }
            case "12":{
                return "Dec";
            }
            default:{
                return "No Date";
            }
        }
    }
}
