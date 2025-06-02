package com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.validate;

import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validate {
    public static void shakeEditText(EditText editText) { // tao rung cho editext loi
        Animation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        editText.startAnimation(shake);
    }
    // ktra chuoi ko co chu so
    public static boolean containDigits(String str){
        for ( char c :str.toCharArray()){
            if(Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
    // ktra khong duoc chua cac ki tu dac biet
    public static boolean containsSpecialCharacters(String str) {
        String specialCharacters = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";

        for (char c : str.toCharArray()) {
            if (specialCharacters.contains(String.valueOf(c))) {
                return false;
            }
        }

        return true;
    }

    // check loi dinh dang ngay thang
    public static boolean validateDateFormat(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(input);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // kiem tra ngay lon hon
    public static boolean isDate1GreaterThanDate2(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsedDate1 = format.parse(date1);
            Date parsedDate2 = format.parse(date2);
            return parsedDate1.after(parsedDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
