package com.github.laziestcoder.jmbus.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.laziestcoder.jmbus.mbus.LibMbus;
import com.github.laziestcoder.jmbus.mbus.mbus_frame;
import com.github.laziestcoder.jmbus.mbus.mbus_frame_data;
import com.github.laziestcoder.jmbus.service.MBusService;
import com.sun.jna.Native;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MBusServiceImpl implements MBusService {
    private static final Logger LOG = Logger.getLogger(MBusService.class.getName());
    private static final String LIB_DIR = new ClassPathResource("libmbus").getPath();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();
    private static final LibMbus libMbus = Native.load(LIB_DIR, LibMbus.class);

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
        LOG.log(Level.INFO, "Libmbus Found: [{}]", LIB_DIR);
        System.out.println(MessageFormat.format("Libmbus Found: {0}", LIB_DIR));
        JsonNode jsonResult = null;
        try {
            byte[] buff = new byte[4096];
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
            LOG.log(Level.INFO, e.getMessage(), e);
        }
        return jsonResult;
    }
}

