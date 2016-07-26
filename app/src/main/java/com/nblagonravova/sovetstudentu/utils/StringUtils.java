package com.nblagonravova.sovetstudentu.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class StringUtils {
    public static String convertToPunycode(String stringUrl)  {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            return stringUrl;
        }

        String punycodeURL = java.net.IDN.toASCII(url.getHost());

        return url.getProtocol() + "://" + punycodeURL + url.getPath();
    }
}
