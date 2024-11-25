package com.notificationapi.notificationapi.crossCutting.utils;

import java.util.UUID;

public class UtilUUID {
   private final static String UUID_STRING = "ffffffff-ffff-ffff-ffff-ffffffffffff";
   private final static UUID UUID_DEFAULT_VALUE = UUID.fromString(UUID_STRING);


    private UtilUUID() {

    }

    public static String getUuidString() {
        return UUID_STRING;
    }

    public static  UUID getUuidDefaultValue() {
        return UUID_DEFAULT_VALUE;
    }
}
