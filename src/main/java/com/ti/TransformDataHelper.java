package com.ti;

public class TransformDataHelper {
    public static String getMonth(String month){
        for (var monthVal:MonthsEnum.values()){
            if ((monthVal.ordinal()+1)== Integer.parseInt(month)){
                return monthVal.toString();
            }
        }
        return "";
    }

    public static int convertToInt(Object item){
        return Integer.parseInt((String)item);
    }
}
