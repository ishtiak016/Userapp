package com.customer.masters.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.customer.masters.constants.Constants;


public class SettingPreference {

    private static final String CURRENCY = "$";
    private static final String ABOUTUS = "ABOUTUS";
    private static final String EMAIL = "EMAIL";
    private static final String PHONE = "PHONE";
    private static final String WEBSITE = "WEBSITE";
    private static final String PAYPALKEY = "PAYPAL";
    private static final String STRIPEACTIVE = "STRIPEACTIVE";
    private static final String PAYPALMODE = "PAYPALMODE";
    private static final String PAYPALACTIVE = "PAYPALACTIVE";
    private static final String CURRENCYTEXT = "CURRENCYTEXT";
    private static final String RAZORPAYMODE = "RAZORPAYMODE";
    private static final String RAZORPAYACTIVE = "RAZORPAYACTIVE";
    private static final String RAZORPAYKEY = "RAZORPAY";
    private static final String FLUTTERWAVEMODE = "FLUTTERWAVEMODE";
    private static final String FLUTTERWAVEACTIVE = "FLUTTERWAVEACTIVE";
    private static final String FLUTTERWAVEKEY = "FLUTTERWAVE";
    private static final String FLUTTERWAVEENC = "FLUTTERWAVEENC";
    private static final String FLUTTERWAVECURRENCY = "FLUTTERWAVECURRENCY";

    private final SharedPreferences pref;

    private SharedPreferences.Editor editor;

    public SettingPreference(Context context) {
        pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void updateCurrency(String string) {
        editor = pref.edit();
        editor.putString(CURRENCY, string);
        editor.commit();
    }

    public void updatePaypal(String string) {
        editor = pref.edit();
        editor.putString(PAYPALKEY, string);
        editor.commit();
    }

    public void updateRazorpay(String string) {
        editor = pref.edit();
        editor.putString(RAZORPAYKEY, string);
        editor.commit();
    }

    public void updateFlutterwave(String string) {
        editor = pref.edit();
        editor.putString(FLUTTERWAVEKEY, string);
        editor.commit();
    }

    public void updateFlutterwaveenc(String string) {
        editor = pref.edit();
        editor.putString(FLUTTERWAVEENC, string);
        editor.commit();
    }

    public void updateabout(String string) {
        editor = pref.edit();
        editor.putString(ABOUTUS, string);
        editor.commit();
    }

    public void updateemail(String string) {
        editor = pref.edit();
        editor.putString(EMAIL, string);
        editor.commit();
    }

    public void updatephone(String string) {
        editor = pref.edit();
        editor.putString(PHONE, string);
        editor.commit();
    }

    public void updateweb(String string) {
        editor = pref.edit();
        editor.putString(WEBSITE, string);
        editor.commit();
    }

    public void updatepaypalactive(String string) {
        editor = pref.edit();
        editor.putString(PAYPALACTIVE, string);
        editor.commit();
    }

    public void updatepaypalmode(String string) {
        editor = pref.edit();
        editor.putString(PAYPALMODE, string);
        editor.commit();
    }

    public void updaterazorpayactive(String string) {
        editor = pref.edit();
        editor.putString(RAZORPAYACTIVE, string);
        editor.commit();
    }

    public void updaterazorpaymode(String string) {
        editor = pref.edit();
        editor.putString(RAZORPAYMODE, string);
        editor.commit();
    }

    public void updateflutterwaveactive(String string) {
        editor = pref.edit();
        editor.putString(FLUTTERWAVEACTIVE, string);
        editor.commit();
    }

    public void updateflutterwavemode(String string) {
        editor = pref.edit();
        editor.putString(FLUTTERWAVEMODE, string);
        editor.commit();
    }

    public void updateflutterwavecurrency(String string) {
        editor = pref.edit();
        editor.putString(FLUTTERWAVECURRENCY, string);
        editor.commit();
    }

    public void updatestripeactive(String string) {
        editor = pref.edit();
        editor.putString(STRIPEACTIVE, string);
        editor.commit();
    }

    public void updatecurrencytext(String string) {
        editor = pref.edit();
        editor.putString(CURRENCYTEXT, string);
        editor.commit();
    }

    public String[] getSetting() {

        String[] settingan = new String[18];
        settingan[0] = pref.getString(CURRENCY, "$");
        settingan[1] = pref.getString(ABOUTUS, "");
        settingan[2] = pref.getString(EMAIL, "");
        settingan[3] = pref.getString(PHONE, "");
        settingan[4] = pref.getString(WEBSITE, "");
        settingan[5] = pref.getString(PAYPALKEY, "");
        settingan[6] = pref.getString(PAYPALACTIVE, "0");
        settingan[7] = pref.getString(STRIPEACTIVE, "0");
        settingan[8] = pref.getString(PAYPALMODE, "1");
        settingan[9] = pref.getString(CURRENCYTEXT, "USD");
        settingan[10] = pref.getString(RAZORPAYKEY, "");
        settingan[11] = pref.getString(RAZORPAYACTIVE, "0");
        settingan[12] = pref.getString(RAZORPAYMODE, "1");
        settingan[13] = pref.getString(FLUTTERWAVEKEY, "");
        settingan[14] = pref.getString(FLUTTERWAVEENC, "");
        settingan[15] = pref.getString(FLUTTERWAVEACTIVE, "0");
        settingan[16] = pref.getString(FLUTTERWAVEMODE, "1");
        settingan[17] = pref.getString(FLUTTERWAVECURRENCY, "USD");
        return settingan;
    }
}