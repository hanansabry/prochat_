package com.egrobots.prochat.utils;

import android.telephony.PhoneNumberUtils;
import android.util.Patterns;

public class Utils {

    public static boolean isEmailValid(String emailAddress) {
        return !emailAddress.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    public static boolean isPhoneValid(String phone) {
        return !phone.isEmpty() && PhoneNumberUtils.isGlobalPhoneNumber(phone);
    }
}
