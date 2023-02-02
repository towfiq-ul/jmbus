/**
 * @author Towfiqul Islam
 * @since ২৫/৮/২২ ১২:০১ AM
 */

package com.github.laziestcoder.jmbus;

public class JMeterBusService {

    static {
        System.load(System.getProperty("user.dir") + "/src/main/resources/libnative.so");
    }

    public static String decodeHexValue(String hexValue) {
        return new JMeterBusService().decodeHexValueFromCLibrary(hexValue);
    }

    public native String decodeHexValueFromCLibrary(String hexValue);
}