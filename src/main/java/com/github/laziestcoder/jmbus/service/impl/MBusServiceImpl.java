package com.github.laziestcoder.jmbus.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.laziestcoder.jmbus.mbus.LibMbus;
import com.github.laziestcoder.jmbus.mbus.mbus_frame;
import com.github.laziestcoder.jmbus.mbus.mbus_frame_data;
import com.github.laziestcoder.jmbus.service.MBusService;
import com.sun.jna.Native;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

@Service
public class MBusServiceImpl implements MBusService {
    private static final Logger LOG = LoggerFactory.getLogger(MBusService.class.getName());
    private static final String SO_FILE_PATH_NAME = "libmbus.so";
    private final XmlMapper xmlMapper = new XmlMapper();
    private LibMbus libMbus = null;

    public MBusServiceImpl() {
        try {
            ClassPathResource LIB_RESOURCE = new ClassPathResource(SO_FILE_PATH_NAME, this.getClass().getClassLoader());
            String LIB_DIR = LIB_RESOURCE.getPath();
            LOG.info("Libmbus Path: {}", LIB_DIR);
            libMbus = Native.load(LIB_DIR, LibMbus.class);
        } catch (Exception e) {
            LOG.error("Exception: {}", e.getMessage(), e);
        }
    }

    @Override
    public JsonNode decodeMessage(JsonNode dataNode, String propertyName) {
        ((ObjectNode) dataNode).put(propertyName, this.getDecodedValue(dataNode.get(propertyName).asText()));
        return dataNode;
    }

    @Override
    public JsonNode decodeMessage(String hexValue) {
        return this.getDecodedValue(hexValue);
    }

    private JsonNode getDecodedValue(String hexValue) {
        JsonNode jsonResult = null;
        try {
            byte[] buff = new byte[5120];
            byte[] raw_buff = hexValue.getBytes();
            long buffLength = libMbus.mbus_hex2bin(buff, buff.length, raw_buff, raw_buff.length);
            mbus_frame mBusFrame = new mbus_frame();
            libMbus.mbus_parse(mBusFrame, buff, buffLength);
            mbus_frame_data frame_data = new mbus_frame_data();
            libMbus.mbus_frame_data_parse(mBusFrame, frame_data);
            libMbus.mbus_frame_print(mBusFrame);
            String result = libMbus.mbus_frame_data_xml(frame_data);
            jsonResult = xmlMapper.readTree(result.getBytes());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return jsonResult;
    }
}

