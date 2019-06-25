package com.baseinfotech.juscep.utility;

import com.baseinfotech.juscep.model.UserType;

import java.util.HashMap;
import java.util.Map;


public class ApiUtility {
    public static final Map<UserType, String> registrationApi = new HashMap<>();
    public static final Map<UserType, String> bookingApi = new HashMap<>();
    public static final String CHECK_USER_ALREADY_REGISTERED_URL = "https://baseinfotech.com/juscep/index.php/appkit/mobilechk";
    public static final String loginApi = "https://baseinfotech.com/juscep/index.php/appkit/login/";
    public static final String equipmentListApi = "https://baseinfotech.com/juscep/index.php/Appkit/serviceslist/";
    public static final String ABOUT_US_URL = "https://baseinfotech.com/juscep/index.php/about-us/";
    public static final String CONTAACT_US_URL = "https://baseinfotech.com/juscep/index.php/contact-us/";
    public static final String PRIVACY_POLICY = "https://baseinfotech.com/juscep/index.php/privacy-policy/";

    static {
        registrationApi.put(UserType.CUSTOMER, "https://baseinfotech.com/juscep/index.php/appkit/registrationuser");
        registrationApi.put(UserType.VENDOR, "https://baseinfotech.com/juscep/index.php/appkit/registrationcompany");

        bookingApi.put(UserType.CUSTOMER, "https://baseinfotech.com/juscep/index.php/appkit/bookingsave");
        bookingApi.put(UserType.VENDOR, "https://baseinfotech.com/juscep/index.php/appkit/supplierbookingsave");
    }


}
