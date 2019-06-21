package com.baseinfotech.juscep.utility;

import com.baseinfotech.juscep.model.UserType;

import java.util.HashMap;
import java.util.Map;


public class ApiUtility {
    public static final Map<UserType, String> registrationApi = new HashMap<>();
    public static final String CHECK_USER_ALREADY_REGISTERED_URL = "https://baseinfotech.com/juscep/index.php/appkit/mobilechk";
    public static final String loginApi = "https://baseinfotech.com/juscep/index.php/appkit/login/";

    static {
        registrationApi.put(UserType.CUSTOMER, "https://baseinfotech.com/juscep/index.php/appkit/registrationuser");
        registrationApi.put(UserType.VENDOR, "https://baseinfotech.com/juscep/index.php/appkit/registrationcompany");
    }
}
