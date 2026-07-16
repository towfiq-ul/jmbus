package io.github.towfiqul.jmbus.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.jna.Native;
import io.github.towfiqul.jmbus.mbus.LibMbus;
import io.github.towfiqul.jmbus.mbus.mbus_frame;
import io.github.towfiqul.jmbus.mbus.mbus_frame_data;
import io.github.towfiqul.jmbus.service.MBusService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Exercises {@link MBusServiceImpl} against the real, bundled {@code libmbus.so} — there's no
 * mocking of the native layer, so a passing test here means the JNA bindings actually
 * round-tripped through the C library correctly.
 */
@SpringBootTest
public class MBusServiceTest {
    public MBusService mBusService = new io.github.towfiqul.jmbus.service.impl.MBusServiceImpl();
    private static final String hexValue = "68 AE AE 68 28 01 72 95 08 12 11 83 14 02 04 17 00 00 00 84 00 86 3B 23 00 00 00 84 0086 3C D1 01 00 00 84 40 86 3B 00 00 00 00 84 40 86 3C 00 00 00 00 85 00 5B 2B 4B AC 41 85 00 5F 20 D7 AC 41 85 40 5B 00 00 B8 42 85 40 5F 00 00 B8 42 85 00 3B 84 00 35 3F 85 40 3B 00 00 00 00 95 00 3B 95 CF B2 43 95 40 3B 00 00 00 00 85 00 2B 00 00 00 00 8540 2B 00 00 00 00 95 00 2B D3 9F 90 46 95 40 2B 00 00 00 00 04 6D 19 0F 8A 17 84 00 7C 01 43 F3 0D 00 00 84 40 7C 01 43 9D 01 00 00 84 00 7C 01 63 01 00 00 00 84 40 7C 01 63 01 00 00 00 0F 2F 16";
    private static final String decodedHex = "{\"SlaveInformation\":{\"Id\":\"11120895\",\"Manufacturer\":\"EDC\",\"Version\":\"2\",\"ProductName\":\"\",\"Medium\":\"Heat: Outlet\",\"AccessNumber\":\"23\",\"Status\":\"00\",\"Signature\":\"0000\"},\"DataRecord\":[{\"id\":\"0\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"0\",\"Unit\":\"Energy (kWh)\",\"Value\":\"35\"},{\"id\":\"1\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"0\",\"Unit\":\"Energy (kWh)\",\"Value\":\"465\"},{\"id\":\"2\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"1\",\"Unit\":\"Energy (kWh)\",\"Value\":\"0\"},{\"id\":\"3\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"1\",\"Unit\":\"Energy (kWh)\",\"Value\":\"0\"},{\"id\":\"4\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"0\",\"Unit\":\"Flow temperature (deg C)\",\"Value\":\"21.536703\"},{\"id\":\"5\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"0\",\"Unit\":\"Return temperature (deg C)\",\"Value\":\"21.605042\"},{\"id\":\"6\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"1\",\"Unit\":\"Flow temperature (deg C)\",\"Value\":\"92.000000\"},{\"id\":\"7\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"1\",\"Unit\":\"Return temperature (deg C)\",\"Value\":\"92.000000\"},{\"id\":\"8\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"0\",\"Unit\":\"Volume flow (m m^3/h)\",\"Value\":\"0.707039\"},{\"id\":\"9\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"1\",\"Unit\":\"Volume flow (m m^3/h)\",\"Value\":\"0.000000\"},{\"id\":\"10\",\"Function\":\"Maximum value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"0\",\"Unit\":\"Volume flow (m m^3/h)\",\"Value\":\"357.621735\"},{\"id\":\"11\",\"Function\":\"Maximum value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"1\",\"Unit\":\"Volume flow (m m^3/h)\",\"Value\":\"0.000000\"},{\"id\":\"12\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"0\",\"Unit\":\"Power (W)\",\"Value\":\"0.000000\"},{\"id\":\"13\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"1\",\"Unit\":\"Power (W)\",\"Value\":\"0.000000\"},{\"id\":\"14\",\"Function\":\"Maximum value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"0\",\"Unit\":\"Power (W)\",\"Value\":\"18511.912109\"},{\"id\":\"15\",\"Function\":\"Maximum value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"1\",\"Unit\":\"Power (W)\",\"Value\":\"0.000000\"},{\"id\":\"16\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Unit\":\"Time Point (time & date)\",\"Value\":\"2012-07-10T15:25:00\"},{\"id\":\"17\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"0\",\"Unit\":\"C\",\"Value\":\"3571\"},{\"id\":\"18\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"1\",\"Unit\":\"C\",\"Value\":\"413\"},{\"id\":\"19\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"0\",\"Unit\":\"c\",\"Value\":\"1\"},{\"id\":\"20\",\"Function\":\"Instantaneous value\",\"StorageNumber\":\"0\",\"Tariff\":\"0\",\"Device\":\"1\",\"Unit\":\"c\",\"Value\":\"1\"},{\"id\":\"21\",\"Function\":\"Manufacturer specific\",\"Value\":\"\"}]}";

    /**
     * Decodes a known-good telegram and checks the resulting JSON matches exactly, field for
     * field.
     */
    @Test
    public void decodeMessage() {
        JsonNode result = mBusService.decodeMessage(hexValue);
        assertEquals(decodedHex, result.toString());
    }

    /**
     * Parses the same telegram directly via {@link LibMbus} (bypassing {@link MBusServiceImpl})
     * and checks {@code data_var.nrecords} reads back correctly. This is really a struct-layout
     * regression test: {@code decodedHex} above has 22 {@code DataRecord} entries (ids 0-21),
     * but {@link MBusServiceImpl} never reads {@code mbus_data_variable}'s fields itself, so the
     * black-box {@link #decodeMessage()} test can't catch a layout bug here the way this one can
     * (see {@code CHANGELOG.md} for the bug this test was added to guard against).
     */
    @Test
    public void mbusDataVariableRecordCountMatchesLibmbus() throws Exception {
        File libResource = ResourceUtils.getFile("libmbus.so");
        LibMbus libMbus = Native.load(libResource.getPath(), LibMbus.class);

        byte[] buff = new byte[4096];
        byte[] rawBuff = hexValue.getBytes();
        long buffLength = libMbus.mbus_hex2bin(buff, buff.length, rawBuff, rawBuff.length);
        mbus_frame frame = new mbus_frame();
        libMbus.mbus_parse(frame, buff, buffLength);
        mbus_frame_data frameData = new mbus_frame_data();
        libMbus.mbus_frame_data_parse(frame, frameData);

        assertEquals(22, frameData.data_var.nrecords);
    }

}
