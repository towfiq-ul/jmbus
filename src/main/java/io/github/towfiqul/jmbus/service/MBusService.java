package io.github.towfiqul.jmbus.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface MBusService {
    JsonNode decodeMessage(JsonNode dataNode, String propertyName);

    JsonNode decodeMessage(String hexValue);

}

