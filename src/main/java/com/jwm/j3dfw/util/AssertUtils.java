package com.jwm.j3dfw.util;

/**
 * Created by Jeff on 2016-04-03.
 */
public class AssertUtils {

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
    }
}
