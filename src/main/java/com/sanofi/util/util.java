package com.sanofi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class util {
    public static String getCurrentDateWithFormat(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }
}
