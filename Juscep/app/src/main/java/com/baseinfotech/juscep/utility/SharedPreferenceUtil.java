package com.baseinfotech.juscep.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.baseinfotech.juscep.model.User;
import com.baseinfotech.juscep.model.UserType;

public class SharedPreferenceUtil {
    private static final String USER_SHARED_PREFERENCE = "User_shared_preference";
    private static final String NAME = "Name";
    private static final String MOBILE_NUMBER = "Mobile_Number";
    private static final String TYPE_ORDINAL = "Type_ordinal";

    public static User getLoggedInUSer(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(NAME, "");
        String mobileNumber = sharedPreferences.getString(MOBILE_NUMBER, "");
        int typeOridinal = sharedPreferences.getInt(TYPE_ORDINAL, UserType.DEFAULT.ordinal());

        if (name.equals("") && mobileNumber.equals("") && typeOridinal==UserType.DEFAULT.ordinal()){
            return null;
        }
        return new User(name, mobileNumber, UserType.values()[typeOridinal]);

    }

    public static void setLoggedInUser(Context context, User user){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME, user.getId());
        editor.putString(MOBILE_NUMBER, user.getMobileNumber());
        editor.putInt(TYPE_ORDINAL, user.getUserType().ordinal());
        editor.apply();
    }

    public static void clearUserLoggedIn(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
